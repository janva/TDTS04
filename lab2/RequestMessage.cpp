#include"RequestMessage.h"
#include<string>
#include<iostream>
#include<sstream>
#include<vector>
#include<map>
#include <sstream>
#include <string.h>
#include "debug.h"
using std::string;
using std::vector;
using std::cout;
using std::endl;
using std::istringstream;
using std::map;
using std::ostringstream;


RequestMessage::RequestMessage()
   :  request_line_ (""),header_fields_(), entity_body_ ("")
{}

RequestMessage::RequestMessage(const string& request)
   :  request_line_ (""),header_fields_(),entity_body_ ("")
{
   init_ (request);
}
string RequestMessage::get_request_line () const
{
   return request_line_;
}

string RequestMessage::get_header (const string& field_name) const
{
   PRINT_DEBUG("----------client request-----------------");
  // TODO: handle errors element might not exist
   return  header_fields_.at(field_name);
}

string RequestMessage::get_entity_body () const
{
   return entity_body_;
}


void RequestMessage::set_request_line (const string& req_line)
{
   request_line_ = req_line;
}

void RequestMessage::set_header (const string& field, const string& value)
{
   header_fields_[field] = value;
}

RequestMessage::~RequestMessage()
{
   
}
//privates
void RequestMessage::init_(string request)
{
   istringstream req_stream {request};
   vector <string>lines{};
   string line{};
   
   while (req_stream)
   {
      getline (req_stream,line, '\n');
      lines.push_back (line) ;	 
   }
   request_line_ = lines.at (0);
   
  
   for (auto it=lines.begin () +1; it != lines.end (); ++it)
   {
      
      //  auto  idx =  (*it).find (':');
 
      //
      //  string field{(*it).begin(),   (*it).begin()+idx};
      //  string value {it->begin()+idx+2, it->end ()-1};
      //  header_fields_[field] =value;
      auto  idx =  (*it).find (':');
       if(idx == string::npos)
       {
	  break;
       }
      //TODO  we allways seem to get some trash in entitybody which is
      // not suppose to be there
     if(idx == string::npos)
     {
     	 entity_body_.clear();
     	 for(auto it2=it+1; it2!=lines.end(); ++it2)
     	    entity_body_+= *it2 ;//+"\n";
     	 break;
     }
      
      string field{(*it).begin(),   (*it).begin()+idx};
      string value {it->begin()+idx+2, it->end ()-1};
      header_fields_[field] =value;
   }
  //entity_body_.clear();
  //auto end_of_headers = request.find("\r\n\r\n");
  //auto start_entity =request.begin() +end_of_headers+4;
  //if (start_entity != request.end())
  //{
  //string temp{start_entity, request.end()}; 
  //entity_body_=temp;
  //}
   
}

std::string RequestMessage::to_str ()
{
   ostringstream message;
   message << (get_request_line())<<"\n";
   for (auto header : header_fields_)
   {
      message <<header.first<<": " <<header.second<<"\n";
   }
   message<<"\r\n";
   if(!(entity_body_.empty()) )
   {
      PRINT_DEBUG(entity_body_);
      message<<entity_body_;
   }
   // cout << "const char* RequestMessage::to_cstr ()" <<endl;
   // cout <<message.str();
   return message.str();
   //char* from=(message.str()).c_str();
   // strcpy (mess,from);
            
   //  return "GET /tutorials/other/top-20-mysql-best-practices/ HTTP/1.1\r\nHost: net.tutsplus.com\r\nUser-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)\r\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\nAccept-Language: en-us,en;q=0.5\r\nAccept-Encoding: gzip,deflate\r\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7\r\nKeep-Alive: 300\r\nConnection: keep-alive\r\nCookie: PHPSESSID=r2t5uvjq435r4q7ib3vtdjq120\r\nPragma: no-cache\r\nCache-Control: no-cache\r\n\r\n";
}
