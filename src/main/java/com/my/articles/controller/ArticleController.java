package com.my.articles.controller;

import com.my.articles.dto.ArticleDTO;
import com.my.articles.service.ArticleService;
import com.my.articles.service.PaginationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.plaf.PanelUI;
import java.util.List;

@Controller
@RequestMapping("articles")
@Slf4j
public class ArticleController {
    private final ArticleService articleService;
    private final PaginationService paginationService;

    public ArticleController(ArticleService articleService, PaginationService paginationService) {
        this.articleService = articleService;
        this.paginationService = paginationService;
    }

    @GetMapping({"", "/"})
    public String showAllArticles(Model model ,
                                  @PageableDefault(page = 0, size = 5,sort = "id" , direction = Sort.Direction.DESC) Pageable pageable) {
//        List<ArticleDTO> articles = articleService.getAllArticles();
//        model.addAttribute("articles", articles);
//        Paging 처리
        Page<ArticleDTO> articles = articleService.getArticlePage(pageable);

        //paging정보 확인하기
        //전체 페이지 수
        int totalPage = articles.getTotalPages();
        //현제 페이지 수
        int currentPage = articles.getNumber();

        System.out.println("totalPage = "+totalPage);
        System.out.println("currentPage = "+currentPage);
        //페이지 블럭을 생성
        List<Integer> barNumbers = paginationService.getPaginationBarNumber(currentPage, totalPage);
        System.out.println("barNumbers = " +barNumbers);

        model.addAttribute("pagebars", barNumbers);
        model.addAttribute("articles", articles);
        return "articles/show_all";
    }

    @GetMapping("one")
    public String one() {
        return "articles/show";
    }

    @GetMapping("{id}")
    public String showOneArticles(@PathVariable("id") Long id, Model model) {
        ArticleDTO dto = articleService.getOneArticle(id);
        //게시글에 id로 요청시 댓글이 달려있는지
        System.out.println(dto);
        model.addAttribute("dto", dto);
        return "articles/show";
    }

    @GetMapping("new")
    public String showNewArticles() {
        return "articles/new";
    }

    @PostMapping("create")
    public ModelAndView createArticle(ArticleDTO articleDTO, Model model) {
        String url = "redirect:/articles";
        articleService.insertArticle(articleDTO);
        return new ModelAndView(url);
    }

    //게시글의 삭제
    @GetMapping("{id}/delete")
    public String deleteArticle(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        String msg = "정장석으로 삭제 되었습니다";
        articleService.deleteArticleById(id);
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/articles";
    }

    //id 받아서 수정 폼 만들기
    @GetMapping("{id}/update")
    public String showUpdateArticles(@PathVariable("id") Long id, Model model) {
        ArticleDTO dto = articleService.getOneArticle(id);
        model.addAttribute("dto", dto);
        return "articles/update";
    }

    @PostMapping("update")
    public String updateArticle(ArticleDTO articleDTO) {
        String url = "redirect:/articles/" + articleDTO.getId();
        articleService.updateArticle(articleDTO);
        return url;
    }
}
