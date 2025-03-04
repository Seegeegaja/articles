package com.my.articles.api.controller;

import com.my.articles.api.exception.BadRequestException;
import com.my.articles.dto.ArticleDTO;
import com.my.articles.dto.CommentDTO;
import com.my.articles.dto.OnlyArticles;
import com.my.articles.service.ArticleService;
import com.my.articles.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleApiController {
    private final ArticleService articleService;
    private final CommentService commentService;

    public ArticleApiController(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    //1.전체 게시글 리스트 가져오기
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleDTO>> findAllArticles() {
        List<ArticleDTO> list = articleService.getAllArticles();
//        List<OnlyArticles> onlyArticleDto = list.stream().map(x -> OnlyArticles.fromArticleDto(x)).toList();
//        주석처리한 객체를 사용해도 됨
        System.out.println(list);
        if (ObjectUtils.isEmpty(list)) {
            throw new BadRequestException("게시글이 없습니다");
        }
        for (ArticleDTO articleDTO : list) {
            articleDTO.setComments(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    //2.특정 게시글의 댓글 전체 리스트 가져오기
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDTO>> findByArticleComments(@PathVariable("articleId") Long articleId) {
        //1.articleId가 있는지 확인
        ArticleDTO dto = articleService.getOneArticle(articleId);
        if (ObjectUtils.isEmpty(dto)) {
            throw new BadRequestException("해당 아이디의 게시글 없음");
        }
        //2.댓글 리스트 가져오기
//        return ResponseEntity.status(HttpStatus.OK).body(dto.getComments());
        List<CommentDTO> list = commentService.findByArticleComments(articleId);
        if (ObjectUtils.isEmpty(list)) {
            throw new BadRequestException("댓글이 비어있음");
        }
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
