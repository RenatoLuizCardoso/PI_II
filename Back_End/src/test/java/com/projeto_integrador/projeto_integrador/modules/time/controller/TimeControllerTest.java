package com.projeto_integrador.projeto_integrador.modules.time.controller;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.CreateTime;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.DeleteTimeById;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.GetAllTimes;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.GetTimeById;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.PutTimeById;

import jakarta.persistence.EntityNotFoundException;

class TimeControllerTest {

    @InjectMocks
    private TimeController timeController;

    @Mock
    private CreateTime createTime;

    @Mock
    private GetAllTimes getAllTimes;

    @Mock
    private GetTimeById getTimeById;

    @Mock
    private PutTimeById putTimeById;

    @Mock
    private DeleteTimeById deleteTimeById;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldReturnCreatedTime_WhenSuccessful() throws Exception {
        // Arrange
        TimeEntity time = new TimeEntity(1L, "08:00", "12:00", null, null);
        when(createTime.execute(time)).thenReturn(time);

        // Act
        ResponseEntity<Object> response = timeController.create(time);

        // Assert
        assertEquals(200, response.getStatusCodeValue()); // Note que o código de status pode ser 201 se for Created
        assertEquals(time, response.getBody());
        verify(createTime, times(1)).execute(time);
    }

    @Test
    void getAllTimes_ShouldReturnListOfTimes_WhenSuccessful() throws Exception {
        // Arrange
        List<TimeEntity> times = Arrays.asList(
                new TimeEntity(1L, "08:00", "12:00", null, null),
                new TimeEntity(2L, "14:00", "18:00", null, null)
        );
        when(getAllTimes.execute()).thenReturn(times);

        // Act
        ResponseEntity<Object> response = timeController.getAllTimes();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(times, response.getBody());
        verify(getAllTimes, times(1)).execute();
    }

    @Test
    void getById_ShouldReturnTime_WhenFound() throws Exception {
        // Arrange
        TimeEntity time = new TimeEntity(1L, "08:00", "12:00", null, null);
        when(getTimeById.execute(1L)).thenReturn(time);

        // Act
        ResponseEntity<Object> response = timeController.getById(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(time, response.getBody());
        verify(getTimeById, times(1)).execute(1L);
    }

    @Test
    void getById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(getTimeById.execute(1L)).thenThrow(new EntityNotFoundException("Time not found"));

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> timeController.getById(1L));
        assertEquals("Time not found", exception.getMessage());
        verify(getTimeById, times(1)).execute(1L);
    }

    @Test
    void putTime_ShouldReturnUpdatedTime_WhenSuccessful() throws Exception {
        // Arrange
        TimeEntity updatedTime = new TimeEntity(1L, "09:00", "13:00", null, null);
        when(putTimeById.execute(1L, updatedTime)).thenReturn(updatedTime);

        // Act
        ResponseEntity<Object> response = timeController.putTime(updatedTime, 1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedTime, response.getBody());
        verify(putTimeById, times(1)).execute(1L, updatedTime);
    }

    @Test
    void deleteTime_ShouldReturnOk_WhenSuccessful() {
        // Arrange
        doNothing().when(deleteTimeById).execute(1L);

        // Act
        ResponseEntity<Object> response = timeController.deleteTime(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(deleteTimeById, times(1)).execute(1L);
    }
}
