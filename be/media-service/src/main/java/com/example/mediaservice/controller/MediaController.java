package com.example.mediaservice.controller;

import com.example.mediaservice.service.Impl.MediaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/sv5/media")
public class MediaController {
    @Autowired
    private MediaServiceImpl mediaService;

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile[] image,
                                              @RequestParam("typeImg") Long typeImg,
                                              @RequestParam("userId") Long userId) {
        try {
            List<String> imageUrls = mediaService.uploadImages(image, typeImg, userId);
            return ResponseEntity.ok(String.join(", ", imageUrls));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file" + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {
        mediaService.deleteImage(id);
        return ResponseEntity.ok("Image deleted successfully");
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<String> changeImage(@PathVariable Long id, @RequestParam("image") MultipartFile image, @RequestParam("typeImg") Long typeImg) {
        try {
            String imageUrl = mediaService.changeImage(id, image, typeImg);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to change image");
        }
    }
}
