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
	PRINT_DEBUG("ALIVE");
      
	return validate(respMsg.get_entity_body());
	//return true;
    }
    return true; 
}

bool Client::is_valid(RequestMessage reqMsg)
{
    //text/plain or text/html
    //std::string type {respMsg.get_header("Content-Type")};
//   if (type == "text/plain" ||type == "text/html")
    //{
    // PRINT_DEBUG(type);
    Validator validate("forbidden.txt");
    PRINT_DEBUG(reqMsg.get_header("Host"));
    PRINT_DEBUG(reqMsg.get_request_line());
		
    // GET and HTTP/1.1 in forbidden.txt would forbid any website (same is true for an empty line)
    return validate(reqMsg.get_request_line());
    //return true;
   
		   
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
    //rec
    ResponseMessage respMsg=receive_message_2();
    delete[]reqMsgCStr;
    
    if (is_valid(respMsg) && is_valid(reqMsg))
    {
       
	PRINT_DEBUG("valid content");
	return respMsg;
    }
   
    ResponseMessage redirected_resp{};
   
    std::string ReDirStatusLine{"HTTP/1.1 302 Found\r"};
   
    std::string LocHeader{"http://www.ida.liu.se/~TDTS04/labs/2011/ass2/error2.html\r"};
    redirected_resp.set_status_line_3_(ReDirStatusLine);
    redirected_resp.set_header("Location",LocHeader);
   
//   redirected_resp.set_message_size_3_(ReDirStatusLine.size() + LocHeader.size() + 4);
    redirected_resp.set_message_size_3_(120);

    PRINT_DEBUG("aLIVE");
    return redirected_resp;
    //PRINT_DEBUG(respMsg.to_str());

}

ResponseMessage  Client::receive_message_2 ()
{
    int  numbytes=1;    
    //int total = 0; 
//    int n = 0;
    //char buf[MAXDATASIZE];
    vector<char> buf(MAXDATASIZE);;
    vector<char> bigbuf(0);
   
    while((numbytes=recv(sockfd, &buf[0], buf.size(), 0)) >0 ){
	buf.resize(numbytes);
	bigbuf.insert(bigbuf.end(), buf.begin(), buf.end());
	buf.clear();
	buf.resize(MAXDATASIZE);
    }
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
