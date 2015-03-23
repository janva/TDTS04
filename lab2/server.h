/**
 *  
 * Filename:     server.h
 * Date:	 2015-03-23
 *
 * Description:  Server is a class representing server-part of
 *               of proxyserver. handles comunication  between  
 *               between interanal proxy-client and actuall
 *               webservers.
 */
#ifndef SERVER_H
#define SERVER_H
#include "client.h"

class Server
{
public:
   Server(const char*  port="3490"): PORT{port}{}
   void run ();
   virtual ~Server(){};

private:
   void send_receive ();
   void init ();
   void bind_socket ();
   void listen_socket ();
   void kill_all_zombies ();
   
   const unsigned int BACKLOG{10};
   const char* PORT{"3490"};
   constexpr static  unsigned int MAX_SIZE =1024 ;
   int sockfd;
   int new_fd;
   struct addrinfo *servinfo;
   void* get_in_addr(struct sockaddr *sa);
   Client* client;
};
#endif /* SERVER_H */
