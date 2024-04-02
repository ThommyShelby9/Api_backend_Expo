package com.api.expo.models;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

@Converter(autoApply = true)
public class MatConverter implements AttributeConverter<Mat, String> {

    @Override
    public String convertToDatabaseColumn(Mat mat) {
        if (mat == null) {
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(mat);
            oos.flush();
            return Base64.getEncoder().encodeToString(bos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Mat convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        byte[] bytes = Base64.getDecoder().decode(s);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (Mat) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

