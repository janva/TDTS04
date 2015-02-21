#ifndef REQUESTMESSAGE_H
#define REQUESTMESSAGE_H

#include<string>
#include<map>

// TODO: construction and copy-controll error handling
// TODO: duplicate code with response message may interface +adaptor? template
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
   //void  to_cstr(char*);
   std::string  to_str ();
   virtual ~RequestMessage();
   // TODO: fixme move to private parts
private:
   void init_(std::string request);
   std::string request_line_;
   // TODO: make this pointer typ maybe unique pointer to make pointer handling simple
   std::map<const std::string, std::string> header_fields_;
   std::string entity_body_;
   
};
#endif /* REQUESTMESSAGE_H */
