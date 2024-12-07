package com.projeto_integrador.projeto_integrador.modules.schedule.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;

import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.PutScheduleById;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;

@ExtendWith(MockitoExtension.class)
public class ScheduleControllerTest {

    @InjectMocks
    private ScheduleController scheduleController;

    @Mock
    private PutScheduleById putScheduleById;

    @Mock
    private TeacherEntity teacher;

    @Mock
    private SubjectEntity subject;

    @Mock
    private TimeEntity time;

    @Mock
    private RoomEntity room;

    @Mock
    private CourseEntity course;

    @BeforeEach
    void setUp() {
        // Nenhum setup necessário aqui, os mocks são injetados pelo MockitoExtension
    }

    @Test
    void testPutSchedule() {

        // Criação de uma entidade ScheduleEntity com mocks de dependências
        ScheduleEntity updatedSchedule = ScheduleEntity.builder()
            .scheduleId(1L)
            .teacher(teacher)    // Mock do Teacher
            .subject(subject)    // Mock do Subject
            .time(time)          // Mock do Time
            .room(room)          // Mock do Room
            .course(course)      // Mock do Course
            .weekDay("Segunda-Feira")
            .build();

        // Configuração do mock para o método putScheduleById
        when(putScheduleById.execute(any(Long.class), any(ScheduleEntity.class))).thenReturn(updatedSchedule);

        // Chamando o método no controller
        ResponseEntity<?> response = scheduleController.putSchedule(updatedSchedule, 1L);

        // Verificando se a resposta está correta
        assertEquals(200, response.getStatusCode().value());
        assertEquals(teacher, ((ScheduleEntity) response.getBody()).getTeacher());  // Verificando o novo professor
        assertEquals("Segunda-Feira", ((ScheduleEntity) response.getBody()).getWeekDay());  // Verificando o novo dia
    }
}
