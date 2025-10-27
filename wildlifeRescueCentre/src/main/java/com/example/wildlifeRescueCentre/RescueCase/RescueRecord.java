//package com.example.wildlifeRescueCentre.RescueCase;
//
//import com.example.wildlifeRescueCentre.Animal.AnimalRecord;
//import jakarta.persistence.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "rescue_record")
//public class RescueRecord {
//    @Id
//    private int rescueId;
//
//    @Column
//    private String issueType;
//
//    private  int count;
//
//    @Column(columnDefinition = "JSON")
//    private String volunteers;
//
//    @OneToMany(mappedBy = "rescueRecord")
//    private List<AnimalRecord> animalList = new ArrayList<>();
//
//
//
//    public String getIssueType() {
//        return issueType;
//    }
//
//    public void setIssueType(String issueType) {
//        this.issueType = issueType;
//    }
//
//    public int getCount() {
//        return count;
//    }
//
//    public void setCount(int count) {
//        this.count = count;
//    }
//
//    public int getRescueId() {
//        return rescueId;
//    }
//
//    public void setRescueId(int rescueId) {
//        this.rescueId = rescueId;
//    }
//
//    public String getVolunteer_assigned() {
//        return volunteers;
//    }
//
//    public void setVolunteer_assigned(String volunteer_assigned) {
//        this.volunteers = volunteer_assigned;
//    }
//
//    public List<AnimalRecord> getAnimalList() {
//        return animalList;
//    }
//
//    public void setAnimalList(List<AnimalRecord> animalList) {
//        this.animalList = animalList;
//    }
//
//    public void updateAnimalList(AnimalRecord animalRecord){
//        if (animalList == null) animalList = new ArrayList<>();
//        animalList.add(animalRecord);
//        this.count = animalList.size(); // optional: update count
//    }
//
//}
package com.example.wildlifeRescueCentre.RescueCase;

import com.example.wildlifeRescueCentre.Animal.AnimalRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rescue_record")
public class RescueRecord {
    @Id
    private int rescueId;

    @Column
    private String issueType;

    @Column(columnDefinition = "JSON")
    private String volunteers;

    @OneToMany()
    @JoinColumn(name = "rescue_id", referencedColumnName = "rescueId", insertable = false, updatable = false)
    private List<AnimalRecord> animalList = new ArrayList<>();

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public int getRescueId() {
        return rescueId;
    }

    public void setRescueId(int rescueId) {
        this.rescueId = rescueId;
    }

    public String getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(String volunteers) {
        this.volunteers = volunteers;
    }

    public List<AnimalRecord> getAnimalList() {
        return animalList;
    }

    public void setAnimalList(List<AnimalRecord> animalList) {
        this.animalList = animalList;
    }


    public void updateAnimalList(AnimalRecord animalRecord) {
        if (animalList == null) animalList = new ArrayList<>();
        animalList.add(animalRecord);
    }
}