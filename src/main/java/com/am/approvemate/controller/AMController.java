package com.am.approvemate.controller;

import com.am.approvemate.model.Library;
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
    public ResponseEntity<List<Library>> sendDetails(){
        try {
            InputStream inputStream = new ClassPathResource("static/java_libraries_with_versions.json").getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            List<Library> libraries = mapper.readValue(inputStream, new TypeReference<List<Library>>() {});
            return ResponseEntity.ok(libraries);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
