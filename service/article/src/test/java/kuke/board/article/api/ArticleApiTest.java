package kuke.board.article.api;

import kuke.board.article.service.response.ArticlePageResponse;
import kuke.board.article.service.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class ArticleApiTest {
  RestClient restClient = RestClient.create("http://localhost:9000");

  @Test
  void createTest() {
    ArticleResponse response = create(new ArticleCreateRequest(
        "title1", "content1", 1L, 1L
    ));
    System.out.println("response = " + response);
  }

  ArticleResponse create(ArticleCreateRequest request) {
    return restClient.post()
        .uri("/v1/articles")
        .body(request)
        .retrieve()
        .body(ArticleResponse.class);
  }

  @Test
  void readTest() {
    ArticleResponse response = read(172243076923314176L);
    System.out.println("response = " + response);
  }

  ArticleResponse read(Long articleId) {
    return restClient.get()
        .uri("/v1/articles/{articleId}", articleId)
        .retrieve()
        .body(ArticleResponse.class);
  }

  @Test
  void updateTest() {
    ArticleResponse response = update(172243076923314176L, new ArticleUpdateRequest("updateTitle1", "updateContent1"));
    System.out.println("response = " + response);
  }

  ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
    return restClient.put()
        .uri("/v1/articles/{articleId}", articleId)
        .body(request)
        .retrieve()
        .body(ArticleResponse.class);
  }

  @Test
  void deleteTest() {
    restClient.delete()
        .uri("/v1/articles/{articleId}", 172243076923314176L)
        .retrieve();
  }

  @Test
  void readAllTest() {
    ArticlePageResponse response = restClient.get()
        .uri("/v1/articles?boardId=1&pageSize=30&page=50000")
        .retrieve()
        .body(ArticlePageResponse.class);

    System.out.println("response.getArticleCount() = " + response.getArticleCount());
    for (ArticleResponse article : response.getArticles()) {
      System.out.println("articleId = " + article.getArticleId());
    }
  }

  @Getter
  @AllArgsConstructor
  static class ArticleCreateRequest {
    private String title;
    private String content;
    private Long writerId;
    private Long boardId;
  }

  @Getter
  @AllArgsConstructor
  static class ArticleUpdateRequest {
    private String title;
    private String content;
  }
}
