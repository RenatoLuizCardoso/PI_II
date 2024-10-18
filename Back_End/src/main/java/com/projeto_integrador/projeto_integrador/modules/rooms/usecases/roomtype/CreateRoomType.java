package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomTypeRepository;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;

@Service
public class CreateRoomType {
    
    @Autowired
    RoomTypeRepository roomTypeRepository;

    public RoomTypeEntity execute(RoomTypeEntity roomTypeEntity){
        return this.roomTypeRepository.save(roomTypeEntity);
    }
}
