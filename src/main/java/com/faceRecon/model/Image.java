package com.faceRecon.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "images")
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = true, length = 500)
  private String originalName;

  @Column(nullable = false, length = 500)
  private String url;

  @Column(nullable = true, name = "data")
  private byte[] data;

  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(name = "image_faces", joinColumns = @JoinColumn(name = "image_id"), inverseJoinColumns = @JoinColumn(name = "face_id"))
  private Set<Face> faces = new HashSet<>();

  public void addFace(Face face) {
    faces.add(face);
    face.getImages().add(this);
  }

  public void removeFace(Face face) {
    faces.remove(face);
    face.getImages().remove(this);
  }

}
