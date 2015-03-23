#include"server.h"
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
#include "debug.h"


using namespace std;
// TODO: consider making seperate  class 
void sigchld_handler(int s)
{
   while(waitpid(-1, NULL, WNOHANG) > 0);
}

// get sockaddr, IPv4 or IPv6:
// auxilary funciton make private inside class 
void* Server::get_in_addr(struct sockaddr *sa)
{
   if (sa->sa_family == AF_INET) {
      return &(((struct sockaddr_in*)sa)->sin_addr);
   }

   return &(((struct sockaddr_in6*)sa)->sin6_addr);
}

void Server::init (  ){
   struct addrinfo hints;
   int rv;
   
   memset(&hints, 0, sizeof hints);
   hints.ai_family = AF_UNSPEC;
   hints.ai_socktype = SOCK_STREAM;
   hints.ai_flags = AI_PASSIVE; // use my IP
   
   if ((rv = getaddrinfo(NULL, PORT, &hints, &servinfo)) != 0) {
      fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
      //return 1;   
      exit (1);
   }
}

void Server::bind_socket ()
{
   // TODO: fixme silly 
   int y=1;
   int* yes =&y;
   struct addrinfo *p;
   for(p = servinfo; p != NULL; p = p->ai_next) {
      if ((sockfd = socket(p->ai_family, p->ai_socktype,
			   p->ai_protocol)) == -1) {
	 perror("server: socket");
	 continue;
      }
      //configure socket to reuse addresses 
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
   // all done with this structure
   freeaddrinfo(servinfo); 
}

void Server::listen_socket ( )
{
   if (listen(sockfd, BACKLOG) == -1) {
      perror("listen");
      exit(1);
   }
}

void Server::kill_all_zombies (){
   // TODO: keep an eye on this might be a problem
   struct sigaction sa;
   sa.sa_handler = sigchld_handler; // reap all dead processes
   sigemptyset(&sa.sa_mask);
   sa.sa_flags = SA_RESTART;
   if (sigaction(SIGCHLD, &sa, NULL) == -1) {
      perror("sigaction");
      exit(1);
   }
}

void Server::dummy_dumbo_change_me ()
{
   struct sockaddr_storage their_addr;
   // TODO:  here for know during testing 
   int  numbytes;   
   char buf[MAX_SIZE ];
   char s[INET6_ADDRSTRLEN];
   socklen_t sin_size;
   
   printf("server: waiting for connections...\n");
   while(1)
   {  // main accept() loop
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
      if (!fork())
      { // this is the child process
	 	
	 close(sockfd); // child doesn't need the listener
	 
	 if ((numbytes = recv(new_fd, buf, MAX_SIZE-1, 0)) == -1) {
	    perror("recv");
	    exit(1);
	 }

	 RequestMessage reqMsg{buf};

	 Client lucky_client{};
	 lucky_client.setup(reqMsg.get_header("Host"));
	 ResponseMessage respMessage = lucky_client.forward (reqMsg);
	 std::string respMsgCppStr = respMessage.to_str();

	// for(auto i : respMsgCppStr){
	//    int n = i;
	//    std::cout << i << " ";
	// }
	 //std::cout << endl << respMsgCppStr << endl;
	 int response_size = respMessage.get_message_size();
	
	 const char *respMsgCStr = respMsgCppStr.c_str();
	
	 int bytes_sent=0;
	 int total_sent=0;

	 //make all sure all content get sent
	 while (total_sent < response_size )
	 {
	    if ((bytes_sent = send(new_fd, respMsgCStr, respMessage.get_message_size()  , 0)) == -1)
	    {
	       perror("send");
	       break;
	    }
	    total_sent += bytes_sent;
	 }
	 if (bytes_sent == -1)
	 close(new_fd);
	 exit(0);
      }
      close(new_fd);  // parent doesn't need this
   }
}

void Server::run ()
{
   init ( );                
   bind_socket ();          
   listen_socket ();        
   //change these                
   kill_all_zombies ();     
   dummy_dumbo_change_me ();
}



