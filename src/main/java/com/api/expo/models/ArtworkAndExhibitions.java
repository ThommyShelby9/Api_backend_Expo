package com.api.expo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ARTWORK_AND_EXHIBITIONS")
public class ArtworkAndExhibitions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "artwork_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Artwork artwork;

    @ManyToOne
    @JoinColumn(name = "exhibition_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Exhibition exhibition;
}

