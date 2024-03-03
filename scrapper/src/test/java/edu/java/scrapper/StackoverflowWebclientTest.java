package edu.java.scrapper;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.clients.StackOverflowClient;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest("app.sof-base-url=http://localhost:8080")
@WireMockTest(httpPort = 8080)
public class StackoverflowWebclientTest {

    private final StackOverflowClient stackoverflowClient;

    @Autowired
    public StackoverflowWebclientTest(StackOverflowClient stackoverflowClient) {
        this.stackoverflowClient = stackoverflowClient;
    }

    @Test
    public void testStackoverflowClientWhenValidUri() {
        String uri = "/1234321";
        OffsetDateTime expectedResult = OffsetDateTime.ofInstant(Instant.ofEpochSecond(1835371229), ZoneOffset.UTC);

        stubFor(get(uri + "?site=stackoverflow")
            .willReturn(okJson("{ \"items\" : [ {\"last_activity_date\" : 1835371229 } ] }")));

        assertThat(stackoverflowClient.getUpdateInfo(uri).getItems().get(0).getDateTime()).isEqualTo(expectedResult);
    }

    @Test
    public void testStackoverflowClientWhenInvalidUri() {
        String uri = "smth";

        assertThat(stackoverflowClient.getUpdateInfo(uri).getItems()).isNull();
    }
}
