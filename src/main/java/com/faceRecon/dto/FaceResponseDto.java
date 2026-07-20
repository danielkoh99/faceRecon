package com.faceRecon.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FaceResponseDto {
        Long id;
        String name;
}