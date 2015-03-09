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
#include "ContentValidator.h"

using std::cout;
using std::endl;
using std::vector;
using std::string;

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
       
       fprintf(stderr, "getaddrinfo@node:%s\n %s\n", node, gai_strerror(rv));
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

bool Client::is_valid(ResponseMessage respMsg)
{
   //text/plain or text/html
   std::string type {respMsg.get_header("Content-Type")};
   if (type == "text/plain" ||type == "text/html")
   {
      PRINT_DEBUG(type);
      Validator validate("forbidden.txt");
      return validate(respMsg.get_entity_body());
   }
   return true; 
}


//ResponseMessage  Client::redirect()
//{
//
//}
ResponseMessage Client::forward (RequestMessage&  reqMsg) 
{
    reqMsg.set_header("Connection","Close");
    std::string reqMsgCppStr = reqMsg.to_str();
    char*  reqMsgCStr = new char[reqMsgCppStr.length ()+1];    
    strcpy (reqMsgCStr, reqMsgCppStr.c_str());
    send_message (reqMsgCStr);
    //recv
    ResponseMessage respMsg=receive_message_2();
    if ( is_valid(respMsg))
    {
       PRINT_DEBUG("valid content");
    }else
    {
       //redirect ();
    }      
    delete[]reqMsgCStr;
    //PRINT_DEBUG(respMsg.to_str());
    return respMsg;
}

//int Client::recvtimeout(int s, char *buf, int len, int timeout)
//@deprecated 
//int Client::recvtimeout(int s, vector<char>& buf, int len, int timeout)
//{
//    fd_set fds;
//    int n;
//    struct timeval tv;
//    vector<char> bigbuf;
//    ResponseMessage returnmessage;
//// set up thie file descriptor set
//    FD_ZERO(&fds);
//    FD_SET(s, &fds);
//    // set up the struct timeval for the timeout
//    tv.tv_sec = timeout;
//    tv.tv_usec = 0;
//    // wait until timeout or data received 
//    n= select(s+1, &fds, NULL, NULL, &tv); 
//    if (n == 0) return -2; // timeout!
//    if (n == -1) return -1; // error
//    // data must be here, so do a normal recv()
//    int numbytes = 1;
//    while((numbytes=recv(s, &buf[0], buf.size(), 0)) >0 ){
//	buf.resize(numbytes);
//	bigbuf.insert(bigbuf.end(), buf.begin(), buf.end());
//	buf.resize(MAXDATASIZE);
//    }
//    PRINT_DEBUG("bigbuf: ");
//    int z =0;
//    for(auto i : bigbuf){
//	++z;
//	std::cout << i;
//    }
//    std::cout << std::endl << "end " << z << std::endl;
//    return 0;
////    return recv(s, &buf[0], len, 0); 
//}

ResponseMessage  Client::receive_message_2 ()
{
    int  numbytes=1;    
    //int total = 0; 
    int n = 0;
    //char buf[MAXDATASIZE];
    vector<char> buf(MAXDATASIZE);;
    vector<char> bigbuf(0);
    //numbytes = recvtimeout(sockfd, buf, sizeof buf, 10); // 10 second timeout   
    while((numbytes=recv(sockfd, &buf[0], buf.size(), 0)) >0 ){
       buf.resize(numbytes);
	bigbuf.insert(bigbuf.end(), buf.begin(), buf.end());
	buf.clear();
	buf.resize(MAXDATASIZE);
    }
    //int z =0;
    //for(auto i : bigbuf){
    //	++z;
    //	std::cout << i;
    //}
    //std::cout << std::endl << "end " << z << std::endl;
    //all done set upp ResponseMessage
    ResponseMessage response_message_3(bigbuf);
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
