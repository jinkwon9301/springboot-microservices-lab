package kuke.board.article.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

@Table(name = "article")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
  @Id
  private Long articleId;
  private String title;
  private String content;
  private Long boardId; // shard key
  private Long writerId; // shard key
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  public static Article create(Long articleId, String title, String content, Long boardId, Long writerId) {
    Article article = new Article();
    article.articleId = articleId;
    article.title = title;
    article.content = content;
    article.boardId = boardId;
    article.writerId = writerId;
    article.createdAt = LocalDateTime.now();
    article.modifiedAt = article.createdAt;
    return article;
  }

  public void update(String title, String content) {
    this.title = title;
    this.content = content;
    this.modifiedAt = LocalDateTime.now();
  }
}
