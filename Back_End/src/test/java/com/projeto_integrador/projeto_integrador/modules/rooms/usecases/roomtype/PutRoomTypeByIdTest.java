package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomTypeRepository;

@ExtendWith(MockitoExtension.class)
class PutRoomTypeByIdTest {

    @InjectMocks
    private PutRoomTypeById putRoomTypeById;

    @Mock
    private RoomTypeRepository roomRepository;

    @Test
    void testExecuteWhenRoomTypeExists() {
        // Cria um tipo de sala simulado
        RoomTypeEntity existingRoomType = new RoomTypeEntity();
        existingRoomType.setRoomTypeId(1L);
        existingRoomType.setRoomTypeDescription("Sala de Aula");

        // Cria a entidade de atualização
        RoomTypeEntity updatedRoomType = new RoomTypeEntity();
        updatedRoomType.setRoomTypeDescription("Laboratório");

        // Simula o comportamento do repositório
        when(roomRepository.findById(1L)).thenReturn(java.util.Optional.of(existingRoomType));
        when(roomRepository.save(any(RoomTypeEntity.class))).thenReturn(updatedRoomType);

        // Executa o método
        RoomTypeEntity result = putRoomTypeById.execute(1L, updatedRoomType);

        // Verifica se a descrição foi atualizada corretamente
        assertEquals("Laboratório", result.getRoomTypeDescription());
    }

    @Test
    void testExecuteWhenRoomTypeDoesNotExist() {
        // Simula o comportamento do repositório quando o tipo de sala não é encontrado
        when(roomRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        // Verifica se a exceção é lançada quando o tipo de sala não é encontrado
        assertThrows(EntityNotFoundException.class, () -> {
            putRoomTypeById.execute(1L, new RoomTypeEntity());
        });
    }
}
