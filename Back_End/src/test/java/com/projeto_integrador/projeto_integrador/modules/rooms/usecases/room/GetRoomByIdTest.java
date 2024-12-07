package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room;

import java.util.Map;
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
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomTypeRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class GetRoomByIdTest {

    @InjectMocks
    private GetRoomById getRoomById;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @Test
    void shouldReturnRoomDetailsWhenRoomExists() {
        // Arrange: Criando uma sala fictícia
        RoomEntity room = new RoomEntity();
        room.setRoomId(1L);
        room.setRoomCapacity("30");
        room.setRoomFloor("2");
        room.setRoomResources("Projector, Whiteboard");
        room.setRoomAvailability("Livre");

        // Criando o tipo de sala fictício
        RoomTypeEntity roomType = new RoomTypeEntity();
        roomType.setRoomTypeId(1L);
        roomType.setRoomTypeDescription("Classroom");

        // Atribuindo o tipo de sala ao objeto RoomEntity
        room.setRoomType(roomType);

        // Simulando o comportamento dos repositórios
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomTypeRepository.findById(1L)).thenReturn(Optional.of(roomType));

        // Act: Executando o método
        Map<String, Object> result = getRoomById.execute(1L);

        // Assert: Verificando se os valores retornados estão corretos
        assertNotNull(result);
        assertEquals(1L, result.get("roomId"));
        assertEquals("30", result.get("roomCapacity"));
        assertEquals("Classroom", result.get("roomType"));
    }

    @Test
    void shouldThrowExceptionWhenRoomNotFound() {
        // Arrange: Simulando que não existe uma sala com ID 1
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Verificando se a exceção é lançada
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            getRoomById.execute(1L);
        });
        assertEquals("Room not found", thrown.getMessage());
    }
}
