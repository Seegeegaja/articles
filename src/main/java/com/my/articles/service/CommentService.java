package com.my.articles.service;

import com.my.articles.dao.CommentDAO;
import com.my.articles.dto.CommentDTO;
import com.my.articles.entity.Comment;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommentService {
    private final CommentDAO dao;

    public CommentService(CommentDAO dao) {
        this.dao = dao;
    }

    public Long deleteComment(Long id) {
        return dao.deleteComment(id);
    }

    public void insertComment(CommentDTO dto, Long articleId) {
        dao.insertComment(articleId, CommentDTO.fromDto(dto));
    }

    public Map<String, Object> findByCommentId(Long commentId) {
        Comment comment = dao.findByCommentId(commentId);
        Map<String, Object> data = new HashMap<>();
        if (ObjectUtils.isEmpty(commentId)) {
            data.put("articleId", null);
            data.put("dto", null);
        } else {
            data.put("articleId", comment.getArticle().getId());
            data.put("dto", CommentDTO.fromEntity(comment));
        }
        return data;
    }

    public void updateComment(CommentDTO dto) {
        dao.updateComment(CommentDTO.fromDto(dto));
    }
}
