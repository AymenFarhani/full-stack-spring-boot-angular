package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PutMapping(value = "/student/{studentId}")
	public ResponseEntity<Student> updateStudent(@PathVariable(name = "studentId") Long studentId,
			@RequestBody Student student) {
		Optional<Student> savedStudentOptional = studentRepository.findById(studentId);
		if (savedStudentOptional.isPresent()) {
			Student savedStudent = savedStudentOptional.get();
			savedStudent.setAge(student.getAge());
			savedStudent.setEmail(student.getEmail());
			savedStudent.setFullName(student.getFullName());
			return ResponseEntity.status(HttpStatus.OK).body(studentRepository.save(savedStudent));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(studentRepository.save(student));
		}
	}

	@DeleteMapping(value = "/student/{studentId}")
	public ResponseEntity<?> deleteStudentById(@PathVariable(name = "studentId") Long studentId) {
		Student student = studentRepository.getById(studentId);
		if (student != null) {
			studentRepository.delete(student);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
