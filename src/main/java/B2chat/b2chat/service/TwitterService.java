package B2chat.b2chat.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TwitterService {

    private final WebClient webClient;

    @Value("${twitter.api.bearer-token}")
    private String bearerToken;

    public TwitterService(WebClient.Builder webClientBuilder, @Value("${twitter.api.base-url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<String> getUserTweets(String username) {
        return this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tweets")
                        .queryParam("username", username)
                        .build())
                .headers(headers -> headers.setBearerAuth(bearerToken))
                .retrieve()
                .bodyToMono(String.class);
    }
}
