package com.my.articles.service;

import com.my.articles.dao.ArticleDAO;
import com.my.articles.dto.ArticleDTO;
import com.my.articles.entity.Article;
import com.my.articles.repository.ArticleRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleDAO dao;
    private final ArticleRepository articleRepository;

    public List<ArticleDTO> getAllArticles() {
        List<Article> articleList = dao.getAllArticles();
        if (ObjectUtils.isEmpty(articleList)) {
            //비어있으면
            return Collections.emptyList();
        }
        return articleList.stream().map(x -> ArticleDTO.fromEntity(x)).toList();
    }

    public void insertArticle(ArticleDTO dto) {
//        Article article = ArticleDTO.fromDto(dto);
//        dao.insertArticle(article);
        dao.insertArticle(ArticleDTO.fromDto(dto));
    }

    public ArticleDTO getOneArticle(Long id) {
        Article article = dao.getOneArticle(id);
        if (ObjectUtils.isEmpty(article)) {
            return null;
        }

        return ArticleDTO.fromEntity(article);
    }


    public void deleteArticleById(Long id) {
        dao.deleteArticle(id);
    }

    public void updateArticle(ArticleDTO articleDTO) {
        dao.updateArticle(ArticleDTO.fromDto(articleDTO));
    }


    public Page<ArticleDTO> getArticlePage(Pageable pageable) {
        Page<Article> articles = articleRepository.findAll(pageable);
        return articles.map(x -> ArticleDTO.fromEntity(x));
    }
}
