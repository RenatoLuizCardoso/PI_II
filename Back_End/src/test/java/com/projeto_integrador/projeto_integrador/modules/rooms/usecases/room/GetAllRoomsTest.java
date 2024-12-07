package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room;

import java.time.LocalDateTime;
import java.util.List;
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
class GetAllRoomTest {

    @InjectMocks
    private GetAllRooms getAllRooms;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @Test
    void shouldReturnRoomListWhenRoomsExist() {
        RoomEntity room = new RoomEntity();
        room.setRoomId(1L);
        room.setRoomCapacity("30");
        room.setRoomFloor("2");
        room.setRoomResources("Projector, Whiteboard");
        room.setRoomAvailability("Livre");

        RoomTypeEntity roomType = new RoomTypeEntity();
        roomType.setRoomTypeId(1L);
        roomType.setRoomTypeDescription("Classroom");
        roomType.setCreate_at(LocalDateTime.now());
        roomType.setUpdate_at(LocalDateTime.now());

        room.setRoomType(roomType);

        when(roomRepository.findAll()).thenReturn(List.of(room));
        when(roomTypeRepository.findById(1L)).thenReturn(Optional.of(roomType));

        List<Map<String, Object>> result = getAllRooms.execute();

        assertNotNull(result);
        assertEquals(1, result.size());
        Map<String, Object> roomData = result.get(0);
        assertEquals(1L, roomData.get("roomId"));
        assertEquals("30", roomData.get("roomCapacity"));
        assertEquals("Classroom", roomData.get("roomType"));
        assertEquals("2", roomData.get("roomFloor"));
        assertEquals("Projector, Whiteboard", roomData.get("roomResources"));
        assertEquals("Livre", roomData.get("roomAvailability"));
    }

    @Test
    void shouldThrowExceptionWhenNoRoomsExist() {
        when(roomRepository.findAll()).thenReturn(List.of());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            getAllRooms.execute();
        });
        assertEquals("Room not Registered", thrown.getMessage());
    }
}
