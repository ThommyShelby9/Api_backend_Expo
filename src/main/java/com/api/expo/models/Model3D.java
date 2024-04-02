package com.api.expo.models;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "MODEL")
public class Model3D {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] cameraMatrixSerialized; // Utilisation d'un tableau de bytes pour la sérialisation

    // Autres attributs
    @Convert(converter = MatOfDoubleConverter.class)
    private MatOfDouble distCoeffs;
    @Convert(converter = MatConverter.class)
    private List<Mat> rvecs;
    @Convert(converter = MatConverter.class)
    private List<Mat> tvecs;


}
