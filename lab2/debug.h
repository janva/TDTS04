#ifndef DEBUG_H
#define DEBUG_H

#include <iostream>

#define P(EXPR) std::cout << "[@Line: "<<__LINE__<<"] "<< #EXPR <<" : " << EXPR << std::endl;


#endif
