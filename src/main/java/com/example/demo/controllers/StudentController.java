package com.example.demo.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Student;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.service.ExcelGenerator;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(origins = "*")
public class StudentController{

	@Autowired
	private StudentRepository studentRepository;

	@PostMapping(value = "/student")
	public ResponseEntity<Student> createStudent(@RequestBody Student student) {
		return ResponseEntity.status(HttpStatus.OK).body(studentRepository.save(student));
	}

	@GetMapping(value = "/students/v2")
	public ResponseEntity<List<Student>> getAllStudentsWithoutFilter() {
		return ResponseEntity.status(HttpStatus.OK).body(studentRepository.findAll());
	}

	@GetMapping(value = "/students")
	public ResponseEntity<Map<String, Object>> getAllStudents(@RequestParam(required = false) String fullName,
			@RequestParam(required = false) String email, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "2") int size) {
		Pageable paging = PageRequest.of(page, size);
		Page<Student> stds;
		if (fullName != null && email == null) {
			stds = studentRepository.getStudentByFullName(fullName, paging);
		} else if (email != null && fullName == null) {
			stds = studentRepository.getStudentsByEmail(email, paging);
		} else if (fullName != null && email != null) {
			stds = studentRepository.getStudentsByFullNameAndEmail(fullName, email, paging);
		} else {
			stds = studentRepository.findAll(paging);
		}
		List<Student> students;
		students = stds.getContent();

		Map<String, Object> response = new HashMap<>();
		response.put("students", students);
		response.put("currentPage", stds.getNumber());
		response.put("totalItems", stds.getTotalElements());
		response.put("totalPages", stds.getTotalPages());
		return ResponseEntity.status(HttpStatus.OK).body(response);

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

	@GetMapping(value = "/download/students")
	public ResponseEntity<?> excelStudentsReport() throws IOException {
		List<Student> students = studentRepository.findAll();

		ByteArrayInputStream in = ExcelGenerator.studentsToExcel(students);
		String filename = "students.xlsx";
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(new InputStreamResource(in));
	}
}
