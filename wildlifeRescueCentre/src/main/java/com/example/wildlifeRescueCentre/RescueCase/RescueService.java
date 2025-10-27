package com.example.wildlifeRescueCentre.RescueCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RescueService {
    @Autowired
    private RescueRepository rescueRepository;

    // get all
    public List<RescueRecord> getAll(){
        return (List<RescueRecord>) rescueRepository.findAll();
    }

    public void createRescue(RescueRecord rescueRecord){
        rescueRepository.save(rescueRecord);
    }

    public RescueRecord getById(int id){
        return rescueRepository.findById(id).orElse(null);
    }

    public void deleteById(int id){
        rescueRepository.deleteById(id);
    }

    public void updateById(int id,RescueRecord rescueRecord){
        rescueRepository.save(rescueRecord);
    }

    public String getCount() {
        long count = rescueRepository.count();
        return "Total number of rescue records: " + count;
    }
}
