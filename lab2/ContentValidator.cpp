#include <fstream>
#include <string>
#include <vector>
#include <set>
#include <iostream>
#include <iterator>
#include <algorithm>
#include <sstream>

// using std::vector;
// using std::string;
// using std::ifstream;
// using std::cerr;
// using std::cout;
// using std::copy;
// using std::istream_iterator;
//read forbidden_words (container type to read into)
// fill vector from file trying to learing templates at same time
using namespace std;

//might want to reuse this with char* for instance hence the template
//@deprecated using function objecet class Validator instead
template<typename T>
void read_list (const string& filename, vector<T>& word_list)
{

   ifstream input(filename);
   if (!input)
   {
      cerr <<"could not open input file : "<< filename<<"\n";
      //hmm could we use exceptions instead 
      //return 2;
   }
   copy (istream_iterator<T>(input) , istream_iterator<T>{} ,back_inserter(word_list));
   input.close();
}

class Validator
 {
 public:
    //no automatic type-conversions please
    explicit Validator (const string& filename)
    {
       ifstream in {filename};
       if(!in)
       {
       // TODO: throw exception instead
 	 exit(1);
       }

       //puts restricion on file only one word per line
       illegal_words_= new set<string> {istream_iterator<string>(in), istream_iterator<string>{}};

       // hmmm is this more effective 
       //string word;  
       //while (getline(in, word, '\n'))
       //{
       //	  illegal_words_.emplace(std::move(word));
       //}
       
       in.close();
       
       for (auto word : *illegal_words_ )
       {
	  cout<< word<< endl;
       }
    }
    
    // TODO: investigate if creating a set from text
    // and using for instance set intersection instead is
    // cheaper way of doing things 
    bool operator()(const string& text)
    {
       string word;
       
       istringstream words{text};
       
       while (words)
       {
	  words>> word ;
	  auto  result = illegal_words_->find(word);
	  if(result != illegal_words_->end())
	  {
	     return false;
	  }
       }
       return true;
    }
    
    ~Validator()
    {
       delete illegal_words_;
    }
 private:
    set<std::string>* illegal_words_;
 };


/**
 *
 *for testing purposes
 *
 */
int main(int argc, char *argv[])
{

   // TODO: put this in main so we can configure proxy
   if (argc != 2 )
   {
      cout << "usage: "<<argv[0]<<" filname\n";
      return 1;
   }


   Validator valid{argv[1]};
   
   vector<string> words{};
   //read_list <string>(argv[1],words);
   read_list("forbidden.txt" , words);

   for (auto word: words)
   {
      cout << word  << endl;
   }
   string text{"this text contains invalid words coffee for instance"};
   string text2{"this text contains invalid words for instance"};
   cout << (valid (text) ?"is valid": "is not valid")<< endl;
   cout << (valid (text2) ?"is valid": "is not valid")<< endl;
   
   return 0;
}
