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
   std::string get_status_line () const;

   std::string get_header ( const std::string& field_name) const;
   std::string get_entity_body () const;

   //NOTE we need this one 
   void set_header (const std::string& field, const std::string& value);
   
   std::string  to_str();
   virtual ~ResponseMessage();
   
   void set_entity(const std::vector<char>& message);
   void set_status_line(const std::string message );
   void set_status_line(const std::vector<char>& message);
   void set_headars(const std::vector<char>& response_message);
   void set_message_size(const int response_message);
   void set_message_size(const std::vector<char>& response_message);
   int  get_message_size();

private:
   void init_(const std::vector<char>& response_message);
   std::string status_line_;
   std::map<const std::string,  std::string> header_fields_;
   int total_message_size_;
   std::string entity_body_;
};
#endif /* RESPONSEMESSAGE_H */
