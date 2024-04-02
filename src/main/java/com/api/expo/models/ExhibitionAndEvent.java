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
@Table(name = "EXHIBITION_AND_EVENT")
public class ExhibitionAndEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "exhibition_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Exhibition exhibition;
}
