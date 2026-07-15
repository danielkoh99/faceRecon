package com.faceRecon.imagerecognition;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class RepresentDtos {

        public record Request(
                        @JsonProperty("model_name") String modelName,

                        @JsonProperty("detector_backend") String detectorBackend,

                        String img) {
        }

        public record Response(
                        List<Result> results) {
        }

        public record Result(
                        List<Double> embedding,

                        @JsonProperty("face_confidence") Double faceConfidence,

                        @JsonProperty("facial_area") FacialArea facialArea) {
        }

        private record FacialArea(
                        Integer h,

                        @JsonProperty("left_eye") List<Integer> leftEye,

                        @JsonProperty("right_eye") List<Integer> rightEye,

                        Integer w,
                        Integer x,
                        Integer y) {
        }
}
