/*
** server.c -- a stream socket server demo
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <sys/wait.h>
#include <signal.h>
#include <iostream>
#include "RequestMessage.h"

#define PORT "3490"  // the port users will be connecting to

#define BACKLOG 10	 // how many pending connections queue will hold
#define MAXDATASIZE 1024 

using namespace std;
void sigchld_handler(int s)
{
   while(waitpid(-1, NULL, WNOHANG) > 0);
}

// get sockaddr, IPv4 or IPv6:
void *get_in_addr(struct sockaddr *sa)
{
   if (sa->sa_family == AF_INET) {
      return &(((struct sockaddr_in*)sa)->sin_addr);
   }

   return &(((struct sockaddr_in6*)sa)->sin6_addr);
}


void init (struct addrinfo hints, struct addrinfo** servinfo ){
      int rv;

   memset(&hints, 0, sizeof hints);
   hints.ai_family = AF_UNSPEC;
   hints.ai_socktype = SOCK_STREAM;
   hints.ai_flags = AI_PASSIVE; // use my IP

    if ((rv = getaddrinfo(NULL, PORT, &hints, servinfo)) != 0) {
      fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
      //return 1;   
      exit (1);
   }
    //servinfo = &(*servinfo);
}


void bind_socket (addrinfo* p, addrinfo* servinfo, int& sockfd, int* yes)
{
   for(p = servinfo; p != NULL; p = p->ai_next) {
      if ((sockfd = socket(p->ai_family, p->ai_socktype,
			   p->ai_protocol)) == -1) {
	 perror("server: socket");
	 continue;
      }
      if (setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &yes,
		     sizeof(int)) == -1) {
	 perror("setsockopt");
	 exit(1);
      }

      if (bind(sockfd, p->ai_addr, p->ai_addrlen) == -1) {
	 close(sockfd);
	 perror("server: bind");
	 continue;
      }
      break;
   }

   if (p == NULL)  {
      fprintf(stderr, "server: failed to bind\n");
      exit (2);
      //return 2;
   }
}

void listen_socket (int sockfd, int backlog)
{
   if (listen(sockfd, backlog) == -1) {
      perror("listen");
      exit(1);
   }
}

void kill_all_zombies (struct sigaction& sa){
   sa.sa_handler = sigchld_handler; // reap all dead processes
   sigemptyset(&sa.sa_mask);
   sa.sa_flags = SA_RESTART;
   if (sigaction(SIGCHLD, &sa, NULL) == -1) {
      perror("sigaction");
      exit(1);
   }
}

int main(void)
{
   int sockfd, new_fd;  // listen on sock_fd, new connection on new_fd
   struct addrinfo hints, *servinfo, *p;
   struct sockaddr_storage their_addr; // connector's address information
   socklen_t sin_size;
   struct sigaction sa;
  //TODO possible to move into local scope? will it be used later by some
  // function that we don't se?
   int yes=1;
   //int rv;
   char s[INET6_ADDRSTRLEN];

   //client sdide
   int  numbytes;   
   char buf[MAXDATASIZE ];
   // init structeres dns lookup TODO might need some more work
   init (hints, &servinfo);
   //bind socket
   bind_socket (p, servinfo, sockfd, &yes);
  // all done with this structure
   freeaddrinfo(servinfo); 
   //listen to socket
   listen_socket (sockfd, BACKLOG);

   kill_all_zombies (sa);
   
   printf("server: waiting for connections...\n");

   while(1) {  // main accept() loop
      sin_size = sizeof their_addr;
      new_fd = accept(sockfd, (struct sockaddr *)&their_addr, &sin_size);
      if (new_fd == -1) {
	 perror("accept");
	 continue;
      }

      inet_ntop(their_addr.ss_family,
		get_in_addr((struct sockaddr *)&their_addr),
		s, sizeof s);
      printf("server: got connection from %s\n", s);

      if (!fork()) { // this is the child process
	 close(sockfd); // child doesn't need the listener
          //TODO analyse here  and forward to client client send request to
           // wherever we want to go 
	 // stuff like recv  etct
	if ((numbytes = recv(new_fd, buf, MAXDATASIZE-1, 0)) == -1) {
	    perror("recv");
	    exit(1);
	}
	//std::cout << buf << std::endl;
	const string reqStr{buf};
	RequestMessage req{buf};

	   std::cout << req.get_request_line() << std::endl;
	 if (send(new_fd, "Hello, world!", 13, 0) == -1)
	    perror("send");
	 close(new_fd);
	 exit(0);
      }
      close(new_fd);  // parent doesn't need this
   }

   return 0;
}

