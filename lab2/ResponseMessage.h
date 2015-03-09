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
   
   void set_entity_3_(const std::vector<char>& message);
   void set_status_line_3_(const std::vector<char>& message);
   void set_headars_3_(const std::vector<char>& response_message);
   void set_message_size_3_(const std::vector<char>& response_message);
   int  get_message_size_3_();
   void init_3(const std::vector<char>& response_message);
private:

   std::string status_line_;
   // TODO: make this pointer typ maybe unique pointer to make pointer handling simple
   std::map<const std::string,  std::string> header_fields_;
   int total_message_size_3_;
   std::string entity_body_3_;
};
#endif /* RESPONSEMESSAGE_H */
