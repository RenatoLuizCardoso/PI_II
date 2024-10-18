package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomTypeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PutRoomTypeById {

    @Autowired
    RoomTypeRepository roomRepository;
    
    public RoomTypeEntity execute(Long id, RoomTypeEntity roomTypeEntity) {
        RoomTypeEntity updateRoomType = this.roomRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("RoomType not found")
        );
        updateRoomType.setRoomTypeDescription(roomTypeEntity.getRoomTypeDescription());


        return this.roomRepository.save(updateRoomType);
    }
}
