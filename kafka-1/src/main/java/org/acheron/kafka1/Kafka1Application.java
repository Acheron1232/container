package org.acheron.kafka1;

import org.acheron.kafka1.service.OllamaRequestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Kafka1Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Kafka1Application.class, args);
        OllamaRequestService bean = run.getBean(OllamaRequestService.class);
        System.out.println("asd");
        bean.generate("hello")
                .map(e -> {
                    if (e.getChoices() == null) {
                        throw new RuntimeException("Choices is null");
                    }
                    return e.getChoices().stream().findFirst();
                })
                .subscribe(
                        result -> System.out.println("Result: " + result),
                        error -> error.printStackTrace()  // Ось тут обробляєш помилки!
                );
        System.out.println("asdasd");
    }

}
