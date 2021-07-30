package com.example.demo.initData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Student;
import com.example.demo.repositories.StudentRepository;

@Component
public class ApplicationStartedData implements ApplicationListener<ApplicationContextEvent>{

	@Autowired
	private StudentRepository studentRepository;
	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		studentRepository.save(new Student(null, "Spring Boot","springboot@gmail.com",18));
		studentRepository.save(new Student(null, "Spring Batch","springbatch@gmail.com",12));
		studentRepository.save(new Student(null, "Spring Data","springdata@gmail.com",10));
		
	}

}
