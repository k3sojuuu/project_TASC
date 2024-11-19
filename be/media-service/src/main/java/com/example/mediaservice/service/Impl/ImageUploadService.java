package com.example.mediaservice.service.Impl;

import org.springframework.stereotype.Service;

import com.example.mediaservice.model.UploadResult;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageUploadService {

    private static final String IMGUR_UPLOAD_URL = "https://api.imgur.com/3/upload";

    @Value("${imgur.client.id}")
    private String CLIENT_ID;

    public List<UploadResult> uploadImages(MultipartFile[] images) throws IOException {
        List<UploadResult> uploadResults = new ArrayList<>(); // Danh sách chứa kết quả của mỗi ảnh

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            for (MultipartFile image : images) {
                HttpPost uploadFile = new HttpPost(IMGUR_UPLOAD_URL);
                uploadFile.addHeader("Authorization", "Client-ID " + CLIENT_ID); // Thêm Client ID vào header

                // Tạo MultipartEntityBuilder để xử lý tệp ảnh
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addBinaryBody(
                        "image", // Trường cần thiết cho Imgur API
                        image.getInputStream(),
                        ContentType.DEFAULT_BINARY,
                        image.getOriginalFilename() // Tên tệp ảnh
                );

                org.apache.http.HttpEntity multipart = builder.build();
                uploadFile.setEntity(multipart);  // Gán entity vào request

                // Thực thi HTTP request và nhận kết quả
                HttpResponse response = httpClient.execute(uploadFile);
                org.apache.http.HttpEntity responseEntity = response.getEntity();
                String result = EntityUtils.toString(responseEntity);

                // Phân tích JSON trả về từ Imgur API
                JSONObject jsonResponse = new JSONObject(result);
                JSONObject data = jsonResponse.getJSONObject("data");

                // Kiểm tra nếu ảnh upload thành công
                if (response.getStatusLine().getStatusCode() == 200) {
                    // Lấy URL ảnh đã upload
                    String imageUrl = data.getString("link");

                    // Thêm kết quả thành công vào danh sách kết quả
                    uploadResults.add(new UploadResult("success", imageUrl));
                } else {
                    // Thêm kết quả thất bại vào danh sách
                    uploadResults.add(new UploadResult("failed", data.getString("error")));
                }
            }
        }
        return uploadResults; // Trả về danh sách kết quả upload
    }
}
