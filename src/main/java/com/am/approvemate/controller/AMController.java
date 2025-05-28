package com.am.approvemate.controller;

import com.am.approvemate.model.Sample;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;

@RestController
public class AMController {

    @GetMapping("/getDetails")
    public ResponseEntity<List<Sample>> sendDetails(){
        try {
            InputStream inputStream = new ClassPathResource("static/SampleData.json").getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            List<Sample> libraries = mapper.readValue(inputStream, new TypeReference<List<Sample>>() {});
            return ResponseEntity.ok(libraries);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
