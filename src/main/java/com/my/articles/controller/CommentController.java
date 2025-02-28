package com.my.articles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommentController {
    @GetMapping("comment")
    public String showComment(){
        return "articles/update_comment";
    }
}
