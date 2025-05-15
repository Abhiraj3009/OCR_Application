package com.Abhiraj.OCR_Project.controller;

import com.Abhiraj.OCR_Project.service.OCRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class OCRController {

    @Autowired
    private OCRService ocrService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("image") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
            return "index";
        }

        try {
            File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
            file.transferTo(tempFile);

            String extractedText = ocrService.extractTextFromImage(tempFile);
            model.addAttribute("ocrResult", extractedText);

        } catch (IOException e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }

        return "result";
    }
}

