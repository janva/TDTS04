/**
 *  
 * Filename:     proxyserver.cpp
 * Date:	 2015-03-23	
 * Description:  Entry point of proxyserver application.
 *               To run proxyserver use
 *
 *               ./proxyserver or
 *
 *               or if you want to choose port use
 *
 *               ./proxyserver NNNN
 *
 *                where NNNN is valid portnumber. Note if wrong number
 *                of arguments is given we consider user not knowning
 *                we she/he is doing and use the default port :-)
 */
#include "server.h"
#include "client.h"
#include <iostream>
#include <memory>

using std::cout;
using std::endl;

int main(int argc, char *argv[])
{
   std::unique_ptr<Server> server;
   //7) if port number is given as argument then use that port instead
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
}
