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
#c# %.o: %.cpp
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
OBJS= proxyserver.o server.o client.o  RequestMessage.o ResponseMessage.o
BINS=  proxyserver test
#pattern rules instead
%.o: %.cpp
	$(CCC) $(CCFLAGS)  -c $<
%: %.o
	$(CCC) $(CCFLAGS)  -o $@ $^

proxyserver: proxyserver.o server.o client.o  RequestMessage.o ResponseMessage.o
proxyserver.o: proxyserver.cpp

#server: server.o RequestMessage.o ResponseMessage.o 
server.o: server.cpp server.h client.h

#client: client.o ResponseMessage.o RequestMessage.o
client.o: client.cpp client.h 

test: test.o ResponseMessage.o  ResponseMessage.h 
test.o: test.cpp

RequestMessage.o: RequestMessage.cpp RequestMessage.h

ResponseMessage.o: ResponseMessage.cpp ResponseMessage.h

ContentValidator.o: ContentValidator.cpp ContentValidator.h

#funkar inte kolla up
.PHONY: clean
clean:
	@ rm -f *.o $(BINS)
.PHONY:zap
zap: clean
	@  \rm *~
