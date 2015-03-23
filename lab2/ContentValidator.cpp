#include <fstream>
#include <string>
#include <set>
#include <iterator>
#include <algorithm>
#include <iostream>
#include <sstream>
#include  <stdexcept>
#include  "debug.h"
	
#include "ContentValidator.h"

using std::string;
using std::set;
using std::ifstream;
using std::cout;
using std::copy;
using std::istream_iterator;
using std::istringstream;
using std::invalid_argument;

class validator_error: public invalid_argument 
{
public:
     explicit validator_error(const string& err_msg)
	: invalid_argument(err_msg) {}
   explicit validator_error(const char* err_msg)
      : invalid_argument(err_msg) {}
};

Validator::Validator (const string& filename)
{
   ifstream in {filename};
   if(!in)
   {
      throw validator_error("Unable to open file: "+filename +"\n");
   }

   std::string word;  
   while (getline(in, word, '\n'))
   {
   	  illegal_words_.emplace(std::move(word));
   }
   in.close();
}
    
// TODO: investigate if creating a set from text
// and using for instance set intersection instead is
// cheaper way of doing things 
bool Validator::operator()(const string& text)
{
   string word;
    for(auto it = illegal_words_.begin(); it != illegal_words_.end(); ++it){
	if(text.find(*it) != string::npos){
	   return false;
	}
    }
    
    return true;
}

//@deprecated
Validator::~Validator()
{
    //delete illegal_words_;
}
