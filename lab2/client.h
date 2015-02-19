#ifndef CLIENT_H
#define CLIENT_H
#include "ResponseMessage.h"
#include "RequestMessage.h"

class Client
{
public:
   Client() {};
   ResponseMessage forward (RequestMessage& reqMess);
   void init_client (const char* node);
   void bind_socket ();
   void send_message (char* message );
   ResponseMessage receive_message ();
   void close_socket ();
   void setup (std::string host);
   
   virtual ~Client(){};
private:
   int sockfd;
   struct addrinfo *servinfo;
   const char* PORT {"3495"};
   static constexpr unsigned int MAXDATASIZE {100};
   // TODO: tempfix
   void* get_in_addr(struct sockaddr *sa);
};


#endif /* CLIENT_H */
