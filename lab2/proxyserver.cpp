#include "server.h"
#include "client.h"
#include <iostream>

using std::cout;
using std::endl;

//use arguements
int main ()
{
   
   Server serv{};
   serv.run();
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
