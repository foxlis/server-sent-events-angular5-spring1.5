package net.pkozak.sseangularspring;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @project: sse-angular-spring
 * @author: Patryk Kozak <pkozak@corelogic.com>
 * @date: 10/31/2018 4:49 PM
 */
@RestController
public class StreamController {

  private AtomicInteger count;

  private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

  @Autowired public StreamController() {
    this.count = new AtomicInteger(0);
  }

  @GetMapping("/progress")
  @CrossOrigin(origins = "http://localhost:4200")
  public SseEmitter streamEvents() {
    SseEmitter emitter = new SseEmitter();
    this.emitters.add(emitter);

    emitter.onCompletion(() -> this.emitters.remove(emitter));
    emitter.onTimeout(() -> this.emitters.remove(emitter));

    return emitter;
  }

  @EventListener(CustomEvent.class)
  public void onEvent(CustomEvent customEvent) {
    List<SseEmitter> deadEmitters = new ArrayList<>();
    this.emitters.forEach(emitter -> {
      try {
        SseEmitter.SseEventBuilder sseEventBuilder =
            SseEmitter.event()
                .name("progressEvent")
                .data(customEvent.getData())
                .id(count.toString());

        emitter.send(sseEventBuilder);
      } catch (Exception e) {
        deadEmitters.add(emitter);
      }
    });

    this.emitters.removeAll(deadEmitters);
  }

}
