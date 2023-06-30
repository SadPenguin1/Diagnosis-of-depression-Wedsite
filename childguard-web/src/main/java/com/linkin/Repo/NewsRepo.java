package com.linkin.Repo;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.linkin.entity.News;


public interface NewsRepo extends JpaRepository<News, Long>{
	
	@Query("SELECT pro FROM News pro JOIN pro.category cate WHERE cate.id = :x")
	Page<News> searchByCategoryId(@Param("x") Long categoryId ,Pageable pageable);

}
