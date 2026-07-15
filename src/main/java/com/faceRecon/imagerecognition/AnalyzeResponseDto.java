package com.faceRecon.imagerecognition;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyzeResponseDto {

    private List<AnalyzeResultDto> results;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnalyzeResultDto {
        private List<Double> embedding;
        private Double faceConfidence;
        private FacialAreaDto facialArea;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FacialAreaDto {
        private Integer h;
        private List<Integer> leftEye;
        private List<Integer> rightEye;
        private Integer w;
        private Integer x;
        private Integer y;
    }
}
