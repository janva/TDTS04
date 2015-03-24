#include"RequestMessage.h"
#include<string>
#include<iostream>
#include<sstream>
#include<vector>
#include<map>
#include <string.h>
#include "debug.h"

using std::string;
using std::vector;
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
      auto  idx =  (*it).find (':');
       if(idx == string::npos)
       {
	  break;
       }
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
      message<<entity_body_;
   }
  
   return message.str();
}
