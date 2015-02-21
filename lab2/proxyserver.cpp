#include "server.h"
#include "client.h"
#include <iostream>
#include <memory>

using std::cout;
using std::endl;

//use arguements
int main(int argc, char *argv[])
{
   std::unique_ptr<Server> server;
   if (argc==2)
   {
      server =std::unique_ptr<Server>{new Server{argv[1]}};
   }
   else
   {
      cout <<"Starting server using PORT=3490 as default" << endl ;
      server =std::unique_ptr<Server>{new Server{}};
   }
      
   server->run();
   cout << "server started" << endl;

   
// serv.init ( );
// serv.bind_socket ();
// serv.listen_socket ();
   //change these 
   // serv.kill_all_zombies ();
   // serv.dummy_dumbo_change_me ();
   
   // s.setup ();
   // s.start ();
   // s.setclient ();   
   // Client c{};
}
