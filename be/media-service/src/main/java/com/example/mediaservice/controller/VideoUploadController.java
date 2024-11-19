package com.example.mediaservice.controller;




import com.example.mediaservice.service.VideoUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/uploadVideos")
public class VideoUploadController {

    @Autowired
    private VideoUploadService videoUploadService;

    @PostMapping("/upload-video")
    public ResponseEntity<Object> uploadVideosToImgur(@RequestParam("videos") MultipartFile[] videos) {
        try {
            // Gọi phương thức từ service để upload video
            List<String> videoUrls = videoUploadService.uploadVideos(videos);

            // Trả về danh sách URL của các video đã upload
            return new ResponseEntity<>(videoUrls, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error occurred while uploading videos", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}