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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faceRecon.dto.CreateImageRequest;
import com.faceRecon.dto.FaceResponse;
import com.faceRecon.dto.ImageResponse;
import com.faceRecon.imagerecognition.AnalyzeResponseDto;
import com.faceRecon.model.Image;
import com.faceRecon.service.ImageService;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    private static final Logger log = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @GetMapping
    public List<ImageResponse> getAllImages() {
        return imageService.getAll().stream()
                .map(image -> new ImageResponse(
                        image.getId(),
                        image.getUrl(),
                        image.getFaces().stream()
                                .map(face -> new FaceResponse(face.getId(), face.getName()))
                                .collect(Collectors.toSet())))
                .toList();
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

    @GetMapping("/test")
    public ResponseEntity<AnalyzeResponseDto> test() {
        List<Image> allImages = imageService.getAll();
        Image firsImage = allImages.getFirst();
        log.info("Found {} images", allImages.size());
        log.info("first image {}", firsImage);
        return imageService.analyzeImage(firsImage.getId());
    }
}
