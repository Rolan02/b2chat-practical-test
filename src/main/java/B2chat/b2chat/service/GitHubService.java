package B2chat.b2chat.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GitHubService {

    private final WebClient webClient;

    public GitHubService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.github.com").build();
    }
    public Mono<String> getUserInfo(String username) {
        return this.webClient
                .get()
                .uri("/users/{username}", username)
                .retrieve()
                .bodyToMono(String.class);
    }
}
