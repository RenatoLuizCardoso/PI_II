package com.projeto_integrador.projeto_integrador.modules.schedule.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.CreateSchedule;
import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;

@ExtendWith(MockitoExtension.class)
public class CreateScheduleTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private CreateSchedule createSchedule;

    private ScheduleEntity scheduleEntity;

    @Mock
    private TeacherEntity teacherEntity;

    @Mock
    private SubjectEntity subjectEntity;

    @Mock
    private TimeEntity timeEntity;

    @Mock
    private RoomEntity roomEntity;

    @Mock
    private CourseEntity courseEntity;

    @BeforeEach
    public void setUp() {
        // Inicializa o objeto ScheduleEntity com os mocks
        scheduleEntity = ScheduleEntity.builder()
                .teacher(teacherEntity)
                .subject(subjectEntity)
                .time(timeEntity)
                .room(roomEntity)
                .course(courseEntity)
                .weekDay("Segunda-Feira")
                .build();
    }

    @Test
    public void testExecute_CreatesSchedule() {
        // Arrange: mockando o comportamento do repository
        when(scheduleRepository.save(any(ScheduleEntity.class))).thenReturn(scheduleEntity);

        // Act: chamando o método a ser testado
        ScheduleEntity result = createSchedule.execute(scheduleEntity);

        // Assert: verificando que o repository.save foi chamado corretamente
        verify(scheduleRepository).save(scheduleEntity);

        // Assert: verificando o resultado
        assertNotNull(result);
        assertEquals(scheduleEntity, result);
    }
}
