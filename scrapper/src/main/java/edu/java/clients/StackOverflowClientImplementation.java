package edu.java.clients;

import edu.java.dtos.StackOverflowResponse;
import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;
import static org.springframework.retry.RetryContext.MAX_ATTEMPTS;

public class StackOverflowClientImplementation implements StackOverflowClient {

    private final WebClient stackOverflowClient;

    public StackOverflowClientImplementation(WebClient stackOverflowClient) {
        this.stackOverflowClient = stackOverflowClient;
    }

    @Override
    public StackOverflowResponse getUpdateInfo(String uri) {
        return stackOverflowClient
            .get()
            .uri(uriBuilder ->
                uriBuilder
                    .path(uri)
                    .queryParam("site", "stackoverflow")
                    .build()
            )
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(StackOverflowResponse.class)
            .onErrorReturn(new StackOverflowResponse())
            .retryWhen(Retry.fixedDelay(Long.parseLong(MAX_ATTEMPTS), Duration.ofSeconds(1)))
            .block();
    }
}
