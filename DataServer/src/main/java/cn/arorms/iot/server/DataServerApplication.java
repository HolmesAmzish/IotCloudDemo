package cn.arorms.iot.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class DataServerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DataServerApplication.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
