package com.homework.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homework.web.pojo.Drug;

public interface DrugRepository extends JpaRepository<Drug, Integer> {

}
