package com.api.expo.models;

import org.opencv.core.MatOfDouble;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class MatOfDoubleConverter implements AttributeConverter<MatOfDouble, String> {

    @Override
    public String convertToDatabaseColumn(MatOfDouble matOfDouble) {
        if (matOfDouble == null) {
            return null;
        }
        double[] data = matOfDouble.toArray();
        return Arrays.toString(data);
    }

    @Override
    public MatOfDouble convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        String[] parts = s.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
        double[] data = new double[parts.length];
        for (int i = 0; i < parts.length; i++) {
            data[i] = Double.parseDouble(parts[i].trim());
        }
        return new MatOfDouble(data);
    }
}

