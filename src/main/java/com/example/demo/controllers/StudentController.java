package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Student;
import com.example.demo.repositories.StudentRepository;

@RestController
@RequestMapping(value = "/api/v1")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@PostMapping(value = "/student")
	public ResponseEntity<Student> createStudent(@RequestBody Student student) {
		return ResponseEntity.status(HttpStatus.OK).body(studentRepository.save(student));
	}

	@GetMapping(value = "/students")
	public ResponseEntity<List<Student>> getAllStudents() {
		return ResponseEntity.status(HttpStatus.OK).body(studentRepository.findAll());
	}

	@GetMapping(value = "/student/{studentId}")
	public ResponseEntity<Student> getStudentById(@PathVariable(name = "studentId") Long studentId) {
		return ResponseEntity.status(HttpStatus.OK).body(studentRepository.findById(studentId).get());
	}
}
