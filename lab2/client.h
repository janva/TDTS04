/**
 *  
 * Filename:     client.h
 * Date:	 2015-03-23	
 * Description:  Client handles traffic between internal proxy client (browser) to internal
 *               server-part of proxysserver.
 */
#ifndef CLIENT_H
#define CLIENT_H
#include "ResponseMessage.h"
#include "RequestMessage.h"

class Client
{
public:
    Client() {};
   //forward incomming messages. If messages or address contains no
   //dissallowed words then this we perform the request on behalf
   //of client else it will redirect to error page.
   ResponseMessage forward (RequestMessage& reqMess);
   //auxilary function just sets up connection by calling init_cient  and connect_socket.
   void setup (std::string host);
   virtual ~Client(){};
private:
   //initias addrinfo structure. resolvning addresses (dns lookup) is handled
   // for us inside this function by calling getaddrinfo.
   void init_client (const char* node);
   //connect to to server throught socket.
   void connect_socket ();
   //sends request to server
   void send_message (const char* message );
   //waits and receives messages from server.
   ResponseMessage receive_message ();
   //releases the socket 
   void close_socket ();
   //Is there any illegal words in the response
   bool is_valid(ResponseMessage respMsg);
   //is there any illegal words in request addressfield
   bool is_valid(RequestMessage reqyMsg);
   //socket filedescriptor
   //(each process keeps track of files by concept of filedescriptors
   //in linux these are presented to the "client" as ints)
   int sockfd;
   //struct used to set up connection
   struct addrinfo *servinfo;
   //we use port 80 for http 
   const char* PORT {"80"};
   //Max buffer size
   static constexpr unsigned int MAX_SIZE {4096};
   //get destination address in convienient way which hides details.
   void* get_in_addr(struct sockaddr *sa);
};

#endif /* CLIENT_H */
