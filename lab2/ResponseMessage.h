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
#include<vector>

// TODO: construction and copy-controll error handling
// TODO: factor out common code with request
class ResponseMessage
{
public:
   ResponseMessage();
   ResponseMessage(const std::string& response_message);
   ResponseMessage(const std::vector<char>& response);
   std::string get_status_line () const;
   //std::string get_header (const std::string& field_name) const;
   //const std::map<std::string,std::string>::iterator
   std::string get_header ( const std::string& field_name) const;
   std::string get_entity_body () const;

   void set_status_line (const std::string& status_line);
   void set_header (const std::string& field, const std::string& value);
   //const char* to_cstr();
   std::string  to_str();
   virtual ~ResponseMessage();
   void set_entity_2_(const char* msg);
   void set_raw(const char* message, int size);
   char* get_raw();
   int get_raw_size();
   int get_content_length (const char* message);
private:
   void init_(std::string response);
   std::string status_line_;
   // TODO: make this pointer typ maybe unique pointer to make pointer handling simple
   std::map<const std::string,  std::string> header_fields_;
   std::string entity_body_;
   char* entity_body_2_;
   // char* entity_body_2_;
   //unique_pointer<char> entity_body_2_;
   int totalSize;
   std::vector <char> entity_body_3_;
   char* raw;
};
#endif /* RESPONSEMESSAGE_H */
