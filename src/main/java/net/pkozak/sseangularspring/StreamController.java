package net.pkozak.sseangularspring;

import java.time.Duration;
import java.time.LocalTime;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import reactor.core.publisher.Flux;

/**
 * @project: sse-angular-spring
 * @author: Patryk Kozak <pkozak@corelogic.com>
 * @date: 10/31/2018 4:49 PM
 */
@RestController
public class StreamController {

  @GetMapping("/progress")
  @CrossOrigin(origins = "http://localhost:4200")
  public Flux<ServerSentEvent<String>> streamEvents() {
    return Flux.interval(Duration.ofSeconds(1))
        .map(sequence -> ServerSentEvent.<String> builder()
            .id(String.valueOf(sequence))
            .event("progressEvent")
            .data("Loading part = " + sequence.intValue())
            .build());
  }

}
