package com.projeto_integrador.projeto_integrador.modules.rooms.controller;
 
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype.CreateRoomType;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype.DeleteRoomTypeById;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype.GetAllRoomTypes;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype.GetRoomTypeById;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype.PutRoomTypeById;
 
class RoomTypeControllerTest {
 
    private MockMvc mockMvc;
 
    @Mock
    private CreateRoomType createRoomType;
 
    @Mock
    private GetAllRoomTypes getAllRoomTypes;
 
    @Mock
    private GetRoomTypeById getRoomTypeById;
 
    @Mock
    private PutRoomTypeById putRoomTypeById;
 
    @Mock
    private DeleteRoomTypeById deleteRoomTypeById;
 
    @InjectMocks
    private RoomTypeController roomTypeController;
 
    private ObjectMapper objectMapper;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(roomTypeController).build();
        this.objectMapper = new ObjectMapper();
    }
 
    @Test
    void testCreateRoomType() throws Exception {
        RoomTypeEntity roomTypeEntity = new RoomTypeEntity();
        roomTypeEntity.setRoomTypeDescription("Laboratório de Informática");
 
        when(createRoomType.execute(any(RoomTypeEntity.class))).thenReturn(roomTypeEntity);
 
        mockMvc.perform(post("/roomType/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomTypeEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomTypeDescription").value("Laboratório de Informática"));
    }
 
    @Test
    void testGetAllRoomTypes() throws Exception {
        RoomTypeEntity roomType1 = new RoomTypeEntity();
        roomType1.setRoomTypeDescription("Laboratório de Física");
 
        RoomTypeEntity roomType2 = new RoomTypeEntity();
        roomType2.setRoomTypeDescription("Laboratório de Química");
 
        when(getAllRoomTypes.execute()).thenReturn(List.of(roomType1, roomType2));
 
        mockMvc.perform(get("/roomType/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roomTypeDescription").value("Laboratório de Física"))
                .andExpect(jsonPath("$[1].roomTypeDescription").value("Laboratório de Química"));
    }
 
    @Test
    void testGetRoomTypeById() throws Exception {
        RoomTypeEntity roomTypeEntity = new RoomTypeEntity();
        roomTypeEntity.setRoomTypeDescription("Auditório");
 
        when(getRoomTypeById.execute(1L)).thenReturn(roomTypeEntity);
 
        mockMvc.perform(get("/roomType/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomTypeDescription").value("Auditório"));
    }
 
    @Test
    void testUpdateRoomType() throws Exception {
        RoomTypeEntity roomTypeEntity = new RoomTypeEntity();
        roomTypeEntity.setRoomTypeDescription("Auditório Atualizado");
 
        when(putRoomTypeById.execute(eq(1L), any(RoomTypeEntity.class))).thenReturn(roomTypeEntity);
 
        mockMvc.perform(put("/roomType/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomTypeEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomTypeDescription").value("Auditório Atualizado"));
    }
 
    @Test
    void testDeleteRoomType() throws Exception {
        doNothing().when(deleteRoomTypeById).execute(1L);
 
        mockMvc.perform(delete("/roomType/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
 
    @Test
    void testCreateRoomTypeValidationError() throws Exception {
        RoomTypeEntity invalidRoomTypeEntity = new RoomTypeEntity(); // Missing required field
 
        mockMvc.perform(post("/roomType/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRoomTypeEntity)))
                .andExpect(status().isBadRequest());
 
        verify(createRoomType, times(0)).execute(any(RoomTypeEntity.class));
    }
}