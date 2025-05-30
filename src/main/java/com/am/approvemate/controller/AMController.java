package com.am.approvemate.controller;

import com.am.approvemate.model.LabelData;
import com.am.approvemate.model.Library;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class AMController {

    @GetMapping("/getDetails")
    public ResponseEntity<List<Library>> sendDetails() {
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

    @GetMapping("/generate")
    public ResponseEntity<String> generateLabel(@RequestParam String LabelType, @RequestParam String ProductLabel, @RequestParam String PrinterName , @RequestParam String ZplFormat, @RequestParam String WorkArea ) throws IOException {
        // ZPL template
//        String zplTemplate = "^XA\n" +
//                "^FO50,50^A0N,50,50^FD${text}^FS\n" +
//                "^FO50,150^A0N,30,30^FD${date}^FS\n" +
//                "^XZ";

        String zplTemplate = "^XA^PRC^XZ^XA^LH0,0^LL1399^FO77,250^A0N,103,67^CI13^FR^FD${LabelType}^FS^FO77,400^A0N,103,83^CI13^FR^FD${PrinterName}^FS^FO300,400^A0N,103,67^CI13^FR^FD${ZplFormat}^FS^BY3,3.0^FO80,520^FR^B3N,N,116,N,N^FR^FD_PART_NO_^FS^FO77,680^A0N,65,65^CI13^FR^FS^FO77,910^A0N,65,65^CI13^FR^FD${WorkArea}^FS^FO290,910^A0N,65,65^CI13^FR^FD_DOM_^FS^FO77,1000^A0N,65,65^CI13^FR^FD${ProductLabel}^FS^BY4,3.0^FO80,1090^FR^B3N,N,100,N,N^FR^FD_CTY_CODE_^FS^MCN^XZ^XA^MCY^XZ";

        String zplData = zplTemplate.replace("${LabelType}", LabelType).replace("${ProductLabel}", ProductLabel).replace("${PrinterName}", PrinterName).replace("${ZplFormat}", ZplFormat).replace("${WorkArea}", WorkArea);

        // Call Labelary API
        byte[] labelImage = callLabelary(zplData);

        // Save to temp file
        String filename = UUID.randomUUID() + ".png";
        Path path = Paths.get(System.getProperty("java.io.tmpdir"), filename);
        Files.write(path, labelImage);

        // Return download link
        String link = "https://approvemate-dbe3.onrender.com/download/" + filename;
        return ResponseEntity.ok(link);
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<byte[]> downloadLabel(@PathVariable String filename) throws IOException {
        Path filePath = Paths.get(System.getProperty("java.io.tmpdir"), filename);
        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        byte[] fileBytes = Files.readAllBytes(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentDisposition(ContentDisposition.inline().filename(filename).build());

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }

    private byte[] callLabelary(String zpl) throws IOException {
        URL url = new URL("http://api.labelary.com/v1/printers/8dpmm/labels/4x6/0/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "image/png");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(zpl.getBytes(StandardCharsets.UTF_8));
        }

        try (InputStream is = conn.getInputStream()) {
            return is.readAllBytes();
        }
    }
}
