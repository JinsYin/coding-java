package cn.guruguru.coding.springcloud.eureka;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "spring-cloud-producer")
public interface RemoteService {
    @GetMapping("/hello")
    String getHello(@RequestParam(value = "name") String name);
}
