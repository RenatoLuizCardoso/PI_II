package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomRepository;

@ExtendWith(MockitoExtension.class)
class CreateRoomTest {

    @InjectMocks
    private CreateRoom createRoom;

    @Mock
    private RoomRepository roomRepository;

    @Test
    void testExecuteRoomCreationSuccessful() {
        // Criar um objeto RoomTypeEntity
        RoomTypeEntity roomTypeEntity = new RoomTypeEntity();
        roomTypeEntity.setRoomTypeId(1L); // Supondo que o tipo de sala com ID 1 exista

        // Criar um objeto RoomEntity
        RoomEntity roomEntity = RoomEntity.builder()
            .roomCapacity("30")
            .roomNumber("02")
            .roomFloor("2")
            .roomResources("Computadores, Televisão, Ventiladores, Quadro")
            .roomAvailability("Livre")
            .roomType(roomTypeEntity)
            .build();

        // Simular a salvamento da sala
        when(roomRepository.save(any(RoomEntity.class))).thenReturn(roomEntity);

        // Executar o método
        RoomEntity createdRoom = createRoom.execute(roomEntity);

        // Verificar o resultado
        assertNotNull(createdRoom);
        assertEquals(roomEntity.getRoomCapacity(), createdRoom.getRoomCapacity());
        assertEquals(roomEntity.getRoomNumber(), createdRoom.getRoomNumber());
        assertEquals(roomEntity.getRoomFloor(), createdRoom.getRoomFloor());
        assertEquals(roomEntity.getRoomResources(), createdRoom.getRoomResources());
        assertEquals(roomEntity.getRoomAvailability(), createdRoom.getRoomAvailability());
        assertEquals(roomEntity.getRoomType(), createdRoom.getRoomType());

        // Verificar se o método save foi chamado
        verify(roomRepository, times(1)).save(roomEntity);
    }

    @Test
    void testExecuteRoomCreationWithNullRoomType() {
        // Criar um objeto RoomEntity com roomType nulo
        RoomEntity roomEntity = RoomEntity.builder()
            .roomCapacity("30")
            .roomNumber("02")
            .roomFloor("2")
            .roomResources("Computadores, Televisão, Ventiladores, Quadro")
            .roomAvailability("Livre")
            .roomType(null)  // roomType é nulo
            .build();

        // Executar o método e verificar se o comportamento correto ocorre (não deveria salvar)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            createRoom.execute(roomEntity);
        });

        // Verificar a mensagem da exceção
        assertEquals("O tipo de sala é obrigatório", exception.getMessage());

        verify(roomRepository, never()).save(any(RoomEntity.class));
    }
}
