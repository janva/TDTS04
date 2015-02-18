#ifndef CLIENT_H
#define CLIENT_H
#include "ResponseMessage.h"
class Client
{
public:
   Client() {};
   void init_client ( const char* node);
   void bind_socket ();
   void send_message ( );
   ResponseMessage receive_message ();
   void close_socket ();
   virtual ~Client(){};
private:
   int sockfd;
   struct addrinfo *servinfo;
   const char* PORT {"3490"};
   static constexpr unsigned int MAXDATASIZE {100};
   // TODO: tempfix
   void* get_in_addr(struct sockaddr *sa);
};


#endif /* CLIENT_H */
