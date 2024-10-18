package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomRepository;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.RoomValidation;

@Service
public class CreateRoom {
    
    @Autowired
    RoomRepository repository;

    @Autowired
    RoomValidation validation;

    public RoomEntity execute(RoomEntity roomEntity){
        Long roomTypeId = roomEntity.getRoomType();
        validation.validateRoomTypeExist(roomTypeId);

        return this.repository.save(roomEntity);
    }
}
