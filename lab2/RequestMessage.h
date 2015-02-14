#ifndef REQUESTMESSAGE_H
#define REQUESTMESSAGE_H

#include<string>
#include<map>

class RequestMessage
{
public:
   RequestMessage();
   RequestMessage(std::string request);

   std::string get_request_line () const;
   std::string get_headers () const;
   std::string get_headers (std::string field_name) const;
   std::string get_entity_body () const;
      
   virtual ~RequestMessage();
   // TODO: fixme move to private parts
   void init_(std::string request);
private:
   std::string request_line_;
   std::map<std::string, std::string> header_fields_;
   std::string entity_body_;
   
};
#endif /* REQUESTMESSAGE_H */
