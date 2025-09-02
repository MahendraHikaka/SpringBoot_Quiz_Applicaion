package com.mahi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahi.model.Result;

@Repository
public interface ResultRepo extends JpaRepository<Result, Integer> {
	
}
