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

    int recvtimeout(int s, std::vector<char>& buf , int len, int timeout);
   
    virtual ~Client(){};
private:
    int sockfd;
    struct addrinfo *servinfo;
    const char* PORT {"80"};
    static constexpr unsigned int MAXDATASIZE {4096};
    // TODO: tempfix
    void* get_in_addr(struct sockaddr *sa);
};


#endif /* CLIENT_H */
