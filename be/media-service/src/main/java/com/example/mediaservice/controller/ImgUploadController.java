package com.example.mediaservice.controller;

import com.example.mediaservice.model.UploadResult;

import com.example.mediaservice.service.Impl.ImageUploadService;
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
@RequestMapping("/uploadImg")
public class ImgUploadController {

    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/upload-images")
    public ResponseEntity<Object> uploadImagesToImgur(@RequestParam("images") MultipartFile[] images) {
        try {
            // Gọi phương thức từ service để upload ảnh
            List<UploadResult> uploadResults = imageUploadService.uploadImages(images);

            // Trả về danh sách kết quả upload
            return new ResponseEntity<>(uploadResults, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error occurred while uploading images", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}