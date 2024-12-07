package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class DeleteRoomByIdTest {

    @InjectMocks
    private DeleteRoomById deleteRoomById;

    @Mock
    private RoomRepository repository;

    @Test
    void shouldDeleteRoomWhenExists() {
        // Arrange
        Long roomId = 1L;
        when(repository.existsById(roomId)).thenReturn(true);

        // Act
        deleteRoomById.execute(roomId);

        // Assert
        verify(repository, times(1)).deleteById(roomId);  // Verifica se deleteById foi chamado uma vez
    }

    @Test
    void shouldThrowExceptionWhenRoomDoesNotExist() {
        // Arrange
        Long roomId = 1L;
        when(repository.existsById(roomId)).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            deleteRoomById.execute(roomId);
        });

        // Verificar a mensagem da exceção
        assertEquals("Room not found", exception.getMessage());

        // Verificar que o método deleteById não foi chamado
        verify(repository, never()).deleteById(roomId);
    }
}
