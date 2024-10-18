package com.projeto_integrador.projeto_integrador.modules.courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;



public interface CourseRepository extends JpaRepository<CourseEntity, Long>{
}