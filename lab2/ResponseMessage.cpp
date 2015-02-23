#include"ResponseMessage.h"
#include<string>
#include<iostream>
#include<sstream>
#include<vector>
#include<map>
#include<sstream>
#include "debug.h"
#include <string.h>
#include "debug.h"


using std::string;
using std::vector;
using std::cout;
using std::endl;
using std::istringstream;
using std::map;
using std::ostringstream;


ResponseMessage::ResponseMessage()
   :  status_line_ (""),header_fields_(),entity_body_ ("Some content will go here")
{}

ResponseMessage::ResponseMessage(const string& response)
   :  status_line_ (""),header_fields_(),entity_body_ ("Some content will go here")
{
   init_ (response);
 
}
string ResponseMessage::get_status_line () const
{
   return status_line_;
}

//string ResponseMessage::get_header (const string& field_name) const
//use typedefs
string ResponseMessage::get_header ( const string& field_name) const
{
   // TODO: create own iterator type for ResponseMessage
   std::map<const string,string>::const_iterator it;
    it= header_fields_.find (field_name);
    return   (it != header_fields_.end()  ? it->second: "0");
}

string ResponseMessage::get_entity_body () const
{
   return entity_body_;
}


void ResponseMessage::set_status_line (const string& status_line)
{
   status_line_ = status_line;
}

void ResponseMessage::set_header (const string& field, const string& value)
{
  header_fields_[field] = value;
}

ResponseMessage::~ResponseMessage()
{
   
}

void ResponseMessage::init_(string response_message)
{
   istringstream resp_stream {response_message};
   vector <string>lines{};
   string line{};
   
   while (resp_stream)
   {
      getline (resp_stream,line,'\n');
      lines.push_back (line) ;
      //if(line == "\r\n")
   }
   
   status_line_ = lines.at (0);

   //fill headers
   for (auto it=lines.begin () +1; it != lines.end (); ++it)
   {
       auto  idx =  (*it).find (':');
      if(idx == string::npos)
      {
	  entity_body_.clear();
	  for(auto it2=it+1; it2!=lines.end(); ++it2)
	     entity_body_+= *it2 +"\n";
	  break;
      }
      
	
      string field{(*it).begin(),   (*it).begin()+idx};
      string value {it->begin()+idx+2, it->end ()-1};
      
	 
      header_fields_[field] =value;
      
   }

   //last part is entity line
   std::cout << "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" << std::endl;
   std::cout << "lines.size(): " << lines.size() << std::endl;
   int index = 0;

   for (auto i : lines){
       int value;
       std::cout << "lines@" << index <<"(" << i.size() << ")" << ": " << i << std::endl;
       for (auto b : i){
	   value = b;
	   cout << value << " | ";
       }
       std::cout << std::endl << std::endl;
	   
       ++index;
   }

   //entity_body_=lines[lines.size ()-1];
   //  cout <<entity_body_ <<endl;
//   cout <<header_fields_.size ()<<endl;
//   cout <<header_fields_["Date"]<<endl;
//   
}
std::string ResponseMessage::to_str()
{
   ostringstream message;
   // TODO: this line is the only that differs factor out
   // template method pattern or simply use template
   message << (get_status_line())<<"\n";
   for (auto header : header_fields_)
   {
      message <<header.first<<": " <<header.second<<"\n";
   }
   message<<"\r\n";
   if(!(entity_body_.empty()) )
      message<<entity_body_;
   //strcpy (mess ,((message.str()).c_str()));
   return message.str();
}
