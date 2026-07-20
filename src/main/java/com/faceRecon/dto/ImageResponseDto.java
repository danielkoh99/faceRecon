package com.faceRecon.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ImageResponseDto {
    Long id;
    String url;
    String originalName;
    Set<FaceResponseDto> faces;
}