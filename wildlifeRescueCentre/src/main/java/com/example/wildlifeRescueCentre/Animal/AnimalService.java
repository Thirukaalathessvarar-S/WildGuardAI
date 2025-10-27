package com.example.wildlifeRescueCentre.Animal;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import java.util.*;

import com.example.wildlifeRescueCentre.RescueCase.RescueRecord;
import com.example.wildlifeRescueCentre.RescueCase.RescueRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AnimalService {
    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private RescueRepository rescueRepository;

    // get all
    public List<AnimalRecord> getAllAnimals() {
        return (List<AnimalRecord>) animalRepository.findAll();
    }

    // get by id
    public AnimalRecord getAnimalById(long id) {
        return animalRepository.findById(id).orElse(null);
    }

    // create
    public void addAnimal(AnimalRecord animalRecord) {
        // assign classes using ninja API
        String animalSpecies = animalRecord.getSpecies();
        assignClass(animalSpecies,animalRecord);
        assignHealthStatus(animalRecord);
        assignCase(animalRecord);
        animalRepository.save(animalRecord);
    }

    // update
    public AnimalRecord updateAnimal(long id, AnimalRecord animalRecord) {
        if (animalRepository.existsById(id)) {
            animalRecord.setAnimalId(id);
            return animalRepository.save(animalRecord);
        } else {
            return null;
        }
    }

    // delete
    public void deleteAnimal(long id) {
        if (animalRepository.existsById(id)) {
            animalRepository.deleteById(id);
        } else {
            throw new RuntimeException("Animal with ID " + id + " does not exist.");
        }
    }

    public void assignClass(String animalSpecies,AnimalRecord animalRecord){
        try{
            String urlString = "https://api.api-ninjas.com/v1/animals?name="+animalSpecies;
            String apiKey = "VKYUo1RjZoZ3rzbStGUiaA==s1IkB8Da89BWRbvP";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Api-Key", apiKey);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            System.out.println("Response from API: " + response.toString());

            // Parse JSON response to extract class
            JsonArray jsonArray = JsonParser.parseString(response.toString()).getAsJsonArray();
            String animalClass = null;

            if (jsonArray.size() > 0) {
                JsonObject animal = jsonArray.get(0).getAsJsonObject();
                JsonObject taxonomy = animal.getAsJsonObject("taxonomy");
                animalClass = taxonomy.get("class").getAsString();
            }

            // Update the animal record with the class
            animalRecord.setClasses(animalClass);
        }
        catch(Exception e){
            System.out.println("Error fetching animal class: " + e.getMessage());
            // Optionally, you can set a default class or handle the error as needed
            animalRecord.setClasses("Unknown");
        }
    }

    public void assignHealthStatus(AnimalRecord animalRecord) {
        String flaskUrl = "http://localhost:5000/classify";
        String defaultHealthStatus = "other"; // fallback
        try {
            URL url = new URL(flaskUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            Map<String, String> jsonMap = new HashMap<>();
            jsonMap.put("image_url", animalRecord.getImageUrl());
            Gson gson = new Gson();
            String jsonInputString = gson.toJson(jsonMap);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                JsonArray jsonArray = JsonParser.parseString(response.toString()).getAsJsonArray();
                if (jsonArray.size() == 0) {
                    animalRecord.setHealthStatus(defaultHealthStatus);
                    animalRecord.setConditionStatus("Critical");
                } else {
                    JsonObject best = jsonArray.get(0).getAsJsonObject();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonObject obj = jsonArray.get(i).getAsJsonObject();
                        if (obj.get("score").getAsDouble() > best.get("score").getAsDouble()) {
                            best = obj;
                        }
                    }
                    String topLabel = best.get("label").getAsString();
                    if (topLabel == null || topLabel.isBlank()) {
                        topLabel = defaultHealthStatus;
                    }
                    animalRecord.setHealthStatus(topLabel);
                    if (topLabel.equalsIgnoreCase("healthy")) {
                        animalRecord.setConditionStatus("Good");
                    } else {
                        animalRecord.setConditionStatus("Critical");
                    }
                }
            }
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            // fallback if something goes wrong
            animalRecord.setHealthStatus(defaultHealthStatus);
            animalRecord.setConditionStatus("Critical");
        }
    }


    public void assignCase(AnimalRecord animalRecord){
        Map<String,Integer> caseMap = new HashMap<>();
        caseMap.put("injured",101);
        caseMap.put("disease",202);
        caseMap.put(("illegal captivity"),303);
        caseMap.put("healthy",404);
        caseMap.put("other",505);
        caseMap.put("human wildlife conflict",606);
        System.out.println("HealthStatus: " + animalRecord.getHealthStatus());

        Integer caseId = caseMap.getOrDefault(animalRecord.getHealthStatus(), 505);
        System.out.println("HealthStatus: " + animalRecord.getHealthStatus() + ", caseId: " + caseId);

        RescueRecord rescueRecord = rescueRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Rescue case not found for id: " + caseId));
        animalRecord.setRescueId(rescueRecord.getRescueId());

        assert rescueRecord != null;
        rescueRecord.updateAnimalList(animalRecord);
    }
}
