package com.faceRecon.service;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.faceRecon.imagerecognition.AnalyzeRequestDto;
import com.faceRecon.imagerecognition.AnalyzeResponseDto;
import com.faceRecon.imagerecognition.ImageRecognitionClient;
import com.faceRecon.model.Image;
import com.faceRecon.repository.ImageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    private ImageRecognitionClient imageRecognitionClient;

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

    public ResponseEntity<AnalyzeResponseDto> analyzeImage(Long imageId) {
        Image image = getImage(imageId);

        String base64 = Base64.getEncoder().encodeToString(image.getData());
        String dataUrl = "data:image/jpeg;base64," + base64;

        AnalyzeRequestDto request = new AnalyzeRequestDto();
        request.setImg(dataUrl);
        request.setDetectorBackend("");
        request.setModelName("opencv");
        return ResponseEntity.ok(imageRecognitionClient.analyze(request));
    }

    // Upload image and alanylze

}
