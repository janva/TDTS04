#include "client.h"
#include "ResponseMessage.h"
#include <string>
#include <iostream>
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


// TODO: make static constexpr  data 
// get sockaddr, IPv4 or IPv6:
void* Client::get_in_addr(struct sockaddr *sa)
{
   if (sa->sa_family == AF_INET) {
      return &(((struct sockaddr_in*)sa)->sin_addr);
   }

   return &(((struct sockaddr_in6*)sa)->sin6_addr);
}


//TODO needs host as argument
//TODO close fit with server.cpp version pattaren?
void Client::init_client ( const char* node)
{
   struct addrinfo hints;
   int rv;
   
   memset(&hints, 0, sizeof hints);
   hints.ai_family = AF_UNSPEC;
   hints.ai_socktype = SOCK_STREAM;
   //TODO not so good place tohave node

//   if ((rv = getaddrinfo(node , PORT,   &hints, &servinfo)) != 0) {
   if ((rv = getaddrinfo("www.ida.liu.se" , PORT,   &hints, &servinfo)) != 0) {
      fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
      exit (1);
      //return 1;
   }

  
   
}

ResponseMessage Client::forward (RequestMessage&  reqMess) 
{
   using std::cout;
   using std::endl;
   using std::string;
   //send
   char* ch=reqMess.to_cstr();
   cout << ch <<std::endl;
   send_message (reqMess.to_cstr());                          
   //recv                                            
   ResponseMessage message= receive_message ();
   cout << message.get_status_line () <<std::endl;
   return message;
   //printf ("Client::forward - under construction -");
}
//TODO close fit with server version simplest solution parameters
// or adapter or template pattern strategy
void Client::bind_socket ()
{
   addrinfo* p;
   char s[INET6_ADDRSTRLEN];
   
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
   std::cout<< "-----------------------------2"<<std::endl;
   if (p == NULL) {
      fprintf(stderr, "client: failed to connect\n");
      exit (2);
      //return 2;
   }
   //TODO consider where place this
   inet_ntop(p->ai_family, get_in_addr((struct sockaddr *)p->ai_addr),
	     s, sizeof s);
   printf("client: connecting to %s\n", s);
   //TODO maybe move inside bindsocket
   freeaddrinfo(servinfo); // all done with this structure
}

ResponseMessage Client::receive_message ()
{
   int  numbytes;  
   char buf[MAXDATASIZE];
   if ((numbytes = recv(sockfd, buf, MAXDATASIZE-1, 0)) == -1) {
      perror("recv");
      exit(1);
   }
   buf[numbytes] = '\0';
   return ResponseMessage(buf);
}

void Client::send_message (char* buf)
{
   int bytessent;
   std::cout <<"send_message"<<std::endl;
   if ((bytessent=send(sockfd, buf, strlen(buf), 0)) == -1)
      perror("send");
}
void Client::close_socket ()
{
   close(sockfd);
}



void Client::setup (std::string host)
{

   init_client (host.c_str ());
   bind_socket ();
}
//int main(int argc, char *argv[])
//{
//   
//
//   Client client{};
//   struct addrinfo hints, *servinfo, *p;
//   const char* node="http://google.com";
//   client.init_client (node);
//   client.bind_socket ();
//
//   //send
//   client.send_message ( );
//   //recv
//   ResponseMessage message= client.receive_message ();
//	
//   //printf("client: received '%s'\n",buf);
//
//   return 0;
//}


