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
   //PRINT_DEBUG(reqMsgCStr);
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
      // PRINT_DEBUG(buf);
      PRINT_DEBUG(" got some data in buf");
   }
  
   unsigned int totalReceivedBytes=numbytes;
   //TODO might still fail
   ResponseMessage response_message(buf);
   ResponseMessage response_message_3(buf);
  
   //  int entity_body_size =  response_message.get_content_length(buf);
   int expected_total_size =  response_message_3.get_message_size(buf);

//   int expected_total_size;
   // char* end_of_headers;

//   if( ( end_of_headers= strstr(buf,"\r\n\r\n")) != NULL)
//   {
//      PRINT_DEBUG("END Delimiter found ");
//      //PRINT_DEBUG (end_of_headers);
//      //PRINT_DEBUG( (end_of_headers - buf) );   
//      //PRINT_DEBUG(totalReceivedBytes); 
//   }
//   //+ 4 to compensate for \r\n\r\n

   // expected_total_size = end_of_headers - buf + entity_body_size +4;
   //buf[numbytes]='\0';

  //  char complete_message[expected_total_size];
  // memcpy (complete_message,buf, numbytes);
   
   //case all wasn't received at once
   while(totalReceivedBytes < expected_total_size)
   {
      //get more data when ready todo error ctrl
      numbytes = recvtimeout(sockfd, buf, MAXDATASIZE, 10); // 10 second timeout
      total_message_buf.insert (total_message_buf.end(), buf.begin(), buf.end() );
      //memcpy (complete_message+totalReceivedBytes,buf, numbytes);
      totalReceivedBytes +=  numbytes;
      // ska jag verkligen lägga på dessa här troligen bara för utskrifterna ska funka
      //buf[numbytes] = '\0';
      //copy stuff to endo buff
      //message += buf;
      //PRINT_DEBUG(totalReceivedBytes);
   }

   //all done set upp ResponseMessage
   response_message.set_entity_3_(total_message_buf);
   //response_message.set_raw(complete_message, expected_total_size);
   return response_message;
}

//ResponseMessage Client::receive_message ()
//{
//   int  numbytes=0;     
//   char buf[MAXDATASIZE];
//   //non blocking socket
//   //unsigned long nonblock = 1;
//   fcntl(sockfd, F_SETFL, O_NONBLOCK);
//   numbytes = recvtimeout(sockfd, buf, sizeof buf, 10); // 10 second timeout
//   if (numbytes == -1) {
//   // error occurred
//      perror("recvtimeout");
//   }
//   else if (numbytes == -2) {
//   // timeout occurred
//   cout << "timed out";
//   exit(-1);
//   } else {
//      // PRINT_DEBUG(buf);
//      PRINT_DEBUG(" got some data in buf");
//   }
//   
//   // the way to wait for all data when size is known
//   //numbytes = recv(sockfd, buf, MAXDATASIZE-1, MSG_WAITALL);
//
//   // bytes received +=
//
//  //   if ((numbytes = recv(sockfd, buf, MAXDATASIZE-1, 0)) == -1) {
//  //	 perror("recv");
//  //	 exit(1);
//  //   }
//  //   
//   unsigned int totalReceivedBytes=numbytes;
//   ResponseMessage  response_message{buf};
//   //PRINT_DEBUG(numbytes);
//   //TODO might fail if we don't get this content,
//   // int entity_body_size =std::stoi( response_message.get_header ("Content-Length"));
//   int entity_body_size =  response_message.get_content_length(buf);
//   int expected_total_size;
//   //for testing stackoverflow
//   //unsigned int expectedSize =148;
//   char* end_of_headers;
//   if( ( end_of_headers= strstr(buf,"\r\n\r\n")) != NULL)
//   {
//      cout <<"Cathiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiing"<<endl;
//      //PRINT_DEBUG (end_of_headers);
//      //PRINT_DEBUG( (end_of_headers - buf) );   
//      //PRINT_DEBUG(totalReceivedBytes); 
//   }
//   //+ 4 to compensate for \r\n\r\n
//   expected_total_size = end_of_headers -buf +entity_body_size +4;
//   //PRINT_DEBUG(expected_total_size);
//
//   buf[numbytes]='\0';
//   std::string message{buf};
//
//  char message2[expected_total_size];
//  memcpy (message2,buf, numbytes);
//   
//   while(totalReceivedBytes < expected_total_size)
//   {
//    // if ((numbytes = recv(sockfd, buf, MAXDATASIZE-1, 0)) == -1) {
//    //	 perror("recv");
//    //	 exit(1);
//    // }
//      numbytes = recvtimeout(sockfd, buf, sizeof buf, 10); // 10 second timeout
//
//      memcpy (message2+totalReceivedBytes,buf, numbytes); 
//      totalReceivedBytes +=  numbytes;
//      buf[numbytes] = '\0';
//      message += buf;
//   }
//   PRINT_DEBUG( expected_total_size); 
//   //PRINT_DEBUG (end_of_headers);
//   //PRINT_DEBUG( (end_of_headers - buf) );   
//   PRINT_DEBUG(totalReceivedBytes); 
//   //  PRINT_DEBUG(message);
//   //  PRINT_DEBUG(numbytes);
//
//   ResponseMessage resp {message};
//   message2[totalReceivedBytes-1] ='\0';
//   //PRINT_DEBUG(message2);
//   //resp.set_entity_2_(message2, (end_of_headers -buf + 4) , entity_body_size  );
//   // TODO: hmm by copy so should not be problem?
//   return resp;
//}

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
