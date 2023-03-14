package com.github.coding.thrift.impl;

import com.github.coding.thrift.Product;
import com.github.coding.thrift.Response;
import com.github.coding.thrift.RpcService;
import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * `RpcService.Iface` 是 RpcServer 的一个同步接口
 */
public class RpcServiceImpl implements RpcService.Iface {

    /**
     * 模拟产品搜索功能
     *
     * @param keyword
     * @return
     * @throws TException
     */
    @Override
    public Response search(String keyword) throws TException {
        List<Product> products = new ArrayList<>();
        // 模拟添加 3 个产品
        {
            final Product product = new Product();
            product.setName("iphone X");
            product.setDesc("iphone X 64GB 4G");
            product.setPrice(3999);
            products.add(product);
        }
        {
            final Product product = new Product();
            product.setName("iphone 13");
            product.setDesc("iphone 13 128GB 5G");
            product.setPrice(4999);
            products.add(product);
        }
        {
            final Product product = new Product();
            product.setName("iphone 14");
            product.setDesc("iphone 14 256GB 5G");
            product.setPrice(5999);
            products.add(product);
        }

        List<Product> result = products.stream()
                .filter(p -> p.getName().contains(keyword) || p.getDesc().contains(keyword))
                .collect(Collectors.toList());

        Response response = new Response();
        response.setProducts(result);
        return response;
    }
}
