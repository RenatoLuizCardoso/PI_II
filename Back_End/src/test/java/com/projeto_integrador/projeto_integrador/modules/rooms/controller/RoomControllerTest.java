package com.projeto_integrador.projeto_integrador.modules.rooms.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room.CreateRoom;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room.DeleteRoomById;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room.GetAllRooms;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room.GetRoomById;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room.PutRoomById;

class RoomControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RoomController roomController;

    @Mock
    private CreateRoom createRoom;

    @Mock
    private GetAllRooms getAllRooms;

    @Mock
    private GetRoomById getRoomById;

    @Mock
    private PutRoomById putRoomById;

    @Mock
    private DeleteRoomById deleteRoomById;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roomController).build();
    }

    @Test
    void testCreateRoom_Success() throws Exception {
        RoomTypeEntity roomType = new RoomTypeEntity(); // Crie uma instância de RoomTypeEntity, ajustando conforme sua classe
        roomType.setRoomTypeId(1L);
        roomType.setRoomTypeDescription("Laboratório");

        RoomEntity roomEntity = new RoomEntity(
            1L, "30", "2", "Computadores", "L", "Bloco A", roomType, LocalDateTime.now(), LocalDateTime.now()
        );
        
        when(createRoom.execute(any(RoomEntity.class))).thenReturn(roomEntity);

        mockMvc.perform(post("/rooms/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId").value(1));
    }

    @Test
    void testGetAllRooms_Success() throws Exception {
        List<Map<String, Object>> roomList = List.of(Map.of("roomId", 1L, "roomCapacity", "30"));
        when(getAllRooms.execute()).thenReturn(roomList);

        mockMvc.perform(get("/rooms/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roomId").value(1));
    }

    @Test
    void testGetRoomById_Success() throws Exception {
        Map<String, Object> roomData = Map.of("roomId", 1L, "roomCapacity", "30");
        when(getRoomById.execute(1L)).thenReturn(roomData);

        mockMvc.perform(get("/rooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId").value(1));
    }

    @Test
    void testPutRoom_Success() throws Exception {
        RoomTypeEntity roomType = new RoomTypeEntity(); // Crie uma instância de RoomTypeEntity
        roomType.setRoomTypeId(1L);
        roomType.setRoomTypeDescription("Laboratório");

        RoomEntity updatedRoom = new RoomEntity(
            1L, "50", "3", "Projetores", "L", "Bloco B", roomType, LocalDateTime.now(), LocalDateTime.now()
        );
        when(putRoomById.execute(eq(1L), any(RoomEntity.class))).thenReturn(updatedRoom);

        mockMvc.perform(put("/rooms/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedRoom)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomCapacity").value("50"));
    }

    @Test
    void testDeleteRoom_Success() throws Exception {
        doNothing().when(deleteRoomById).execute(1L);

        mockMvc.perform(delete("/rooms/1"))
                .andExpect(status().isOk());
    }
}
