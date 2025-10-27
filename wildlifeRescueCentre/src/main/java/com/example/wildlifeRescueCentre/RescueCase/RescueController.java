package com.example.wildlifeRescueCentre.RescueCase;

import com.example.wildlifeRescueCentre.Animal.AnimalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rescue")
public class RescueController {
    @Autowired
    private RescueService rescueService;

    @GetMapping("/all")
    public List<RescueRecord> getAll(){
        return (List<RescueRecord>) rescueService.getAll();
    }

    @GetMapping("/{id}")
    public RescueRecord getById(@PathVariable int id){
        return rescueService.getById(id);
    }

    @PostMapping
    public String addRescue(@RequestBody RescueRecord rescueRecord){
        rescueService.createRescue(rescueRecord);
        return "New rescue added";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable int id){
        rescueService.deleteById(id);
        return "Rescue with ID " + id + " deleted.";
    }

    @PutMapping("/{id}")
    public String updateById(@PathVariable int id,@RequestBody RescueRecord rescueRecord){
        rescueService.updateById(id,rescueRecord);
        return "Rescue with ID " + id + " updated.";
    }

    @GetMapping("/count")
    public String getCount() {
        return  rescueService.getCount();
    }
}
