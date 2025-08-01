package org.acheron.reactive.api;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalTime;

@RestController
public class SSETest {

    @GetMapping("/async")
    public Flux<ServerSentEvent<String>> async(){
        return Flux.interval(Duration.ofSeconds(2)).map(sequence -> ServerSentEvent.<String> builder()
                .id(String.valueOf(sequence))
                .event("periodic-event")
                .data("SSE - " + LocalTime.now())
                .build());
    }
}
