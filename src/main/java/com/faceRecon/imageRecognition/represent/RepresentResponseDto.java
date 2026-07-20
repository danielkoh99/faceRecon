package com.faceRecon.imageRecognition.represent;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepresentResponseDto {

    private List<RepresentResultDto> results;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RepresentResultDto {
        private List<Double> embedding;
        private Double face_confidence;
        private FacialAreaDto facial_area;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FacialAreaDto {
        private Integer h;
        private List<Integer> left_eye;
        private List<Integer> right_eye;
        private Integer w;
        private Integer x;
        private Integer y;
    }
}
