package cn.guruguru.coding.springcloud.eureka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {
    @Autowired
    RemoteService remoteService;

    @GetMapping("/hello/{name}")
    public String getHello(@PathVariable("name") String name) {
        return remoteService.getHello(name);
    }

    @GetMapping("/hello")
    public String index(@RequestParam String name) {
        return remoteService.getHello(name);
    }

}
