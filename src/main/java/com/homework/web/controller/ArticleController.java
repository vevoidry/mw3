package com.homework.web.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homework.web.pojo.Article;
import com.homework.web.service.impl.ArticleServiceImpl;

@Controller
@RequestMapping("/articles")
public class ArticleController {

	@Autowired
	ArticleServiceImpl articleServiceImpl;

	@PostMapping
	@ResponseBody
	public HashMap<String, String> post(String title, String content, Model model) {
		Article article = new Article();
		article.setTitle(title.trim());
		article.setContent(content.trim());
		if (article.getTitle().equals("")) {
			throw new RuntimeException("标题不可为空");
		}
		if (article.getContent().equals("")) {
			throw new RuntimeException("内容不可为空");
		}
		articleServiceImpl.insert(article);
		return new HashMap<String, String>();
	}

	@GetMapping("/{id:[0-9]*}/category/{category:[0-9]*}")
	@ResponseBody
	@Transactional
	public HashMap<String, String> article_category(@PathVariable Integer id, @PathVariable Integer category) {
		articleServiceImpl.updateCategoryById(id, category);
		return new HashMap<String, String>();
	}

	@GetMapping("/delete/{id:[0-9]*}")
	@ResponseBody
	@Transactional
	public HashMap<String, String> article_delete(@PathVariable Integer id) {
		articleServiceImpl.deleteById(id);
		return new HashMap<String, String>();
	}

	@GetMapping("/{id:[0-9]*}")
	public String article(@PathVariable Integer id, Model model) {
		Article article = articleServiceImpl.selectById(id);
		model.addAttribute("article", article);
		return "article";
	}

}
