package org.ac.cst8277.du.yuhan.tweetservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        ReactiveSecurityAutoConfiguration.class
})
public class YuhanTweetServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuhanTweetServiceApplication.class, args);
    }
}

