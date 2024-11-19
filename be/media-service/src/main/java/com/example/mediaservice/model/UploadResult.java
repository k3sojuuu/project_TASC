package com.example.mediaservice.model;

public class UploadResult {
    private String status;
    private String data;

    public UploadResult(String status, String data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }
}
