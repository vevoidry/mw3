package com.homework.web.service;

import java.util.List;

import com.homework.web.pojo.Disease;

public interface DiseaseService {
	List<Disease> selectByRank(Integer rank);

	List<Disease> selectByParent_id(Integer parent_id);
	
	Disease insert(Disease disease);
	
	Disease selectByParent_idRankName(Integer parent_id, Integer rank, String name);
	
	Disease selectById(Integer id);
}
