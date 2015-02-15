#
######################################################################
# Some gnu make hints and reminders
# 
# make --print-data-base to check default rules (predefined) -p the same
# 
# look for .c files in src subdirectory 
# VPATH %.c src
#
# % almost the same as * 
# 
# $^ Prerequisit seperated by space
# $< filename of first prereqisit 
# $@ filename representig the target
# $? names of all prerequisit that are never than target space seperated
# $% filename element of archive member function
# $+ Similar to $^ except includes duplicates
# $* the stem of target filename(filename whitout suffix)
#
#
#  example of simple rule
#
#  hello: main.cpp hello.cpp factorial.cpp
#	$(CC) $(CFLAGS) $? $(LDFLAGS) -o $@
#
# use pattern rules instead of suffix rules
# %.o: %.cpp
#	$(CCC) $(CCFLAGS) -c $<
# %: %.o
#	$(CCC) $(CCFLAGS) -o $@ $^
#
#
######################################################################
#
CCC= g++
CCFLAGS += -std=c++11 -Wall -Wextra -pedantic
DEBUG = -g
LIBS =
ASSEMBLER= -S
OBJ = 

#pattern rules instead
%.o: %.cpp
	$(CCC) $(CCFLAGS) -g -c $<
%: %.o
	$(CCC) $(CCFLAGS) -g -o $@ $^


server: server.o RequestMessage.o 
server.o: server.cpp

RequestMessage: RequestMessage.o RequestMessage.h
RequestMessage.o: RequestMessage.cpp


server2: server2.o
server2.o: server2.cpp

getaddr_info: getaddr_info.o
getaddr_info.o: getaddr_info.cpp

RequestMessage.o: RequestMessage.cpp RequestMessage.h

