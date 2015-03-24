/**
 *  
 * Filename:     ContentValidator.h
 * Date:	 2015-03-23
 *
 * Description:  Validator checks if given string contains illegal word.
 *               The list of illegal words are read from text-file. C
 *
 */
#ifndef CONTENTVALIDATOR_H
#define CONTENTVALIDATOR_H 
#include<set>
class Validator
{

public:
   //constructor fills set illegal_words_ from file.   
   explicit Validator (const std::string& filename);
   //overloaded call operator (making this a function object at instanciation)
   //checks if string contains illegal words mentioned illegal_words_
   bool operator()(const std::string& text);
   
private:
   std::set<std::string> illegal_words_;
};

#endif /* CONTENTVALIDATOR_H */
