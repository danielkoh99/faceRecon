package com.faceRecon.config;

import com.faceRecon.model.Face;
import com.faceRecon.model.Image;
import com.faceRecon.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ImageRepository imageRepository;

    @Override
    public void run(String... args) {
        if (imageRepository.count() > 0) {
            return;
        }

        Image image1 = new Image();
        image1.setUrl("https://example.com/images/daniel-1.jpg");

        Face face1 = new Face();
        face1.setName("daniel");

        Face face2 = new Face();
        face2.setName("anna");

        image1.addFace(face1);
        image1.addFace(face2);

        Image image2 = new Image();
        image2.setUrl("https://example.com/images/anna-1.jpg");

        Face face3 = new Face();
        face3.setName("anna");

        image2.addFace(face3);

        Image image3 = new Image();
        image3.setUrl("https://example.com/images/group-1.jpg");

        Face face4 = new Face();
        face4.setName("daniel");

        Face face5 = new Face();
        face5.setName("peter");

        image3.addFace(face4);
        image3.addFace(face5);

        imageRepository.save(image1);
        imageRepository.save(image2);
        imageRepository.save(image3);
    }
}
