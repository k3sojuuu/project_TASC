package com.example.scheduleservice.model.DTO;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class MultipartFileResource extends InputStreamResource {

    private final MultipartFile file;

    public MultipartFileResource(MultipartFile file) throws IOException {
        super(file.getInputStream());
        this.file = file;
    }

    public String getFilename() {
        return file.getOriginalFilename();
    }

    public long contentLength() throws IOException {
        return file.getSize();
    }
}

