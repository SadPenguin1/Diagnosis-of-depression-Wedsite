package com.linkin.Repo;


import org.springframework.data.jpa.repository.JpaRepository;


import com.linkin.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>{
	
}
