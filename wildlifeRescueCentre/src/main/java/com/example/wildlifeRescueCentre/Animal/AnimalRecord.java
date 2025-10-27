package com.example.wildlifeRescueCentre.Animal;

import com.example.wildlifeRescueCentre.RescueCase.RescueRecord;
import jakarta.persistence.*;

@Entity
@Table(name = "animal_record")
public class AnimalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long animalId;

    @Column(nullable = false)
    private String species;

    @Column
    private String healthStatus;

    @Column(nullable = false)
    private String conditionStatus;

    @Column
    private String classes;

    @Column
    private String imageUrl;

    @Column(name = "rescue_id")
    private int rescueId;

    // Getters and Setters
    public long getAnimalId() {
        return animalId;
    }

    public void setAnimalId(long animalId) {
        this.animalId = animalId;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getConditionStatus() {
        return conditionStatus;
    }

    public void setConditionStatus(String conditionStatus) {
        this.conditionStatus = conditionStatus;
    }

    public int getRescueId() {
        return rescueId;
    }

    public void setRescueId(int rescueId) {
        this.rescueId = rescueId;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
