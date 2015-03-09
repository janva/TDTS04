#ifndef CONTENTVALIDATOR_H
#define CONTENTVALIDATOR_H
#include<set>
class Validator
{

public:
   explicit Validator (const std::string& filename);
   bool operator()(const std::string& text);
   
   virtual ~Validator();
   
private:
   std::set<std::string>* illegal_words_;
};

#endif /* CONTENTVALIDATOR_H */
