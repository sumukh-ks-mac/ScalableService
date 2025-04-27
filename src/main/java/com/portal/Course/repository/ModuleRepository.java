package com.portal.Course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portal.Course.entity.Module;

public interface ModuleRepository extends JpaRepository<Module, Long> {
	
}