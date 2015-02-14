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

#pattern rules instead
%.o: %.cpp
	$(CCC) $(CCFLAGS) -c $<
%: %.o
	$(CCC) $(CCFLAGS) -o $@ $^

test: test.o  RequestMessage.o
test.o: test.cpp

RequestMessage.o: RequestMessage.cpp RequestMessage.h

