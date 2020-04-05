package com.homework.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.homework.web.pojo.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
	// 根据分类获取所有文章
	@Query(value = "select * from article where category = :category", nativeQuery = true)
	List<Article> selectByCategory(Integer category);
//
	// 更新文章的分类
	@Modifying(clearAutomatically = true)
	@Query(value = "update article set category=:category where id=:id", nativeQuery = true)
	void updateCategoryById(Integer id, Integer category);
}
