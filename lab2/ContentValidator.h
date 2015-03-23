/**
 *  
 * Filename:     ContentValidator.h
 * Date:	 2015-03-23
 *
 * Description:  Validator checks if given string contains illegal word.
 *               The list of illegal words are read from text-file.
 *
 */
#ifndef CONTENTVALIDATOR_H
#define CONTENTVALIDATOR_H 
#include<set>
class Validator
{

public:
   //No typeconversion please
   explicit Validator (const std::string& filename);
   bool operator()(const std::string& text);
   
   virtual ~Validator();
   
private:
   std::set<std::string> illegal_words_;
};

#endif /* CONTENTVALIDATOR_H */
