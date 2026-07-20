package com.faceRecon.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.faceRecon.dto.ImageResponseDto;
import com.faceRecon.imageRecognition.ImageRecognitionClient;
import com.faceRecon.imageRecognition.represent.RepresentRequestDto;
import com.faceRecon.imageRecognition.represent.RepresentResponseDto;
import com.faceRecon.model.Image;
import com.faceRecon.repository.ImageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageRecognitionClient imageRecognitionClient;

    @Value("${app.upload.dir}")
    private String uploadDir;

    public ImageResponseDto uploadImage(MultipartFile file) {
        try {
            Path uploadDirectoryPath = Paths.get(uploadDir);
            Files.createDirectories(uploadDirectoryPath);
            String originalFileName = file.getOriginalFilename();
            Path target = uploadDirectoryPath.resolve(originalFileName);
            file.transferTo(target);

            Image image = new Image();
            image.setUrl(target.toString());
            image.setOriginalName(originalFileName);
            Image saved = imageRepository.save(image);

            return ImageResponseDto.builder()
                    .id(saved.getId())
                    .url(saved.getUrl())
                    .originalName(saved.getOriginalName())
                    .faces(Set.of())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }
    }

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    public Image getImage(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + id));
    }

    public ResponseEntity<String> deleteImage(Long id) {
        imageRepository.deleteById(id);
        return ResponseEntity.ok("deleted");
    }

    public Page<Image> getAllByFaceName(String faceName, Pageable page) {
        return imageRepository.findDistinctByFacesName(faceName, page);
    }

    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    public ResponseEntity<RepresentResponseDto> analyzeImage(Long imageId) {
        Image image = getImage(imageId);

        RepresentRequestDto request = new RepresentRequestDto();
        request.setImg("/data/" + image.getOriginalName());
        System.out.println(image);
        request.setDetectorBackend("opencv");
        request.setModelName("VGG-Face");
        return ResponseEntity.ok(imageRecognitionClient.analyze(request));
    }

}
