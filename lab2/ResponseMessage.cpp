#include"ResponseMessage.h"
#include<string>
#include<iostream>
#include<sstream>
#include<vector>
#include<map>


using std::string;
using std::vector;
using std::cout;
using std::endl;
using std::istringstream;
using std::map;


ResponseMessage::ResponseMessage()
   :  status_line_ (""),header_fields_(),entity_body_ ("")
{}

ResponseMessage::ResponseMessage(const string& request)
   :  status_line_ (""),header_fields_(),entity_body_ ("")
{
   init_ (request);
}
string ResponseMessage::get_status_line () const
{
   return status_line_;
}

string  ResponseMessage::get_headers () const
{
   return "under construction-will eventually return string of all headers";
}
string ResponseMessage::get_header (const string& field_name) const
{
   return  header_fields_.at(field_name);
}

string ResponseMessage::get_entity_body () const
{
   cout << "under construct" << endl;
   return entity_body_;
}


void ResponseMessage::set_status_line (const string& req_line)
{
   status_line_ = req_line;
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
      getline (resp_stream,line);
      lines.push_back (line) ;	 
   }
   
   status_line_ = lines.at (0);

   //fill headers
   for (auto it=lines.begin () +1; it != lines.end (); ++it)
   {
      auto  idx =  (*it).find (':');
      if(idx == string::npos)
      {
	 break;
      }

      string field{(*it).begin(),   (*it).begin()+idx};
      string value {it->begin()+idx+2, it->end ()};
      header_fields_[field] =value;
   }

   //last part is entity line
   entity_body_=lines[lines.size ()-1];
   //  cout <<entity_body_ <<endl;
//   cout <<header_fields_.size ()<<endl;
//   cout <<header_fields_["Date"]<<endl;
//   
}
