package com.projeto_integrador.projeto_integrador.modules.rooms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;

public interface RoomTypeRepository extends JpaRepository<RoomTypeEntity, Long>{
    
}
