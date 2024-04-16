package cn.guruguru.coding.thrift.server;

import cn.guruguru.coding.thrift.RpcService;
import cn.guruguru.coding.thrift.impl.RpcServiceImpl;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

public class ThriftServer {

    public static void main(String[] args) throws TTransportException {
        TServerTransport serverSocket = new TServerSocket(10000);

        RpcService.Processor<RpcServiceImpl> processor = new RpcService.Processor<>(new RpcServiceImpl());
        final TServer.Args serverArgs = new TServer.Args(serverSocket).processor(processor);

        System.out.println("Starting a thrift server listened on 10000 port ...");
        TServer server = new TSimpleServer(serverArgs);
        server.serve();
    }
}
