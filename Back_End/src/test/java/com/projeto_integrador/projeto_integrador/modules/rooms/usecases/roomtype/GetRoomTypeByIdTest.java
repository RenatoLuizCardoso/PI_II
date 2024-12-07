package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomTypeRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class GetRoomTypeByIdTest {

    @InjectMocks
    private GetRoomTypeById getRoomTypeById;

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @Test
    void testExecuteWhenRoomTypeExists() {
        Long roomTypeId = 1L;
        
        // Criando um tipo de sala simulado
        RoomTypeEntity roomType = new RoomTypeEntity();
        roomType.setRoomTypeId(roomTypeId);
        roomType.setRoomTypeDescription("Sala de Aula");

        // Simulando o retorno do repositório
        when(roomTypeRepository.findById(roomTypeId)).thenReturn(Optional.of(roomType));

        // Executar o método
        RoomTypeEntity result = getRoomTypeById.execute(roomTypeId);

        // Verificar se o tipo de sala retornado é o esperado
        assertEquals(roomTypeId, result.getRoomTypeId());
        assertEquals("Sala de Aula", result.getRoomTypeDescription());
    }

    @Test
    void testExecuteWhenRoomTypeDoesNotExist() {
        Long roomTypeId = 1L;

        // Simulando que o repositório retorna vazio
        when(roomTypeRepository.findById(roomTypeId)).thenReturn(Optional.empty());

        // Executar o método e verificar se a exceção é lançada
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            getRoomTypeById.execute(roomTypeId);
        });

        // Verificar a mensagem da exceção
        assertEquals("RoomType not found", exception.getMessage());
    }
}
