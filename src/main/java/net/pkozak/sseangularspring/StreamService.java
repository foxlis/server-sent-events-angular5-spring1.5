package net.pkozak.sseangularspring;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StreamService {

  private final ApplicationEventPublisher applicationEventPublisher;
  private AtomicLong loadingProgress;

  @Autowired public StreamService(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
    this.loadingProgress = new AtomicLong(0L);
  }

  @Scheduled(fixedRate = 1000)
  public void sendEvent() {
    this.applicationEventPublisher.publishEvent(new CustomEvent("Loading - " + loadingProgress.getAndIncrement()));
  }

}
