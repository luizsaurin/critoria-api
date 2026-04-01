package com.example.critoria.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.critoria.api.entity.TitleEntity;

public interface TitleRepository extends JpaRepository<TitleEntity, Long>, JpaSpecificationExecutor<TitleEntity> {
}
