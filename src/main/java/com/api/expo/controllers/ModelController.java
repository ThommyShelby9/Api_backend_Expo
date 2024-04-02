package com.api.expo.controllers;

import java.nio.file.Files;
import java.nio.file.Path;

import org.opencv.core.Core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.expo.models.Model3D;
import com.api.expo.services.Model3DService;


@RestController
@RequestMapping("/api")
public class ModelController {
      private final Model3DService modelService;

    @Autowired
    public ModelController(Model3DService modelService) {
        this.modelService = modelService;
    }

@PostMapping("/model_maker")
public ResponseEntity<Model3D> generateModel3D(@RequestParam("imagePath") MultipartFile image) {
    try {
        // Vérifier si le fichier est vide
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Créer un fichier temporaire pour stocker l'image
        Path tempFile = Files.createTempFile("image-", null);

        // Écrire le contenu de l'image dans le fichier temporaire
        Files.write(tempFile, image.getBytes());

        // Appeler la fonction de service pour générer le modèle 3D
        Model3D model3D = modelService.generateModel3D(tempFile.toString());

        // Supprimer le fichier temporaire
        Files.deleteIfExists(tempFile);

        // Retourner le modèle 3D généré
        return ResponseEntity.ok().body(model3D);

    } catch (Exception e) {
        // Gérer les exceptions
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

}
