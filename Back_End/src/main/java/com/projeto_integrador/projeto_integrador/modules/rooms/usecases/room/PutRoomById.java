package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PutRoomById {

    @Autowired
    RoomRepository roomRepository;

    public RoomEntity execute(Long id, RoomEntity roomEntity) {
        RoomEntity updateRoom = this.roomRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Room not found")
        );

        updateRoom.setRoomCapacity(roomEntity.getRoomCapacity());
        updateRoom.setRoomFloor(roomEntity.getRoomFloor());
        updateRoom.setRoomResources(roomEntity.getRoomResources());
        updateRoom.setRoomAvailability(roomEntity.getRoomAvailability());
        updateRoom.setRoomType(roomEntity.getRoomType());
        updateRoom.setRoomNumber(roomEntity.getRoomNumber());


        return this.roomRepository.save(updateRoom);
    }
}
