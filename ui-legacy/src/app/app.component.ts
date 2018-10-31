import { Component } from '@angular/core';

declare var EventSource:any; //needed

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  eventData;

  getEvents() {
    const eventSource = new EventSource('http://localhost:8080/progress');

    eventSource.addEventListener('progressEvent', event => {
      this.eventData = event.data;
    });
  }
}
