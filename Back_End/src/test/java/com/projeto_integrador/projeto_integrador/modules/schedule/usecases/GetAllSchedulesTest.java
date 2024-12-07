package com.projeto_integrador.projeto_integrador.modules.schedule.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;
import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.GetAllSchedules;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;
import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;

import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GetAllSchedulesTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TimeRepository timeRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private GetAllSchedules getAllSchedules;

    @BeforeEach
    public void setUp() {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute_ReturnsSchedules() {
        // Arrange: cria objetos de exemplo para ScheduleEntity e as entidades associadas
        SubjectEntity subject = new SubjectEntity();
        subject.setSubjectName("Math");

        TeacherEntity teacher = new TeacherEntity();
        teacher.setTeacherName("John Doe");

        TimeEntity time = new TimeEntity();
        time.setStartTime("08:00");
        time.setEndTime("10:00");

        CourseEntity course = new CourseEntity();
        course.setCourseName("Computer Science");
        course.setCourseSemester("Fall");

        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setScheduleId(1L);
        schedule.setSubject(subject);
        schedule.setTeacher(teacher);
        schedule.setTime(time);
        schedule.setCourse(course);

        List<ScheduleEntity> allSchedules = Arrays.asList(schedule);

        // Mockando os comportamentos dos repositórios
        when(scheduleRepository.findAll()).thenReturn(allSchedules);
        when(subjectRepository.findById(101L)).thenReturn(Optional.of(subject));
        when(teacherRepository.findById(201L)).thenReturn(Optional.of(teacher));
        when(timeRepository.findById(301L)).thenReturn(Optional.of(time));
        when(courseRepository.findById(401L)).thenReturn(Optional.of(course));

        // Act: chamando o método a ser testado
        List<Map<String, Object>> result = getAllSchedules.execute();

        // Assert: verificando se o resultado não está vazio
        assertNotNull(result);
        assertFalse(result.isEmpty());

        // Verificando o conteúdo do mapa
        Map<String, Object> scheduleMap = result.get(0);
        assertEquals(1L, scheduleMap.get("scheduleId"));
        assertEquals("Math", scheduleMap.get("subject"));
        assertEquals("John Doe", scheduleMap.get("teacher"));
        assertEquals("08:00 - 10:00 (Monday)", scheduleMap.get("time"));
        assertEquals("Computer Science - Fall", scheduleMap.get("course"));

        // Verificando se os métodos dos repositórios foram chamados corretamente
        verify(scheduleRepository).findAll();
        verify(subjectRepository).findById(101L);
        verify(teacherRepository).findById(201L);
        verify(timeRepository).findById(301L);
        verify(courseRepository).findById(401L);
    }

    @Test
    public void testExecute_ThrowsEntityNotFoundException_WhenNoSchedules() {
        // Arrange: quando não houver nenhum agendamento
        when(scheduleRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert: verificando se a exceção é lançada
        assertThrows(EntityNotFoundException.class, () -> {
            getAllSchedules.execute();
        });
    }
}
