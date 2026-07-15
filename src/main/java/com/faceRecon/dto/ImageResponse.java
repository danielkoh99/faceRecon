package com.faceRecon.dto;

import java.util.Set;

public record ImageResponse(
        Long id,
        String url,
        Set<FaceResponse> faces) {
}