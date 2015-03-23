/**
 *  
 * Filename:     RequestMessage.h
 * Date:	 2015-03-23	
 * Description:  RequestMessage is container for a http request.
 * 
 *  
 */
#ifndef REQUESTMESSAGE_H
#define REQUESTMESSAGE_H

#include<string>
#include<map>

class RequestMessage
{
public:
   RequestMessage();
   RequestMessage(const std::string& request);

   std::string get_request_line () const;
   std::string get_header (const std::string& field_name) const;
   std::string get_entity_body () const;

   void set_request_line (const std::string& req_line);
   void set_header (const std::string& field, const std::string& value);
   std::string  to_str ();
   virtual ~RequestMessage();
   
private:
   void init_(std::string request);
   std::string request_line_;
   std::map<const std::string, std::string> header_fields_;
   std::string entity_body_;
   
};
#endif /* REQUESTMESSAGE_H */
