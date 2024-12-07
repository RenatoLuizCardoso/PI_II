package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class PutRoomByIdTest {

    @InjectMocks
    private PutRoomById putRoomById;

    @Mock
    private RoomRepository roomRepository;

    @Test
    void shouldUpdateRoomWhenRoomExists() {
        // Preparar dados de entrada
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setRoomId(1L);
        roomEntity.setRoomCapacity("50");
        roomEntity.setRoomFloor("1");
        roomEntity.setRoomResources("Projector, Whiteboard");
        roomEntity.setRoomAvailability("Occupied");
        
        RoomTypeEntity roomType = new RoomTypeEntity();
        roomType.setRoomTypeId(2L);
        roomType.setRoomTypeDescription("Lab");

        roomEntity.setRoomType(roomType);

        // Preparar o "updatedRoom"
        RoomEntity updatedRoom = new RoomEntity();
        updatedRoom.setRoomId(1L);
        updatedRoom.setRoomCapacity("50");
        updatedRoom.setRoomFloor("1");
        updatedRoom.setRoomResources("Projector, Whiteboard");
        updatedRoom.setRoomAvailability("Occupied");
        updatedRoom.setRoomType(roomType); // Atualizando o tipo da sala

        // Simular comportamento do repositório
        when(roomRepository.findById(1L)).thenReturn(Optional.of(roomEntity));
        when(roomRepository.save(updatedRoom)).thenReturn(updatedRoom);

        // Executar a atualização
        RoomEntity result = putRoomById.execute(1L, updatedRoom);

        // Verificar o resultado
        assertNotNull(result);
        assertEquals(1L, result.getRoomId());
        assertEquals("50", result.getRoomCapacity());
        assertEquals("1", result.getRoomFloor());
        assertEquals("Projector, Whiteboard", result.getRoomResources());
        assertEquals("Occupied", result.getRoomAvailability());
        assertEquals(roomType, result.getRoomType());
    }

    @Test
    void shouldThrowExceptionWhenRoomNotFound() {
        // Simular comportamento do repositório quando não encontrar a sala
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        // Executar e verificar a exceção
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            putRoomById.execute(1L, new RoomEntity());
        });

        assertEquals("Room not found", thrown.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenRoomTypeIsInvalid() {
        // Preparar dados de entrada
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setRoomId(1L);
        roomEntity.setRoomType(new RoomTypeEntity());  // Tipo de sala inválido (não existe)

        // Simular comportamento do repositório
        when(roomRepository.findById(1L)).thenReturn(Optional.of(roomEntity));

        // Executar e verificar a exceção
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            putRoomById.execute(1L, roomEntity);
        });

        assertEquals("Invalid room type", thrown.getMessage());
    }
}
