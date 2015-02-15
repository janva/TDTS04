#include "RequestMessage.h"
#include <string>
using namespace std;
int main(int argc, char *argv[])
{
   string request { "GET /hello.htm HTTP/1.1\r\nUser-Agent: curl/7.16.3libcurl/7.16.3 OpenSSL/0.9.7l zlib/1.2.3\r\n Host: www.example.com\r\nAccept-Language: en, mi\r\nDate: Mon, 27 Jul 2009 12:28:53 GMT\r\nServer: Apaceh\r\nLast-Modified: Wed, 22 Jul 2009 19:15:56 GMT\r\nETag: \"34aa387-d-1568eb00\"\r\n Accept-Ranges: bytes\r\nContent-Length: 51\r\n Vary: Accept-Encoding\r\nContent-Type: text/plain\r\n\r\nentitybody comes here dont know when it ends"};

   RequestMessage rq;
   RequestMessage rq2 (request);
   
   //rq.get_request_line () ;
   return 0;
}
