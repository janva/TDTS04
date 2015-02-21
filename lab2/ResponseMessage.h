/**
 *  
 * Filename:     ResponseMessage.h
 * Date:	 2015-02-15	
 * Description:  desription goes here
 * 
 */
#ifndef RESPONSEMESSAGE_H
#define RESPONSEMESSAGE_H

#include<string>
#include<map>

// TODO: construction and copy-controll error handling
// TODO: factor out common code with request
class ResponseMessage
{
public:
   ResponseMessage();
   ResponseMessage(const std::string& response_message);

   std::string get_status_line () const;
   std::string get_header (const std::string& field_name) const;
   std::string get_entity_body () const;

   void set_status_line (const std::string& status_line);
   void set_header (const std::string& field, const std::string& value);
   //const char* to_cstr();
   std::string  to_str();
   virtual ~ResponseMessage();

private:
   void init_(std::string response);
   std::string status_line_;
   // TODO: make this pointer typ maybe unique pointer to make pointer handling simple
   std::map<const std::string, std::string> header_fields_;
   std::string entity_body_;
};
#endif /* RESPONSEMESSAGE_H */
