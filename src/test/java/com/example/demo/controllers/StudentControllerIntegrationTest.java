package com.example.demo.controllers;

import com.example.demo.StudentApiApplication;
import com.example.demo.entities.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= StudentApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class StudentControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;
    private HttpHeaders headers=new HttpHeaders();

    @Test
    public void createStudentTest() throws JsonProcessingException {
        Student student = new Student();
        student.setId(3L);
        student.setFullName("Spring Integration Testing");
        student.setAge(8);
        student.setEmail("integration@gmail.com");
        String URIToCreateStudent="/api/v1/student";
        String inputJson=this.mapToJson(student);
        HttpEntity<Student> entity=new HttpEntity<>(student, headers);

        ResponseEntity<String> responseEntity=testRestTemplate.exchange(fromFullURLWithPort(URIToCreateStudent),
                HttpMethod.POST, entity, String.class);
        String responseInJson=responseEntity.getBody();
        assertThat(responseInJson).isEqualTo(inputJson);

    }

    @Test
    public void getStudentByIdTest() throws Exception {
        Student student = new Student();
        student.setId(2L);
        student.setFullName("Spring Integration Testing");
        student.setAge(8);
        student.setEmail("integration@gmail.com");

        String inputJson=this.mapToJson(student);

        String URIToCreateStudent="/api/v1/student";
        HttpEntity<Student> entity=new HttpEntity<>(student, headers);
        testRestTemplate.exchange(fromFullURLWithPort(URIToCreateStudent),
                HttpMethod.POST, entity, String.class);
        String URI="/api/v1/student/2";
        String bodyJsonResponse = testRestTemplate.getForObject(fromFullURLWithPort(URI), String.class);
        assertThat(bodyJsonResponse).isEqualTo(inputJson);


    }

    private String fromFullURLWithPort(String uri) {
        return "http://localhost:"+port+uri;
    }

    private String mapToJson(Student student) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(student);
    }
}
