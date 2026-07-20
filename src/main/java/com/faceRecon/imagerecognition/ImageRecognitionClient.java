package com.faceRecon.imageRecognition;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

// import com.faceRecon.imageRecognition.represent.RepresentDtos;
import com.faceRecon.imageRecognition.represent.RepresentRequestDto;
import com.faceRecon.imageRecognition.represent.RepresentResponseDto;
import com.faceRecon.imageRecognition.verify.VerifyDtos;

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

    // public RepresentDtos.Response represent(RepresentDtos.Request request) {
    // return restClient.post()
    // .uri("/analyze")
    // .body(request)
    // .retrieve()
    // .body(RepresentDtos.Response.class);
    // }

    public RepresentResponseDto analyze(RepresentRequestDto request) {
        return restClient.post()
                .uri("/represent")
                .body(request)
                .retrieve()
                .body(RepresentResponseDto.class);
    }

    public VerifyDtos.Response verify(VerifyDtos.Request request) {
        return restClient.post()
                .uri("/verify")
                .body(request)
                .retrieve()
                .body(VerifyDtos.Response.class);
    }
}
