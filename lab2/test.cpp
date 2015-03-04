#include "RequestMessage.h"
#include "ResponseMessage.h"
#include <string>
#include <iostream>
#include <iterator>
#include <algorithm>
using namespace std;
template<typename T>
void print_vector(const vector<T>& vec)
{
   copy (vec.begin(), vec.end(), ostream_iterator<char>( cout," "));
}
int main(int argc, char *argv[])
{
   string request { "GET /hello.htm HTTP/1.1\r\nUser-Agent: curl/7.16.3libcurl/7.16.3 OpenSSL/0.9.7l zlib/1.2.3\r\n Host: www.example.com\r\nAccept-Language: en, mi\r\nDate: Mon, 27 Jul 2009 12:28:53 GMT\r\nServer: Apaceh\r\nLast-Modified: Wed, 22 Jul 2009 19:15:56 GMT\r\nETag: \"34aa387-d-1568eb00\"\r\n Accept-Ranges: bytes\r\nContent-Length: 51\r\n Vary: Accept-Encoding\r\nContent-Type: text/plain\r\n\r\nentitybody comes here dont know when it ends"};

   const char* response ="HTTP/1.1 200 OK\r\nServer: Apache\r\nX-Content-Type-Options: nosniff\r\nX-Analytics: page_id=902605;ns=100\r\nContent-Language: sv\r\nContent-Encoding: gzip\r\nx-ua-compatible: IE=Edge\r\nVary: Accept-Encoding,Cookie\r\nX-Powered-By: HHVM/3.3.1\r\nLast-Modified: Sat, 28 Feb 2015 04:50:22 GMT\r\nContent-Type: text/html; charset=UTF-8\r\nX-Varnish: 3090692437, 1247920399 1247805754, 1386115147 1360489617\r\nVia: 1.1 varnish, 1.1 varnish, 1.1 varnish\r\nContent-Length: 33\r\nAccept-Ranges: bytes\r\nDate: Sat, 28 Feb 2015 17:09:57 GMT\r\nAge: 44374\r\nConnection: keep-alive\r\nX-Cache: cp1065 miss (0), amssq59 hit (22), amssq52 frontend hit (828)\r\nCache-Control: private, s-maxage=0, max-age=0, must-revalidate\r\n\r\nsome oridinary text was faked here";

   
    string cpp_str_resp=  "HTTP/1.1 200 OK\r\nServer: Apache\r\nX-Content-Type-Options: nosniff\r\nX-Analytics: page_id=902605;ns=100\r\nContent-Language: sv\r\nContent-Encoding: gzip\r\nx-ua-compatible: IE=Edge\r\nVary: Accept-Encoding,Cookie\r\nX-Powered-By: HHVM/3.3.1\r\nLast-Modified: Sat, 28 Feb 2015 04:50:22 GMT\r\nContent-Type: text/html; charset=UTF-8\r\nX-Varnish: 3090692437, 1247920399 1247805754, 1386115147 1360489617\r\nVia: 1.1 varnish, 1.1 varnish, 1.1 varnish\r\nContent-Length: 33\r\nAccept-Ranges: bytes\r\nDate: Sat, 28 Feb 2015 17:09:57 GMT\r\nAge: 44374\r\nConnection: keep-alive\r\nX-Cache: cp1065 miss (0), amssq59 hit (22), amssq52 frontend hit (828)\r\nCache-Control: private, s-maxage=0, max-age=0, must-revalidate\r\n\r\nsome oridinary text was faked here";

    vector<char> vresponse;

    for (char ch: cpp_str_resp)
    {
       vresponse.push_back(ch);
    }

    //  print_vector(vresponse);
    //RequestMessage rq;
    ResponseMessage rp;    //  RequestMessage rq2 (request);
    // rp.get_content_length(response);

    print_vector(vresponse);
    rp.set_status_line_3_(vresponse);
    rp.set_headars_3_(vresponse);
    rp.set_entity_3_(vresponse);

    
    string res =rp.to_str();
    cout << "res: "<< res <<endl;
    

//    cout << "message size is:" <<   rp.get_message_size(vresponse) <<endl;
//    cout << "size of vector is:" <<  vresponse.size() <<endl;
//    cout << "string size is: " << cpp_str_resp.size() << endl;
    //rq.get_request_line () ;
    return 0;
}
