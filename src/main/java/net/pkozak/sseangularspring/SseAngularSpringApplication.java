package net.pkozak.sseangularspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SseAngularSpringApplication {

  public static void main(String[] args) {
    SpringApplication.run(SseAngularSpringApplication.class, args);
  }

}
