package cn.arorms.iot.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.arorms.iot.server.mapper")  // 指定 mapper 所在的包路径
public class DataServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataServerApplication.class, args);
    }
}
