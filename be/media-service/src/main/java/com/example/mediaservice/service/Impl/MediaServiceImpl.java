package com.example.mediaservice.service.Impl;

import com.example.mediaservice.model.Image;
import com.example.mediaservice.repository.ImageRepository;
import com.example.mediaservice.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MediaServiceImpl {

    @Autowired
    private ImageRepository imageRepository;
    private static final String UPLOAD_DIR = "D:/uploads/";
    private static final long MAX_SIZE = 10485760;


    public List<String> uploadImages(MultipartFile[] images, Long typeImg, Long userId) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            if (image.getSize() > MAX_SIZE) {
                throw new IOException("File is too large: " + image.getOriginalFilename());
            }
            String contentType = image.getContentType();
            if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
                throw new IOException("Invalid file type: " + image.getOriginalFilename());
            }

            Path path = Paths.get(UPLOAD_DIR + image.getOriginalFilename());
            Files.write(path, image.getBytes());

            Image savedImage = new Image();
            savedImage.setFileName(image.getOriginalFilename());
            savedImage.setPathName(path.toString());
            savedImage.setTypeImg(typeImg);
            savedImage.setUploadTime(new Date());
            savedImage.setUserId(userId);
            imageRepository.save(savedImage);

            imageUrls.add(path.toString());
        }

        return imageUrls;
    }

    public void deleteImage(Long id) {
        Image existingImage = null;
        try {
            existingImage = imageRepository.findById(id).orElseThrow(() -> new FileNotFoundException("Image not found"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        // Xóa ảnh từ hệ thống tệp
        try {
            Files.deleteIfExists(Paths.get(existingImage.getPathName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Xóa ảnh từ cơ sở dữ liệu
        imageRepository.deleteById(id);
    }

    public String changeImage(Long id, MultipartFile newImage, Long typeImg) throws IOException {
        Image existingImage = imageRepository.findById(id).orElseThrow(() -> new FileNotFoundException("Image not found"));

        // Xóa ảnh cũ
        Files.deleteIfExists(Paths.get(existingImage.getPathName()));

        // Lưu ảnh mới
        Path newPath = Paths.get(UPLOAD_DIR + newImage.getOriginalFilename());
        Files.write(newPath, newImage.getBytes());

        // Cập nhật thông tin ảnh
        existingImage.setFileName(newImage.getOriginalFilename());
        existingImage.setPathName(newPath.toString());
        existingImage.setTypeImg(typeImg);
        imageRepository.save(existingImage);

        return newPath.toString();
    }

}
