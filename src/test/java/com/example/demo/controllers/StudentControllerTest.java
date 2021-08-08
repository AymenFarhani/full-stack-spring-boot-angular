package com.example.demo.controllers;

import com.example.demo.entities.Student;
import com.example.demo.repositories.StudentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    public void createStudentTest() throws Exception{
        Student student = new Student();
        student.setId(1L);
        student.setFullName("Spring Unit Testing");
        student.setAge(10);
        student.setEmail("spring@gmail.com");

        String inputJson = this.mapToJson(student);
        String URI = "/api/v1/student";

        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);

        RequestBuilder requestBuilder= MockMvcRequestBuilders.post(URI)
                .accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response=result.getResponse();
        String outputJson = result.getResponse().getContentAsString();
        assertThat(outputJson).isEqualTo(inputJson);
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

   /* @Test
    public void getStudentByIdTest() throws Exception {
        Student student = new Student();
        student.setId(2L);
        student.setFullName("Spring Integration Testing");
        student.setAge(20);
        student.setEmail("integration@gmail.com");
        Mockito.when(studentRepository.getById(Mockito.anyLong())).thenReturn(student);

        String URI="/api/v1/student/2";

        RequestBuilder requestBuilder=MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectJson = this.mapToJson(student);
        String resultJson= result.getResponse().getContentAsString();
        assertThat(resultJson).isEqualTo(expectJson);

    }*/

    private String mapToJson(Student student) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(student);
    }
}