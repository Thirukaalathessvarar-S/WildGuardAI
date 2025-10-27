package com.example.wildlifeRescueCentre.Animal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @GetMapping("/all")
    public List<AnimalRecord> getAllAnimals(){
        return animalService.getAllAnimals();
    }

    @GetMapping("/{id}")
    public AnimalRecord getAnimalById(@PathVariable long id){
        return animalService.getAnimalById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addAnimal(@RequestBody AnimalRecord animalRecord){
        animalService.addAnimal(animalRecord);
        return "Animal added successfully with ID: " + animalRecord.getAnimalId();
    }

    @PutMapping("/{id}")
    public AnimalRecord updateAnimal(@PathVariable long id, @RequestBody AnimalRecord animalRecord){
        return animalService.updateAnimal(id, animalRecord);
    }

    @DeleteMapping("/{id}")
    public void deleteAnimal(@PathVariable long id){
        animalService.deleteAnimal(id);
    }
}
