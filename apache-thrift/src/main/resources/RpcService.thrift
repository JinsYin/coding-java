namespace java com.github.coding.thrift

// 产品定义
struct Product {
    1: string name;
    2: string desc;
    3: double price;
}

// 查询响应结果
struct Response {
    1: list<Product> products;
}

// RPC 服务
service RpcService {
    Response search(1: string keyword);
}