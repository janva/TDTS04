/*
** client.c -- a stream socket client demo
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <netdb.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <sys/socket.h>

#include <arpa/inet.h>

#define PORT "3490" // the port client will be connecting to 

#define MAXDATASIZE 100 // max number of bytes we can get at once 

// get sockaddr, IPv4 or IPv6:
void *get_in_addr(struct sockaddr *sa)
{
   if (sa->sa_family == AF_INET) {
      return &(((struct sockaddr_in*)sa)->sin_addr);
   }

   return &(((struct sockaddr_in6*)sa)->sin6_addr);
}
//TODO needs host as argument
void init_client (struct addrinfo hints, struct addrinfo** servinfo, const char* node)
{
   memset(&hints, 0, sizeof hints);
   hints.ai_family = AF_UNSPEC;
   hints.ai_socktype = SOCK_STREAM;
   
   if ((rv = getaddrinfo(node , PORT, &hints, servinfo)) != 0) {
      fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
      exit (1);
      //return 1;
   }
}

void bind_socket (addrinfo* p, addrinfo* servinfo, int& sockfd)
{
   // loop through all the results and connect to the first we can
   for(p = servinfo; p != NULL; p = p->ai_next) {
      if ((sockfd = socket(p->ai_family, p->ai_socktype,
			   p->ai_protocol)) == -1) {
	 perror("client: socket");
	 continue;
      }
		
      if (connect(sockfd, p->ai_addr, p->ai_addrlen) == -1) {
	 close(sockfd);
	 perror("client: connect");
	 continue;
      }
      break;
   }
   if (p == NULL) {
      fprintf(stderr, "client: failed to connect\n");
      exit (2);
      //return 2;
   }
   //TODO consider where place this
   inet_ntop(p->ai_family, get_in_addr((struct sockaddr *)p->ai_addr),
	     s, sizeof s);	
}

ResponseMessage receive_message (int sockfd)
{
   char buf[MAXDATASIZE];
   if ((numbytes = recv(sockfd, buf, MAXDATASIZE-1, 0)) == -1) {
      perror("recv");
      exit(1);
   }
   buf[numbytes] = '\0';
   return ResponseMessage(buf);
}

void send_message (char buf* ,int sockfd )
{
   int bytessent;
   if ((bytessent=send(sock_fd, buf, strlen(buf), 0)) == -1)
      perror("send");
}

int main(int argc, char *argv[])
{
   int sockfd, numbytes;  
	
   struct addrinfo hints, *servinfo, *p;
   int rv;
   char s[INET6_ADDRSTRLEN];

   init_client ( &hints, &servinfo, node);
   bind_socket ( p,  servinfo, sockfd);

   printf("client: connecting to %s\n", s);
   //TODO maybe move inside bindsocket
   freeaddrinfo(servinfo); // all done with this structure
   //send
   send_message (char buf* ,int sockfd );
   //recv
   ResponseMessage message=receive_message (sockfd);
	
   //printf("client: received '%s'\n",buf);
   close(sockfd);
   return 0;
}

