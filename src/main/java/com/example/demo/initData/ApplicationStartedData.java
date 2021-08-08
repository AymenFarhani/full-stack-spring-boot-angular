package com.example.demo.initData;

import com.example.demo.entities.Student;
import com.example.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;



@Component
public class ApplicationStartedData implements ApplicationListener<ApplicationContextEvent>{

	@Autowired
	private StudentRepository studentRepository;
	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		studentRepository.save(new Student("Spring Boot","springboot@gmail.com",18));
		studentRepository.save(new Student("Spring Batch","springbatch@gmail.com",12));
		studentRepository.save(new Student("Spring Data","springdata@gmail.com",10));
		studentRepository.save(new Student("Hibernate","hibernate@gmail.com",15));
		studentRepository.save(new Student("Java","java@gmail.com",20));
		studentRepository.save(new Student("Kubernetes","kubernetes@gmail.com",6));
		studentRepository.save(new Student("Clean Code","cleancode@gmail.com",18));
		studentRepository.save(new Student("Design Pattern","designpattern@gmail.com",21));
		studentRepository.save(new Student("Web Services","ws@gmail.com",13));
		
		
	}

}
