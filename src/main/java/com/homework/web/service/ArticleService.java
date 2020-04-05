package com.homework.web.service;

import java.util.List;

import com.homework.web.pojo.Article;

public interface ArticleService {

	Article insert(Article article);
//
	Article selectById(Integer id);
//
	List<Article> selectByCategory(Integer category);
//	
	void updateCategoryById(Integer id, Integer category);
//	
	void deleteById(Integer id);
}
