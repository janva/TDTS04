/**
 *
 * Filename:     debug.h
 * Date:	 2015-03-23	
 * Description:  desription goes here
 * 
 * Simple tools for debugging. Print traces and such... 
 * 
 */
#ifndef DEBUG_H
#define DEBUG_H

#include <iostream>

#define PRINT_DEBUG(EXPR)  std::cout <<"------------------------------"<<std::endl<<"["<< __FILE__<<"@Line:" <<__LINE__<<"]" << #EXPR <<" : "<<std::endl << EXPR << std::endl <<"-----------------------------------"<<std::endl;


#endif
