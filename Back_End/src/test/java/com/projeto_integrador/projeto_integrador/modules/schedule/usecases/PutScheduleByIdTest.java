package com.projeto_integrador.projeto_integrador.modules.schedule.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.PutScheduleById;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;

import jakarta.persistence.EntityNotFoundException;

class PutScheduleByIdTest {

    @InjectMocks
    private PutScheduleById putScheduleById;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ScheduleEntity existingSchedule;

    @BeforeEach
    void setUp() {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);

        // Inicializando a entidade de exemplo que representa um Schedule existente
        existingSchedule = new ScheduleEntity();
        existingSchedule.setScheduleId(1L);
        
        // Criação de objetos de entidades para preencher o schedule existente
        TeacherEntity teacher = new TeacherEntity();
        teacher.setTeacherId(3L);
        existingSchedule.setTeacher(teacher);

        SubjectEntity subject = new SubjectEntity();
        subject.setSubjectId(4L);
        existingSchedule.setSubject(subject);

        TimeEntity time = new TimeEntity();
        time.setTimeId(2L);
        existingSchedule.setTime(time);

        RoomEntity room = new RoomEntity();
        room.setRoomId(5L);
        existingSchedule.setRoom(room);

        CourseEntity course = new CourseEntity();
        course.setCourseId(6L);
        existingSchedule.setCourse(course);
    }

    @Test
    void testExecute_Success() {
        // Dados de entrada para o agendamento atualizado
        Long id = 1L;
        ScheduleEntity updatedSchedule = new ScheduleEntity();
        
        // Criação de objetos de entidades para preencher o agendamento atualizado
        TeacherEntity teacher = new TeacherEntity();
        teacher.setTeacherId(3L);
        updatedSchedule.setTeacher(teacher);
        
        SubjectEntity subject = new SubjectEntity();
        subject.setSubjectId(4L);
        updatedSchedule.setSubject(subject);
        
        TimeEntity time = new TimeEntity();
        time.setTimeId(2L);
        updatedSchedule.setTime(time);
        
        RoomEntity room = new RoomEntity();
        room.setRoomId(5L);
        updatedSchedule.setRoom(room);
        
        CourseEntity course = new CourseEntity();
        course.setCourseId(6L);
        updatedSchedule.setCourse(course);

        // Simula o comportamento do repositório
        when(scheduleRepository.findById(id)).thenReturn(java.util.Optional.of(existingSchedule));
        when(scheduleRepository.save(any(ScheduleEntity.class))).thenReturn(updatedSchedule);

        // Chama o método
        ScheduleEntity result = putScheduleById.execute(id, updatedSchedule);

        // Verifica se o método save foi chamado
        verify(scheduleRepository, times(1)).save(any(ScheduleEntity.class));

        // Verifica o resultado
        assertEquals(updatedSchedule.getTime().getTimeId(), result.getTime().getTimeId());
        assertEquals(updatedSchedule.getTeacher().getTeacherId(), result.getTeacher().getTeacherId());
        assertEquals(updatedSchedule.getSubject().getSubjectId(), result.getSubject().getSubjectId());
        assertEquals(updatedSchedule.getRoom().getRoomId(), result.getRoom().getRoomId());
        assertEquals(updatedSchedule.getCourse().getCourseId(), result.getCourse().getCourseId());
    }

    @Test
    void testExecute_ScheduleNotFound() {
        Long id = 1L;
        ScheduleEntity updatedSchedule = new ScheduleEntity();

        // Simula que o schedule não existe
        when(scheduleRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // Verifica se a exceção é lançada
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            putScheduleById.execute(id, updatedSchedule);
        });

        // Verifica se a mensagem da exceção está correta
        assertEquals("Schedule not found with id: " + id, exception.getMessage());

        // Verifica se o save não foi chamado
        verify(scheduleRepository, never()).save(any(ScheduleEntity.class));
    }

    @Test
    void testExecute_ValidationFail() {
        Long id = 1L;
        ScheduleEntity updatedSchedule = new ScheduleEntity();
        
        // Criação de objetos de entidades para preencher o agendamento atualizado
        TeacherEntity teacher = new TeacherEntity();
        teacher.setTeacherId(3L);
        updatedSchedule.setTeacher(teacher);
        
        SubjectEntity subject = new SubjectEntity();
        subject.setSubjectId(4L);
        updatedSchedule.setSubject(subject);
        
        TimeEntity time = new TimeEntity();
        time.setTimeId(2L);
        updatedSchedule.setTime(time);
        
        RoomEntity room = new RoomEntity();
        room.setRoomId(5L);
        updatedSchedule.setRoom(room);
        
        CourseEntity course = new CourseEntity();
        course.setCourseId(6L);
        updatedSchedule.setCourse(course);

        // Simula que o schedule existe
        when(scheduleRepository.findById(id)).thenReturn(java.util.Optional.of(existingSchedule));

        // Simula falha ao salvar (por exemplo, violação de banco de dados ou erro inesperado)
        doThrow(new IllegalArgumentException("Failed to save schedule")).when(scheduleRepository).save(any(ScheduleEntity.class));

        // Verifica se a exceção é lançada durante o processo de salvar
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            putScheduleById.execute(id, updatedSchedule);
        });

        // Verifica a mensagem da exceção
        assertEquals("Failed to save schedule", exception.getMessage());

        // Verifica se o save foi chamado
        verify(scheduleRepository, times(1)).save(any(ScheduleEntity.class));
    }
}
