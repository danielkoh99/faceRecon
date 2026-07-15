package com.faceRecon.imagerecognition;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ImageRecognitionClient {

    private final RestClient restClient;

    public ImageRecognitionClient(
            RestClient.Builder builder,
            @Value("${imagerecognition.base-url}") String imageRecognitionApi) {
        this.restClient = builder
                .baseUrl(imageRecognitionApi)
                .build();
    }

    public RepresentDtos.Response represent(RepresentDtos.Request request) {
        return restClient.post()
                .uri("/represent")
                .body(request)
                .retrieve()
                .body(RepresentDtos.Response.class);
    }

    public AnalyzeResponseDto analyze(AnalyzeRequestDto request) {
        return restClient.post()
                .uri("/analyze")
                .body(request)
                .retrieve()
                .body(AnalyzeResponseDto.class);
    }

    public VerifyDtos.Response verify(VerifyDtos.Request request) {
        return restClient.post()
                .uri("/verify")
                .body(request)
                .retrieve()
                .body(VerifyDtos.Response.class);
    }
}
