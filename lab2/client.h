/**
 *  
 * Filename:     client.h
 * Date:	 2015-03-23	
 * Description:  desription goes here
 * 
 */
#ifndef CLIENT_H
#define CLIENT_H
#include "ResponseMessage.h"
#include "RequestMessage.h"

class Client
{
public:
    Client() {};
    ResponseMessage forward (RequestMessage& reqMess);
    void init_client (const char* node);
    void bind_socket ();
    void send_message (const char* message );
    ResponseMessage receive_message ();
    ResponseMessage receive_message_2 ();
    void close_socket ();
    void setup (std::string host);
   bool is_valid(ResponseMessage respMsg);
   bool is_valid(RequestMessage reqyMsg);
    int recvtimeout(int s, std::vector<char>& buf , int len, int timeout);
   
    virtual ~Client(){};
private:
    int sockfd;
    struct addrinfo *servinfo;
    const char* PORT {"80"};
    static constexpr unsigned int MAX2015-03-23ASIZE {4096};
    // TODO: tempfix
    void* get_in_addr(struct sockaddr *sa);
};


#endif /* CLIENT_H */
