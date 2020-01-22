package cn.tedu.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.tedu.store.mapper") //根據給定的包名，啟動掃描Mapper持久層
public class TeduStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeduStoreApplication.class, args);
	}

}

