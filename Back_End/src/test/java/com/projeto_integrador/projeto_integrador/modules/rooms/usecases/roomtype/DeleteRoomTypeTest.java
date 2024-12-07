package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype;

import static org.junit.jupiter.api.Assertions.assertEquals;  // Certifique-se de importar corretamente
import static org.junit.jupiter.api.Assertions.assertThrows;  // Adicionada a importação para assertThrows
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomTypeRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class DeleteRoomTypeTest {

    @InjectMocks
    private DeleteRoomTypeById deleteRoomTypeById;

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @Test
    void testDeleteRoomTypeByIdSuccess() {
        Long roomTypeId = 1L;

        // Simular que o tipo de sala existe
        when(roomTypeRepository.existsById(roomTypeId)).thenReturn(true);

        // Executar o método
        deleteRoomTypeById.execute(roomTypeId);

        // Verificar se o método deleteById foi chamado
        verify(roomTypeRepository).deleteById(roomTypeId);
    }

    @Test
    void testDeleteRoomTypeByIdNotFound() {
        Long roomTypeId = 1L;

        // Simular que o tipo de sala não existe
        when(roomTypeRepository.existsById(roomTypeId)).thenReturn(false);

        // Executar o método e verificar se a exceção é lançada
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            deleteRoomTypeById.execute(roomTypeId);
        });

        // Verificar a mensagem da exceção
        assertEquals("RoomType not found", exception.getMessage());
    }
}
