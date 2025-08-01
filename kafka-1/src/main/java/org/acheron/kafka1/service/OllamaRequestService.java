package org.acheron.kafka1.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class OllamaRequestService {

    private final WebClient webClient;

    public OllamaRequestService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:11434").build();
    }

    public Mono<OpenAIResponse> generate(String prompt) {
        return webClient.post()
            .uri("/api/generate")

            .bodyValue(new OllamaRequest("deepseek-r1:1.5b", prompt,false))
            .retrieve()
            .bodyToMono(OpenAIResponse.class);
    }

    record OllamaRequest(String model, String prompt,Boolean stream) {}
}
