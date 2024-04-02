package com.api.expo.repository;

import org.springframework.data.repository.CrudRepository;

import com.api.expo.models.Artwork;

public interface ArtworkRepository extends CrudRepository<Artwork, Long> {
    
}
