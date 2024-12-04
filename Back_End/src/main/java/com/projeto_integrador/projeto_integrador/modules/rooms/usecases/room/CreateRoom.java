package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomRepository;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;

@Service
public class CreateRoom {
    
    @Autowired
    RoomRepository repository;

    public RoomEntity execute(RoomEntity roomEntity){
        return this.repository.save(roomEntity);
    }
}
