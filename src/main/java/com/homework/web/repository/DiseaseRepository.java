package com.homework.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homework.web.pojo.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Integer> {
	@Query(value = "select * from disease where rank = :rank", nativeQuery = true)
	List<Disease> selectByRank(Integer rank);

	@Query(value = "select * from disease where parent_id = :parent_id", nativeQuery = true)
	List<Disease> selectByParent_id(Integer parent_id);

	@Query(value = "select * from disease where parent_id = :parent_id and rank=:rank and name=:name", nativeQuery = true)
	Disease selectByParent_idRankName(Integer parent_id, Integer rank, String name);
}
