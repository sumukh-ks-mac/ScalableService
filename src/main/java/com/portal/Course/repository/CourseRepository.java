package com.portal.Course.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portal.Course.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByTitleContainingIgnoreCase(String title);

    List<Course> findByCategoryIgnoreCase(String category);
}

