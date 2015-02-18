#ifndef SERVER_H
#define SERVER_H
class Server
{
public:
   //inlined by default when defined here
   Server(){}
   //TODO think thtrough what to pass in and waht should be members
   void init ();
   void bind_socket ();
   void listen_socket ();
   // TODO: move to separate class
   void kill_all_zombies ();
   void dummy_dumbo_change_me ();
   virtual ~Server(){};
private:
  const unsigned int BACKLOG{10};
   const char* PORT= "3490";
   constexpr static  unsigned int MAXDATASIZE =1024 ;
   int sockfd;

   // TODO:  
   int new_fd;
   struct addrinfo *servinfo;
   
   void* get_in_addr(struct sockaddr *sa);
};

#endif /* SERVER_H */