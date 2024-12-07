package com.projeto_integrador.projeto_integrador.modules.teacher.controller;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.CreateTeacher;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.DeleteTeacherById;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.GetAllTeachers;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.GetTeacherById;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.PutTeacherById;

@WebMvcTest(controllers = TeacherController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateTeacher createTeacher;

    @MockBean
    private GetAllTeachers getAllTeachers;

    @MockBean
    private GetTeacherById getTeacherById;

    @MockBean
    private PutTeacherById putTeacherById;

    @MockBean
    private DeleteTeacherById deleteTeacherById;

    private TeacherEntity teacherEntity;

    @BeforeEach
    void setUp() {
        teacherEntity = TeacherEntity.builder()
                .teacherId(1L)
                .teacherName("John Doe")
                .institutionalEmail("johndoe@school.com")
                .personalEmail("johndoe@gmail.com")
                .teacherPassword("password123")
                .personalPhone("123456789")
                .businessPhone("987654321")
                .researchLine("Artificial Intelligence")
                .teacherArea("Computer Science")
                .build();
    }

    @Test
    void testCreateTeacher() throws Exception {
        Mockito.when(createTeacher.execute(any(TeacherEntity.class))).thenReturn(teacherEntity);

        mockMvc.perform(post("/teacher/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(teacherEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teacherName").value("John Doe"))
                .andExpect(jsonPath("$.institutionalEmail").value("johndoe@school.com"));
    }

    @Test
    void testGetAllTeachers() throws Exception {
        List<Map<String, Object>> teachers = List.of(
                Map.of("teacherName", "John Doe", "institutionalEmail", "johndoe@school.com")
        );

        Mockito.when(getAllTeachers.execute()).thenReturn(teachers);

        mockMvc.perform(get("/teacher/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].teacherName").value("John Doe"))
                .andExpect(jsonPath("$[0].institutionalEmail").value("johndoe@school.com"));
    }

    @Test
    void testGetTeacherById() throws Exception {
        Map<String, Object> teacherMap = Map.of(
                "teacherName", "John Doe",
                "institutionalEmail", "johndoe@school.com"
        );

        Mockito.when(getTeacherById.execute(anyLong())).thenReturn(teacherMap);

        mockMvc.perform(get("/teacher/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teacherName").value("John Doe"))
                .andExpect(jsonPath("$.institutionalEmail").value("johndoe@school.com"));
    }

    @Test
    void testUpdateTeacher() throws Exception {
        Mockito.when(putTeacherById.execute(anyLong(), any(TeacherEntity.class))).thenReturn(teacherEntity);

        mockMvc.perform(put("/teacher/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(teacherEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teacherName").value("John Doe"))
                .andExpect(jsonPath("$.institutionalEmail").value("johndoe@school.com"));
    }

    @Test
    void testDeleteTeacher() throws Exception {
        mockMvc.perform(delete("/teacher/1"))
                .andExpect(status().isOk());
        Mockito.verify(deleteTeacherById).execute(1L);
    }
}
