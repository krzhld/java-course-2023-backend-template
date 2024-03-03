package edu.java.scrapper;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.clients.GitHubClient;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest("app.git-base-url=http://localhost:8080")
@WireMockTest(httpPort = 8080)
public class GithubWebclientTest {

    private final GitHubClient githubClient;

    @Autowired
    public GithubWebclientTest(GitHubClient gitHubClient) {
        this.githubClient = gitHubClient;
    }

    @Test
    public void testGitClientWhenValidURI() {
        String uri = "/user/repo";
        OffsetDateTime expectedResult = OffsetDateTime.parse("2023-03-16T16:24:43Z");

        stubFor(get(uri)
            .willReturn(okJson("{ \"updated_at\": \"2023-03-16T16:24:43Z\" }")));

        assertThat(githubClient.getUpdateInfo(uri).getDateTime()).isEqualTo(expectedResult);
    }

    @Test
    public void testGitClientWhenInvalidURI() {
        String uri = "smth";

        assertThat(githubClient.getUpdateInfo(uri).getDateTime()).isNull();
    }
}
