package com.homework.web.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.web.pojo.Article;
import com.homework.web.repository.ArticleRepository;
import com.homework.web.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleRepository articleRepository;

	@Override
	public List<Article> selectByCategory(Integer category) {
		return articleRepository.selectByCategory(category);
	}

	@Override
	public Article insert(Article article) {
		article.setCategory(0);
		article.setCreate_time(new Date());
		return articleRepository.save(article);
	}

	@Override
	public void updateCategoryById(Integer id, Integer category) {
		articleRepository.updateCategoryById(id, category);
	}

	@Override
	public void deleteById(Integer id) {
		articleRepository.deleteById(id);
	}

	@Override
	public Article selectById(Integer id) {
		return articleRepository.findById(id).get();
	}

}
