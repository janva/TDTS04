#ifndef DEBUG_H
#define DEBUG_H

#include <iostream>

#define PRINT_DEBUG(EXPR)  std::cout <<"------------------------------"<<std::endl<<"["<< __FILE__<<"@Line:" <<__LINE__<<"]" << #EXPR <<" : "<<std::endl << EXPR << std::endl <<"-----------------------------------"<<std::endl;


#endif
