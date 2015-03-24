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
   //construct request from string
   RequestMessage(const std::string& request);
   //returns request line of request message
   std::string get_request_line () const;
   //returns single header value given its fieladname
   std::string get_header (const std::string& field_name) const;
   //returns entitity body part of message
   std::string get_entity_body () const;

   //set request manually 
   void set_request_line (const std::string& req_line);
   //set header given field and value
   void set_header (const std::string& field, const std::string& value);
   //returns whole message as string includning all \r\n:s
   std::string  to_str ();
   
private:
   //breaks down request as string into our own abstraction.
   void init_(std::string request);
   std::string request_line_;
   std::map<const std::string, std::string> header_fields_;
   std::string entity_body_;
   
};
#endif /* REQUESTMESSAGE_H */
