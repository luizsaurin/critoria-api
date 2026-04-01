package com.example.critoria.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.critoria.api.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>, JpaSpecificationExecutor<ReviewEntity> {
}
