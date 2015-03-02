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
   :  status_line_ (""),header_fields_(),entity_body_ ("Some content will go here")
{}

ResponseMessage::ResponseMessage(const std::vector<char>& response)
{
     init_3 (response);
}
ResponseMessage::ResponseMessage(const string& response)
   :  status_line_ (""),header_fields_(),entity_body_ ("Some content will go here")
{
   init_ (response);
 
}
string ResponseMessage::get_status_line () const{
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

//void ResponseMessage::set_entity_2_(char* message, int start_of_entity,
// 				    int size_of_entity)
// void ResponseMessage::set_entity_2_(const char* message)
// {
//    const char* start_of_entity;
//    int size_of_entity= get_content_length(message);
//    PRINT_DEBUG(size_of_entity);
//    if( ( start_of_entity = strstr(message,"\r\n\r\n")) != NULL)
//    {
//       //compensate for \r\n\r\n
//       start_of_entity = start_of_entity +4;  
//       PRINT_DEBUG("END Delimiter found ");
//       PRINT_DEBUG (start_of_entity);
//       //PRINT_DEBUG( (end_of_headers - buf) );   
//       
//       // memcpy(message +start_of_entity, entity_body_2_, size_of_entity);
//       //kääk remember to delete
//       // char* hello = (char*)malloc (size_of_entity);
//       //memset (entity_body_2_,0,size_of_entity);
//       entity_body_2_ = new char[size_of_entity];
//       //strcpy(&(this->entity_body_2_[0]),start_of_entity);
//       memcpy(entity_body_2_,start_of_entity,size_of_entity);
//       
//      PRINT_DEBUG (entity_body_2_);
//    }
// }
// TODO: rename  something like build_  these maybe introduce builder patter
int ResponseMessage::get_message_size (vector<char>& message_chunk)
{
   //behöver content-length + fram till headers +4
   vector<char> delimiter {'C','o','n','t','e','n','t','-','L','e','n','g','t','h'};
   auto it =
      search (message_chunk.begin(), message_chunk.end(), delimiter.begin(), delimiter.end());
   auto it2 =find(it, message_chunk.end(), ':');
   ++it2;
   auto it3= find(it, message_chunk.end(), '\r');
   it3;
   string s_size{it2, it3};

   vector<char> delimiter2 {'\r','\n','\r','\n'};
   auto begin_delimiter =
      search (message_chunk.begin(), message_chunk.end(), delimiter2.begin(), delimiter2.end());

   int entity_size = stoi (s_size);
   PRINT_DEBUG (entity_size);
   PRINT_DEBUG (message_chunk.size());
   PRINT_DEBUG (begin_delimiter - message_chunk.begin());
   int total_size =  entity_size + (begin_delimiter - message_chunk.begin())+4 ;
   return total_size;

   
   //expected_total_size = end_of_headers - buf + entity_body_size +4;
}

void ResponseMessage::set_status_line_3_(const vector<char>& message)
{
   auto end_of_status_line= find(message.begin(),message.end(), '\r');
   status_line_={message.begin(), end_of_status_line-1};
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
      copy (begin_delimiter, message.end(), back_inserter(entity_body_3_));
      copy (entity_body_3_.begin(),entity_body_3_.end(), ostream_iterator<char>(cout, ""));
   }
}

// TODO: continue here all messed upp right now
void ResponseMessage::set_headars_3_(const vector<char>& response_message)
{
   string field;
   string value;

   // can i do this with only algorithm and maybe lambda?
   //move begin iteraror to past status line 
   //move end iteraror before entity body 
   //find first : becomes key
   // move iterator past :
   // find \r start over

   auto begin_of_headers = find (response_message.begin(),response_message.end(),'\n');
   ++begin_of_headers;
   
   string searchString{"\r\n\r\n"};
   auto end_of_headers = search(begin (response_message), end(response_message),
				begin(searchString), end(searchString));

   
   string headers_string{begin_of_headers,end_of_headers};

   istringstream resp_stream {headers_string};
   vector <string>lines{};
   string line{};
   
   while (resp_stream)
   {
      getline (resp_stream,line,'\n');
      lines.push_back (line) ;
   }
    //fill headers
   for (auto it=lines.begin () +1; it != lines.end (); ++it)
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

void ResponseMessage::init_3(const vector<char>& response_message)
{
  set_status_line_3_(response_message);
  set_entity_3_(response_message);
  set_headars_3_(response_message);
}

  
char* ResponseMessage::get_raw()
{
   return raw;
}

void ResponseMessage::set_raw(const char* message, int size)
{
   raw= new char[size];
   memcpy(raw ,message,size);
   totalSize=size ;
}

int ResponseMessage::get_raw_size()
{
   return totalSize;
}


void ResponseMessage::init_(string response_message)
{
   //char* begining_of_entity= strstr("/r/n/rn");
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
   entity_body_.clear();
//will be char from in future
try{
    string searchString{"\r\n\r\n"};
   auto begining_of_entity = search(begin (response_message),
				    end(response_message),
				    begin(searchString),
				    end(searchString));

   
   // PRINT_DEBUG (response_message);
   begining_of_entity++;
   begining_of_entity++;
   begining_of_entity++;
   begining_of_entity++;
   
   entity_body_ =string {begining_of_entity,end(response_message)};
}
   catch (std::length_error e)
   {
      cout << "iterator fell over edge"<< e.what();
      
   }

   //fill headers
   for (auto it=lines.begin () +1; it != lines.end (); ++it)
   {
       auto  idx =  (*it).find (':');
       //no more headers
      if(idx == string::npos)
      {
	 // entity_body_.clear();
	 // for(auto it2=it+1; it2!=lines.end(); ++it2)
	 //    entity_body_+= *it2 +"\n";
	  break;
      }
      string field{(*it).begin(),   (*it).begin()+idx};
      string value {it->begin()+idx+2, it->end ()-1};
      	 
      header_fields_[field] =value;
      
   }
}

// void ResponseMessage::init_(string response_message)
// {
//    //char* begining_of_entity= strstr("/r/n/rn");
//    istringstream resp_stream {response_message};
//    vector <string>lines{};
//    string line{};
//    
//    while (resp_stream)
//    {
//       getline (resp_stream,line,'\n');
//       lines.push_back (line) ;
//       //if(line == "\r\n")
//    }
//    
//    status_line_ = lines.at (0);
//    entity_body_.clear();
// //will be char from in future
// try{
//     string searchString{"\r\n\r\n"};
//    auto begining_of_entity = search(begin (response_message),
// 				    end(response_message),
// 				    begin(searchString),
// 				    end(searchString));
// 
//    
//    // PRINT_DEBUG (response_message);
//    begining_of_entity++;
//    begining_of_entity++;
//    begining_of_entity++;
//    begining_of_entity++;
//    
//    entity_body_ =string {begining_of_entity,end(response_message)};
// }
//    catch (std::length_error e)
//    {
//       cout << "iterator fell over edge"<< e.what();
//       
//    }
// 
//    //fill headers
//    for (auto it=lines.begin () +1; it != lines.end (); ++it)
//    {
//        auto  idx =  (*it).find (':');
//        //no more headers
//       if(idx == string::npos)
//       {
// 	 // entity_body_.clear();
// 	 // for(auto it2=it+1; it2!=lines.end(); ++it2)
// 	 //    entity_body_+= *it2 +"\n";
// 	  break;
//       }
//       string field{(*it).begin(),   (*it).begin()+idx};
//       string value {it->begin()+idx+2, it->end ()-1};
//       	 
//       header_fields_[field] =value;
//       
//    }
//    
// //   #ifdef DEBUG_ME
// //   std::cout << "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" << std::endl;
// //   std::cout << "lines.size(): " << lines.size() << std::endl;
// //   int index = 0;
// //
// //   for (auto i : lines){
// //       int value;
// //       std::cout << "lines@" << index <<"(" << i.size() << ")" << ": " << i << std::endl;
// //       for (auto b : i){
// //	   value = b;
// //	   cout << value << " | ";
// //       }
// //       std::cout << std::endl << std::endl;
// //	   
// //       ++index;
// //   }
// //   #endif
//    //entity_body_=lines[lines.size ()-1];
//    //  cout <<entity_body_ <<endl;
// //   cout <<header_fields_.size ()<<endl;
// //   cout <<header_fields_["Date"]<<endl;
// //
//    
//    //PRINT_DEBUG(entity_body_2_);
// }

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

int ResponseMessage::get_content_length (const char* message)
{
   const char* start= strstr(message,"Content-Length");
   start= strstr(start,":");
   start++;
   const char* end = strstr(start, "\r");
   int size =end- start;
   char res[size];
   memcpy(  res ,start, size );
   return atoi(res);
}
