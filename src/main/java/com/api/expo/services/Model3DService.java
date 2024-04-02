package com.api.expo.services;
import org.opencv.core.CvType;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point3;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint3f;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;

import com.api.expo.models.Model3D;

import java.util.ArrayList;
import java.util.List;

@Service
public class Model3DService {

    public Model3D generateModel3D(String imagePath) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = Imgcodecs.imread(imagePath);
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        List<Point> corners = new ArrayList<>();
        MatOfPoint3f objectPoints = new MatOfPoint3f();
        MatOfPoint2f imagePoints = new MatOfPoint2f();

        // Trouver les coins de l'image
        Imgproc.goodFeaturesToTrack(gray, (MatOfPoint) corners, 25, 0.01, 10);

        // Convertir les points en coordonnées 3D
        for (Point corner : corners) {
            objectPoints.push_back(new MatOfPoint3f(new Point3(corner.x, corner.y, 0)));
        }

        // Calibrer la caméra
        Mat cameraMatrix = new Mat(3, 3, CvType.CV_64FC1);
        MatOfDouble distCoeffs = new MatOfDouble();
        List<Mat> rvecs = new ArrayList<>();
        List<Mat> tvecs = new ArrayList<>();
        
        // Initialiser les listes rvecs et tvecs avec des Mat vides
        for (int i = 0; i < corners.size(); i++) {
            rvecs.add(new Mat());
            tvecs.add(new Mat());
        }

        // Résoudre les poses de la caméra
        Calib3d.solvePnP(objectPoints, imagePoints, cameraMatrix, distCoeffs, rvecs.get(0), tvecs.get(0));

        // Autres étapes pour générer le modèle 3D...

        return new Model3D(); // Retourne le modèle 3D généré
    }
}
