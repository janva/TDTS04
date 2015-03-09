#include"ResponseMessage.h"
#include<string>
#include<string.h>
#include<iostream>
#include<sstream>
#include<vector>
#include<map>
#include<sstream>
#include "debug.h"
#include <algorithm>
#include <iterator>


using std::string;
using std::ostream_iterator;
using std::vector;
using std::cout;
using std::endl;
using std::istringstream;
using std::map;
using std::ostringstream;
using std::find;
using std::length_error;
using namespace std;

ResponseMessage::ResponseMessage()
    :  status_line_ (""),header_fields_(),entity_body_3_ ("Some content will go here")
{}

ResponseMessage::ResponseMessage(const std::vector<char>& response)
{
    init_3 (response);
}

string ResponseMessage::get_status_line () const{
    return status_line_;
}


string ResponseMessage::get_header ( const string& field_name) const
{
    // TODO: create own iterator type for ResponseMessage
    std::map<const string,string>::const_iterator it;
    it= header_fields_.find (field_name);
    return   (it != header_fields_.end()  ? it->second: "0");
}

string ResponseMessage::get_entity_body () const
{
    return entity_body_3_;
}

void ResponseMessage::set_header (const string& field, const string& value)
{
    header_fields_[field] = value;
}

ResponseMessage::~ResponseMessage()
{
   
}

void ResponseMessage::set_status_line_3_(const vector<char>& message)
{
    auto end_of_status_line= find(message.begin(),message.end(), '\r');
    status_line_={message.begin(), end_of_status_line};
    
    PRINT_DEBUG(status_line_);
}

void ResponseMessage::set_entity_3_(const vector<char>& message)
{
    vector<char> delimiter {'\r','\n','\r','\n'};
    auto begin_delimiter =
	search (message.begin(), message.end(), delimiter.begin(), delimiter.end());
    if(begin_delimiter != message.end())
    {
	//skip past deilimter
	try {
	    begin_delimiter++;
	    begin_delimiter++;
	    begin_delimiter++;
	    begin_delimiter++;
	}catch(length_error& e)
	{
	    cout<<"ResponseMessage::set_entity_3_(const vector<char>& message) :"<<
		e.what();
	}
	entity_body_3_ = {begin_delimiter, message.end()};
      //  copy (begin_delimiter, message.end(), back_inserter(entity_body_3_));
	// copy (entity_body_3_.begin(),entity_body_3_.end(), ostream_iterator<char>(cout, ""));
    }
}

// TODO: continue here all messed upp right now
void ResponseMessage::set_headars_3_(const vector<char>& response_message)
{
 
    string field;
    string value;
    
    auto begin_of_headers = find (response_message.begin(),response_message.end(),'\n');
    ++begin_of_headers;
   

    string searchString{"\n\r\n"};
    auto end_of_headers = search(begin (response_message), end(response_message),
				 begin(searchString), end(searchString));
    PRINT_DEBUG("before ??????????????????????????");   
    
    string headers_string{begin_of_headers,end_of_headers};
    
       cout << "    string headers_string{begin_of_headers,end_of_headers};"<<endl;
    
    PRINT_DEBUG("after !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");       
    istringstream resp_stream {headers_string};
    vector <string>lines{};
    string line{};

    while (resp_stream)
    {
	getline (resp_stream,line,'\n');
	lines.push_back (line) ;
    }

    //fill headers
    for (auto it=lines.begin () ; it != lines.end (); ++it)
    {

	auto  idx =  (*it).find (':');
	//no more headers
	if(idx == string::npos)
	{
	    break;
	}
	string field{(*it).begin(),   (*it).begin()+idx};
	string value {it->begin()+idx+2, it->end ()-1};
      
	header_fields_[field] =value;
    }
     
}

void ResponseMessage::set_message_size_3_(const vector<char>& response_message)
{
   total_message_size_3_= response_message.size();
}
int ResponseMessage:: get_message_size_3_()
{
   return total_message_size_3_;
}
void ResponseMessage::init_3(const vector<char>& response_message)
{
    set_status_line_3_(response_message);
    set_entity_3_(response_message);
    set_headars_3_(response_message);
    set_message_size_3_(response_message);
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
    if(!(entity_body_3_.empty()) )
	message<<entity_body_3_;
    return message.str();
}
