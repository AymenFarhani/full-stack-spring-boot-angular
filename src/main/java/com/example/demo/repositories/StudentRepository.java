package com.example.demo.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

	@Query("SELECT s FROM Student s WHERE s.fullName like %:fullName%")
	Page<Student> getStudentByFullName(@Param(value = "fullName")String fullName, Pageable paging);
	
	@Query("SELECT s FROM Student s WHERE s.fullName like %:email%")
	Page<Student> getStudentsByEmail(@Param(value = "email")String email, Pageable paging);
	
	@Query("SELECT s FROM Student s WHERE s.fullName like %:fullName% and s.email like %:email%")
	Page<Student> getStudentsByFullNameAndEmail(@Param(value = "fullName")String fullName,@Param(value = "email")String email, Pageable paging);
}
