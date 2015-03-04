#include "client.h"
#include "ResponseMessage.h"
#include <string>
#include <cstring>
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <netdb.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <vector>
#include <sys/fcntl.h>
#include "debug.h"

using std::cout;
using std::endl;
using std::vector;


// get sockaddr, IPv4 or IPv6:
void* Client::get_in_addr(struct sockaddr *sa)
{
   if (sa->sa_family == AF_INET)
   {
      return &(((struct sockaddr_in*)sa)->sin_addr);
   }

   return &(((struct sockaddr_in6*)sa)->sin6_addr);
}

void Client::init_client ( const char* node)
{
   PRINT_DEBUG(node);
   struct addrinfo hints;
   int rv;
   // PRINT_DEBUG(node);
   memset(&hints, 0, sizeof hints);
   hints.ai_family = AF_UNSPEC;
   hints.ai_socktype = SOCK_STREAM;

   if ((rv = getaddrinfo(node , PORT,   &hints, &servinfo)) != 0) {
      fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
      exit (1);
      //return 1;
   }
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
   
   if (p == NULL) {
      fprintf(stderr, "client: failed to connect\n");
      exit (2);
      //return 2;
   }
   inet_ntop(p->ai_family, get_in_addr((struct sockaddr *)p->ai_addr),
	     s, sizeof s);
   printf("client: connecting to %s\n", s);
   freeaddrinfo(servinfo); // all done with this structure
}


ResponseMessage Client::forward (RequestMessage&  reqMsg) 
{
   reqMsg.set_header("Connection","Close");
   std::string reqMsgCppStr = reqMsg.to_str();
   char*  reqMsgCStr = new char[reqMsgCppStr.length ()+1];    
   strcpy (reqMsgCStr, reqMsgCppStr.c_str());
   PRINT_DEBUG(reqMsgCStr);
   send_message (reqMsgCStr);
   
   
   //recv
   // ResponseMessage respMsg=receive_message_ ();
   ResponseMessage respMsg=receive_message_2();
   delete[]reqMsgCStr;
   //PRINT_DEBUG(respMsg.to_str());
   return respMsg;
}

//int Client::recvtimeout(int s, char *buf, int len, int timeout)
int Client::recvtimeout(int s, vector<char>& buf, int len, int timeout)
{
   fd_set fds;
   int n;
   struct timeval tv;
// set up thie file descriptor set
   FD_ZERO(&fds);
   FD_SET(s, &fds);
   // set up the struct timeval for the timeout
   tv.tv_sec = timeout;
   tv.tv_usec = 0;
   // wait until timeout or data received 
   n= select(s+1, &fds, NULL, NULL, &tv); 
   if (n == 0) return -2; // timeout!
   if (n == -1) return -1; // error
  // data must be here, so do a normal recv()
    return recv(s, &buf[0], len, 0); 
}

ResponseMessage  Client::receive_message_2 ()
{
   int  numbytes=0;     
   //char buf[MAXDATASIZE];
   vector<char> buf;
   vector<char> total_message_buf;
   //Non blocking socket
   fcntl(sockfd, F_SETFL, O_NONBLOCK);
   //numbytes = recvtimeout(sockfd, buf, sizeof buf, 10); // 10 second timeout
   numbytes = recvtimeout(sockfd, buf, sizeof buf, 10); // 10 second timeout
   
   if (numbytes == -1) {
   // error occurred
      perror("some error occured");
   }
   else if (numbytes == -2) {
   // timeout occurred
   cout << "timed out";
   exit(-1);
   } else {
      PRINT_DEBUG(" got some data in buf");
   }
  
   unsigned int totalReceivedBytes=numbytes;
   //TODO might still fail
   ResponseMessage response_message(buf);
   ResponseMessage response_message_3(buf);

   int expected_total_size =  response_message_3.get_message_size(buf);
   //case all wasn't received at once
   while(totalReceivedBytes < expected_total_size)
   {
      //get more data when ready todo error ctrl
      numbytes = recvtimeout(sockfd, buf, MAXDATASIZE, 10); // 10 second timeout
      total_message_buf.insert (total_message_buf.end(), buf.begin(), buf.end() );
      totalReceivedBytes +=  numbytes;
   }

   //all done set upp ResponseMessage
   response_message_3.init_3 (total_message_buf);
   return response_message_3;
}

void Client::send_message (const char* buf)
{
   int bytessent;
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
