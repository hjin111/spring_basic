package com.beyond.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

// 해당 어노테이션을 통해 ComponentScanning 수행
@SpringBootApplication
// 주로 web 서블릿 기반의 구성요소를 스캔하고, 자동으로 등록하는 기능
// 예를 들어)@WebServlet, @WebFilter, @WebListener 등
@ServletComponentScan
public class BasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
	}

}
