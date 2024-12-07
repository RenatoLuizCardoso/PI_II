package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype;

import java.util.Collections;
import java.util.List;

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
class GetAllRoomTypeTest {

    @InjectMocks
    private GetAllRoomTypes getAllRoomTypes;

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @Test
    void testExecuteWhenRoomTypesExist() {
        RoomTypeEntity roomType1 = new RoomTypeEntity();
        roomType1.setRoomTypeId(1L);
        roomType1.setRoomTypeDescription("Sala de Aula");

        RoomTypeEntity roomType2 = new RoomTypeEntity();
        roomType2.setRoomTypeId(2L);
        roomType2.setRoomTypeDescription("Laboratório");

        List<RoomTypeEntity> roomTypes = List.of(roomType1, roomType2);

        when(roomTypeRepository.findAll()).thenReturn(roomTypes);

        // Executar o método
        List<RoomTypeEntity> result = getAllRoomTypes.execute();

        // Verificar se o retorno tem dois tipos de sala
        assertEquals(2, result.size());
    }

    @Test
    void testExecuteWhenNoRoomTypesExist() {
        when(roomTypeRepository.findAll()).thenReturn(Collections.emptyList());

        // Executar o método e verificar se a exceção é lançada
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            getAllRoomTypes.execute();
        });

        // Verificar a mensagem da exceção
        assertEquals("RoomType not Registered", exception.getMessage());
    }
}
