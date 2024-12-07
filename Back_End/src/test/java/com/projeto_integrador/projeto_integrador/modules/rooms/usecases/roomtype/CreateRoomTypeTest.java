package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomTypeRepository;

@ExtendWith(MockitoExtension.class)
class CreateRoomTypeTest {

    @InjectMocks
    private CreateRoomType createRoomType;

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @Test
    void testCreateRoomTypeSuccess() {
        // Criar uma instância de RoomTypeEntity
        RoomTypeEntity roomTypeEntity = new RoomTypeEntity();
        roomTypeEntity.setRoomTypeId(1L);
        roomTypeEntity.setRoomTypeDescription("Sala de Aula");

        // Simular o comportamento do repositório
        when(roomTypeRepository.save(any(RoomTypeEntity.class))).thenReturn(roomTypeEntity);

        // Chamar o método que estamos testando
        RoomTypeEntity createdRoomType = createRoomType.execute(roomTypeEntity);

        // Verificar se o retorno não é nulo e se os dados estão corretos
        assertNotNull(createdRoomType);
        assertEquals(roomTypeEntity.getRoomTypeId(), createdRoomType.getRoomTypeId());
        assertEquals(roomTypeEntity.getRoomTypeDescription(), createdRoomType.getRoomTypeDescription());

        // Verificar se o método de salvar foi chamado uma vez
        verify(roomTypeRepository).save(roomTypeEntity);
    }

    @Test
    void testCreateRoomTypeFailure() {
        // Criar uma instância de RoomTypeEntity
        RoomTypeEntity roomTypeEntity = new RoomTypeEntity();
        roomTypeEntity.setRoomTypeId(1L);
        roomTypeEntity.setRoomTypeDescription("Sala de Aula");

        // Simular a falha no repositório (por exemplo, se já existir um tipo de sala com o mesmo ID)
        when(roomTypeRepository.save(any(RoomTypeEntity.class))).thenThrow(new RuntimeException("Tipo de Sala já existe"));

        // Executar o método e verificar se a exceção ocorre
        try {
            createRoomType.execute(roomTypeEntity);
        } catch (RuntimeException e) {
            // Verificar se a mensagem de erro está correta
            assertEquals("Tipo de Sala já existe", e.getMessage());
        }

        // Verificar se o método de salvar foi chamado uma vez
        verify(roomTypeRepository).save(roomTypeEntity);
    }
}
