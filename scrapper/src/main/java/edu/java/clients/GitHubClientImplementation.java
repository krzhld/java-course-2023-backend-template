package edu.java.clients;

import edu.java.dtos.GitHubResponse;
import java.time.Duration;
import edu.java.dtos.StackOverflowResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;
import static org.springframework.retry.RetryContext.MAX_ATTEMPTS;

public class GitHubClientImplementation implements GitHubClient {

    private final WebClient githubClient;

    public GitHubClientImplementation(WebClient githubClient) {
        this.githubClient = githubClient;
    }

    @Override
    public GitHubResponse getUpdateInfo(String uri) {
        return githubClient
            .get()
            .uri(uri)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(GitHubResponse.class)
            .onErrorReturn(new GitHubResponse())
            .retryWhen(Retry.fixedDelay(Long.parseLong(MAX_ATTEMPTS), Duration.ofSeconds(1)))
            .block();
    }
}
