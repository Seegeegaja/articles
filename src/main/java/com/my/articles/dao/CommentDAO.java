package com.my.articles.dao;

import com.my.articles.entity.Article;
import com.my.articles.entity.Comment;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class CommentDAO {
    @Autowired
    EntityManager em ;
    public Long deleteComment(Long id) {
        Comment comment = em.find(Comment.class, id);
        em.remove(comment);
        return comment.getArticle().getId();
    }

    public void insertComment(Long articleId, Comment comment) {
        Article article = em.find(Article.class, articleId);
        //댓글 객체에 게시글 객체 추가
        comment.setArticle(article);
        //게시글의 댓글 리스트에 comment 추 가
        article.getCommentList().add(comment);
        //persist 선언 해서 article 저장
        em.persist(article);

    }

    public Comment findByCommentId(Long commentId) {
        return em.find(Comment.class, commentId);
    }

    public void updateComment(Comment comment) {
        Comment original = em.find(Comment.class, comment.getId());
        //dirty checking 으로 저장 됨
        original.setNickname(comment.getNickname());
        original.setBody(comment.getBody());

    }
}
