/**
 *  
 * Filename:     server.h
 * Date:	 2015-03-23
 *
 * Description:  Server is a class representing server-part of
 *               of proxyserver. handles comunication  between  
 *               between interanal actual client (browser) and
 *               internal proxy-client part.
 *
 */
#ifndef SERVER_H
#define SERVER_H
#include "client.h"

class Server
{
public:
   Server(const char*  port="3490"): PORT{port}{}
   //start up the server instance
   void run ();
   virtual ~Server(){};

private:
   //receives message from client and passes them on to internal server-part
   //as well as wait for response to pass back to client (browser)
   void send_receive ();
   //sets up addrinfo struct to be used to set up connection.
   void init ();
   //bind host socket to ip and port to listen to.
   void bind_socket ();
   //listen on socket for incomming connections
   void listen_socket ();
   //we are spawning new processes for each internal client connections
   // this function will kill zombies which hang around after child processes
   // finnished (in order to not waste resources).
   void kill_all_zombies ();
   //nr of connection allowed to wait on queue to be accapted   
   const unsigned int BACKLOG{10};
   //Port number 
   const char* PORT{"3490"};
   //Message-buffer size
   constexpr static  unsigned int MAX_SIZE =1024 ;
   //socket file descriptor
   int sockfd;
   //socket file descriptor used by child process
   int new_fd;
   //structure for setting up connection (type of socket, protocol etc) 
   struct addrinfo *servinfo;
   //get us destionation address in convenient way hiding all the details
   void* get_in_addr(struct sockaddr *sa);
   // the internal client-part
   Client* client;
};
#endif /* SERVER_H */
