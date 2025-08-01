package org.acheron.kafka1.api;

import lombok.RequiredArgsConstructor;
import org.acheron.kafka1.service.OllamaRequestService;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
public class ProducerController {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OllamaRequestService ollamaRequestService;

    @GetMapping("/")
    public String index() {
        kafkaTemplate.send("yo", "hello");
        return "Hello World!";
    }

//    @GetMapping("/h")
//    public Flux<ServerSentEvent<String>> h() {
//        return Flux.interval(Duration.ofSeconds(10)).flatMap(l -> ollamaRequestService.generate(" generate html template for login page with email and password and add some cool css and mb js").map(this::extractHtml).map(html ->
//                ServerSentEvent.<String>builder()
////                        .event("html-template")
//                        .data(html)
//                        .build()
//        ));
//    }

    private String extractHtml(String response) {
        System.out.println(response);
        Pattern pattern = Pattern.compile("```html\\s*([\\s\\S]+?)\\s*```");
        Matcher matcher = pattern.matcher(response);
        if (matcher.find()) {
            return matcher.group(1).trim(); // Return only the HTML block
        } else {
            return "No HTML block found.";
        }
    }

}