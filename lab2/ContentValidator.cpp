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

//no automatic type-conversions please
Validator::Validator (const string& filename)
{
   ifstream in {filename};
   if(!in)
   {
      // TODO: throw exception instead
      //exit(1);
      throw validator_error("Unable to open file: "+filename +"\n");
   }
   //puts restricion on file only one word per line
   illegal_words_=
      new set<string>{istream_iterator<string>(in), istream_iterator<string>{}};

   // hmmm is this more effective ???
   //string word;  
   //while (getline(in, word, '\n'))
   //{
   //	  illegal_words_.emplace(std::move(word));
   //}
   in.close();
}
    
// TODO: investigate if creating a set from text
// and using for instance set intersection instead is
// cheaper way of doing things 
bool Validator::operator()(const string& text)
{
   string word;
   istringstream words{text};
       
   while (words)
   {
PRINT_DEBUG("trying to validate");
      words>> word ;
      auto  result = illegal_words_->find(word);
      if(result != illegal_words_->end())
      {
	 return false;
      }
   }
   return true;
}
    
Validator::~Validator()
{
   delete illegal_words_;
}


/**
 *
 *for testing purposes
 *
 */
//int main(int argc, char *argv[])
//{
//
//   // TODO: put this in main so we can configure proxy
//   if (argc != 2 )
//   {
//      cout << "usage: "<<argv[0]<<" filname\n";
//      return 1;
//   }
//
//
//   Validator valid{argv[1]};
//   
//   vector<string> words{};
//   //read_list <string>(argv[1],words);
//   read_list("forbidden.txt" , words);
//
//   for (auto word: words)
//   {
//      cout << word  << endl;
//   }
//   string text{"this text contains invalid words coffee for instance"};
//   string text2{"this text contains invalid words for instance"};
//   cout << (valid (text) ?"is valid": "is not valid")<< endl;
//   cout << (valid (text2) ?"is valid": "is not valid")<< endl;
//   
//   return 0;
//}
