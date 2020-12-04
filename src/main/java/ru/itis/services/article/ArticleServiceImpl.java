package ru.itis.services.article;

import ru.itis.dto.ArticleDto;
import ru.itis.models.Article;
import ru.itis.repositories.articles.ArticlesRepository;

import java.util.List;
import java.util.Optional;

public class ArticleServiceImpl implements ArticleService {

    private ArticlesRepository articlesRepository;

    public ArticleServiceImpl(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    @Override
    public void save(Article article) {
        articlesRepository.save(article);
    }

    @Override
    public void delete(Integer id) {
        articlesRepository.removeById(id);
    }

    @Override
    public void like(Integer articleId, Integer fromUserId, Integer toUserId, Integer countLikes) {
        articlesRepository.like(articleId, fromUserId, toUserId, countLikes);
    }

    @Override
    public List<Integer> findAllIdsWhereLiked(Integer userId) {
        return articlesRepository.findAllIdsWhereLiked(userId);
    }

    @Override
    public Optional<List<ArticleDto>> showUserArticles(Integer id) {
        return articlesRepository.findAllUserArticlesById(id);
    }
}
