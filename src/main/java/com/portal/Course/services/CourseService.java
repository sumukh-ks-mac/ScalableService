package com.portal.Course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.portal.Course.entity.Course;
import com.portal.Course.entity.Module;
import com.portal.Course.repository.CourseRepository;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course createCourse(Course course) {
        if (course.getModules() != null) {
            for (Module module : course.getModules()) {
                module.setCourse(course);
            }
        }
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course courseDetails) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setTitle(courseDetails.getTitle());
        course.setDescription(courseDetails.getDescription());
        course.setCategory(courseDetails.getCategory());

        if (courseDetails.getModules() != null) {
            course.getModules().clear(); // clear the old modules list
            
            for (Module module : courseDetails.getModules()) {
                module.setCourse(course); // set the parent reference
                course.getModules().add(module); // add module into the existing list
            }
        }

        return courseRepository.save(course);
    }

	/*
	 * public Course updateCourse(Long id, Course courseDetails) { Course course =
	 * courseRepository.findById(id) .orElseThrow(() -> new
	 * RuntimeException("Course not found"));
	 * 
	 * course.setTitle(courseDetails.getTitle());
	 * course.setDescription(courseDetails.getDescription());
	 * course.setCategory(courseDetails.getCategory());
	 * 
	 * if (courseDetails.getModules() != null) { for (Module module :
	 * courseDetails.getModules()) { module.setCourse(course); }
	 * course.setModules(courseDetails.getModules()); }
	 * 
	 * return courseRepository.save(course); }
	 */

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public List<Course> searchCourses(String title, String category) {
        if (title != null && !title.isEmpty() && category != null && !category.isEmpty()) {
            List<Course> titleFiltered = courseRepository.findByTitleContainingIgnoreCase(title);
            return titleFiltered.stream()
                    .filter(course -> course.getCategory() != null &&
                            course.getCategory().equalsIgnoreCase(category))
                    .toList();
        } else if (title != null && !title.isEmpty()) {
            return courseRepository.findByTitleContainingIgnoreCase(title);
        } else if (category != null && !category.isEmpty()) {
            return courseRepository.findByCategoryIgnoreCase(category);
        } else {
            return courseRepository.findAll();
        }
    }
}
