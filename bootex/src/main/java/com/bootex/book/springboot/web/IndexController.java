package com.bootex.book.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bootex.book.springboot.service.posts.PostsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IndexController {
	private final PostsService postsService;
	
	@GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }
	
	@GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
}
