package com.api.expo.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.expo.models.Artwork;
import com.api.expo.repository.ArtworkRepository;

@Service
public class ArtworkService {
     @Autowired
    private ArtworkRepository artworkRepository;

    public Artwork add_artwork(List<MultipartFile> images) throws IOException {
        // Vérifier si l'utilisateur a le rôle ROLE_CREATOR
        if (!checkUserRole()) {
            throw new IllegalStateException("Seuls les utilisateurs avec le rôle ROLE_CREATOR peuvent ajouter une œuvre.");
        }

        // Vérifier le nombre d'images
        if (images.size() < 3 || images.size() > 6) {
            throw new IllegalArgumentException("Le nombre d'images doit être compris entre 3 et 6.");
        }

        // Stocker les images dans le dossier private_images et renommer les fichiers
        for (int i = 0; i < images.size(); i++) {
            String fileName = "image_" + i + ".jpg"; // Renommer les fichiers
            String filePath = "private_images/" + fileName;
            File dest = new File(filePath);
            images.get(i).transferTo(dest);
        }

        // Créer et sauvegarder l'œuvre dans la base de données
        Artwork artwork = new Artwork();
        // Ajouter d'autres propriétés de l'œuvre au besoin
        Artwork savedArtwork = artworkRepository.save(artwork);
        return savedArtwork;
    }

    // Vérifier le rôle de l'utilisateur actuel
private boolean checkUserRole() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
        UserDetails userDetails = (UserDetails) principal;
        // Vérifier si l'utilisateur a le rôle ROLE_CREATOR
        return userDetails.getAuthorities().stream()
                .anyMatch(role -> role.equals("ROLE_CREATOR"));
    }
    return false;
}

}
