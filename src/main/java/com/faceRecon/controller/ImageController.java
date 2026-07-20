package com.faceRecon.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.faceRecon.dto.CreateImageRequest;
import com.faceRecon.dto.FaceResponseDto;
import com.faceRecon.dto.ImageResponseDto;
import com.faceRecon.imageRecognition.represent.RepresentResponseDto;
import com.faceRecon.model.Image;
import com.faceRecon.service.ImageService;

@RestController
@RequestMapping("/image")
public class ImageController {
    private static final Logger log = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @GetMapping
    public List<ImageResponseDto> getAllImages() {
        return imageService.getAll().stream()
                .map(image -> ImageResponseDto.builder()
                        .id(image.getId())
                        .url(image.getUrl())
                        .originalName(image.getOriginalName())
                        .faces(
                                image.getFaces().stream()
                                        .map(face -> FaceResponseDto.builder()
                                                .id(face.getId())
                                                .name(face.getName())
                                                .build())
                                        .collect(Collectors.toSet()))
                        .build())
                .toList();
    }

    @DeleteMapping("/{id}")
    public String deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return "Image with ID " + id + " deleted successfully!";
    }

    @GetMapping("/face")
    public Page<Image> getAllByFaceName(
            @RequestParam String faceName,
            @PageableDefault(size = 10) Pageable pageable) {
        return imageService.getAllByFaceName(faceName, pageable);
    }

    @PostMapping
    public ResponseEntity<Image> addImages(@RequestBody CreateImageRequest createImageRequest) {
        Image image = new Image();
        image.setUrl(createImageRequest.url());

        Image savedImage = imageService.saveImage(image);
        return ResponseEntity.ok(savedImage);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponseDto> uploadImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(imageService.uploadImage(file));
    }

    @GetMapping("/test")
    public ResponseEntity<RepresentResponseDto> test() {
        List<Image> allImages = imageService.getAll();
        Image firsImage = allImages.get(5);
        log.info("Found {} images", allImages.size());
        log.info("first image {}", firsImage.getUrl());
        return imageService.analyzeImage(firsImage.getId());
    }
}
