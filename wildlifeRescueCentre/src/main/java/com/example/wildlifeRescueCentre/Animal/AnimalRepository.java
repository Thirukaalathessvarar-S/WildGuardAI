package com.example.wildlifeRescueCentre.Animal;

import org.springframework.data.repository.CrudRepository;

public interface AnimalRepository extends CrudRepository<AnimalRecord, Long> {
    // This interface will automatically provide CRUD operations for AnimalRecord entities
    // No additional methods are needed unless specific queries are required
}
