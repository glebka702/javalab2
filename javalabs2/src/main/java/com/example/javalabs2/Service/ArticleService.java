package com.example.javalabs2.Service;

import com.example.javalabs2.Entity.Article;
import com.example.javalabs2.Entity.Comment;
import com.example.javalabs2.Repository.ArticleRepository;
import com.example.javalabs2.Repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public ArticleService(ArticleRepository articleRepository,
                          CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        return article.orElse(null);
    }

    public Article updateArticle(Long id, Article articleDetails) {
        Optional<Article> articleOpt = articleRepository.findById(id);
        if (articleOpt.isEmpty()) {
            return null;
        }

        Article article = articleOpt.get();
        article.setTitle(articleDetails.getTitle());
        article.setContent(articleDetails.getContent());

        return articleRepository.save(article);
    }

    @Transactional
    public boolean deleteArticle(Long id) {
        Optional<Article> articleOpt = articleRepository.findById(id);
        if (articleOpt.isEmpty()) {
            return false;
        }

        Article article = articleOpt.get();

        article.getComments().clear();
        articleRepository.delete(article);

        return true;
    }

    @Transactional
    public Comment addComment(Long articleId, Comment comment) {
        Optional<Article> articleOpt = articleRepository.findById(articleId);
        if (articleOpt.isEmpty()) {
            return null;
        }

        Article article = articleOpt.get();
        comment.setArticle(article);
        article.getComments().add(comment);

        return commentRepository.save(comment);
    }


    public List<Comment> getArticleComments(Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }
}