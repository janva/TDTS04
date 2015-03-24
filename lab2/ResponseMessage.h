/**
 *  
 * Filename:     ResponseMessage.h
 * Date:	 2015-02-15	
 * Description:  Response message is container for a http response.
 * 
 */
#ifndef RESPONSEMESSAGE_H
#define RESPONSEMESSAGE_H
#include<string>
#include<map>
#include<vector>

class ResponseMessage
{
public:
   ResponseMessage();

   ResponseMessage(const std::vector<char>& response);
   //gets string representation which can be sent to client
   std::string  to_str();

   //return status line part of messages
   std::string get_status_line () const;
   //given header returns corresponding value
   std::string get_header ( const std::string& field_name) const;
   //return entity body of message
   std::string get_entity_body () const;
   //return message total size 
   int  get_message_size();

   //set headers we use vector<char>& instead of char* in some places
   void set_headars(const std::vector<char>& response_message);
   //set single header
   void set_header (const std::string& field, const std::string& value);
   //set entity body
   void set_entity(const std::vector<char>& message);
   //status line from string and vector<char>
   void set_status_line(const std::string message );
   void set_status_line(const std::vector<char>& message);
   //calculate and set message size
   void set_message_size(const int response_message);
   void set_message_size(const std::vector<char>& response_message);

private:
   //translate to our abstractrion from vector<char>
   void init_(const std::vector<char>& response_message);

   std::string status_line_;
   std::map<const std::string,  std::string> header_fields_;
   int total_message_size_;
   std::string entity_body_;
};
#endif /* RESPONSEMESSAGE_H */
