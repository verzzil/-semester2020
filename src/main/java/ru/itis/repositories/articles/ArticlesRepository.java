package ru.itis.repositories.articles;

import ru.itis.dto.ArticleDto;
import ru.itis.models.Article;
import ru.itis.repositories.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArticlesRepository extends CrudRepository<Article> {
    Optional<List<ArticleDto>> findAll();
    Optional<List<ArticleDto>> findAllUserArticlesById(Integer id);
    void like(Integer articleId, Integer fromUserId, Integer toUserId, Integer countLikes);
    List<Integer> findAllIdsWhereLiked(Integer userId);
}
