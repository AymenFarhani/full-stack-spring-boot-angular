package com.example.demo.repositories;

import com.example.demo.entities.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class StudentRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void saveStudentTest(){
       Student savedStudent= entityManager.persist(getStudentObject());
        Student studentIndb=studentRepository.getById(savedStudent.getId());
        assertEquals(savedStudent.getEmail(), studentIndb.getEmail());
        assertEquals(savedStudent.getFullName(), studentIndb.getFullName());
    }

    @Test
    public void getAllStudentsTest(){
        Student firstStudent=new Student();
        firstStudent.setAge(20);
        firstStudent.setEmail("springtest@gmail.com");
        firstStudent.setFullName("Spring Test");

        Student secondStudent=new Student();
        secondStudent.setAge(22);
        secondStudent.setEmail("springunittest@gmail.com");
        secondStudent.setFullName("Spring Unit Test");

        Student thirdStudent=new Student();
        thirdStudent.setAge(23);
        thirdStudent.setEmail("springintegrationtest@gmail.com");
        thirdStudent.setFullName("Spring Integration Test");

        entityManager.persist(firstStudent);
        entityManager.persist(secondStudent);
        entityManager.persist(thirdStudent);

        List<Student> studentsInDb=studentRepository.findAll();
        assertEquals(3, studentsInDb.size());
    }

    private Student getStudentObject(){
        Student student = new Student();
        student.setAge(20);
        student.setEmail("springtest@gmail.com");
        student.setFullName("Spring test");
        return student;
    }


}