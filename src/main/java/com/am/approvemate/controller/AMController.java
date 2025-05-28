package com.am.approvemate.controller;

import com.am.approvemate.model.LabelData;
import com.am.approvemate.model.Library;
import com.am.approvemate.model.LicensingRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

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
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/getPrinterDetails")
    public ResponseEntity<List<LabelData>> getLabelsByItemType(@RequestParam String itemType) {
        try {
            InputStream inputStream = new ClassPathResource("static/LabelData.json").getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            List<LabelData> allLabels = mapper.readValue(inputStream, new TypeReference<List<LabelData>>() {});

            List<LabelData> filtered = allLabels.stream()
                    .filter(label -> label.getItemType().equalsIgnoreCase(itemType))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(filtered);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


}
