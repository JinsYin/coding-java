package com.github.coding.thrift.client;

import com.github.coding.thrift.Response;
import com.github.coding.thrift.RpcService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftClient {

    public static void main(String[] args) throws TException {
        TTransport transport = new TSocket("localhost", 10000);
        transport.open();

        TProtocol protocol = new TBinaryProtocol(transport);
        RpcService.Client client = new RpcService.Client(protocol);

        {
            Response res = client.search("iphone 1");
            // Product(name:iphone 13, desc:iphone 13 128GB 5G, price:4999.0)
            // Product(name:iphone 14, desc:iphone 14 256GB 5G, price:5999.0)
            res.getProducts().forEach(System.out::println);
        }

        System.out.println("--------------------");

        {
            Response res = client.search("iphone X");
            // Product(name:iphone X, desc:iphone X 64GB 4G, price:3999.0)
            res.getProducts().forEach(System.out::println);
        }

        transport.close();
    }
}
