#include"RequestMessage.h"
#include<string>
#include<iostream>
#include<sstream>
#include<vector>


using std::string;
using std::vector;
using std::cout;
using std::endl;
using std::istringstream;


RequestMessage::RequestMessage()
   :  request_line_ (""), entity_body_ (""),header_fields_()
{}

RequestMessage::RequestMessage(string request)
   :  request_line_ (""),entity_body_ (""),header_fields_()
{
   init_ (request);
}
string RequestMessage::get_request_line () const
{
   return request_line_;
}

std::string  RequestMessage::get_headers () const
{
   
}
std::string RequestMessage::get_headers (string field_name) const
{

}

string RequestMessage::get_entity_body () const
{
   cout << "under construct" << endl;
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
      getline (req_stream,line);
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

      string field{(*it).begin(),   (*it).begin()+idx};
      string value {it->begin()+idx+2, it->end ()};
      header_fields_[field] =value;
   }
   
   entity_body_=lines[lines.size ()-1];
   //  cout <<entity_body_ <<endl;
//   cout <<header_fields_.size ()<<endl;
//   cout <<header_fields_["Date"]<<endl;
//   
}
