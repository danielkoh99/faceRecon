package com.faceRecon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.faceRecon.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Page<Image> findDistinctByFacesName(String faceName, Pageable pageable);
}
