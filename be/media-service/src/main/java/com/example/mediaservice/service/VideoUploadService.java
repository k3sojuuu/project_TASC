package com.example.mediaservice.service;

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
public class VideoUploadService {

    private static final String IMGUR_UPLOAD_URL = "https://api.imgur.com/3/upload";

    @Value("${imgur.client.id}")
    private String CLIENT_ID;

    public List<String> uploadVideos(MultipartFile[] videos) throws IOException {
        List<String> videoUrls = new ArrayList<>();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            for (MultipartFile video : videos) {
                HttpPost uploadFile = new HttpPost(IMGUR_UPLOAD_URL);
                uploadFile.addHeader("Authorization", "Client-ID " + CLIENT_ID);

                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addBinaryBody(
                        "video",
                        video.getInputStream(),
                        ContentType.DEFAULT_BINARY,
                        video.getOriginalFilename()
                );
                org.apache.http.HttpEntity multipart = builder.build();
                uploadFile.setEntity(multipart);


                HttpResponse response = httpClient.execute(uploadFile);
                org.apache.http.HttpEntity responseEntity = response.getEntity();
                String result = EntityUtils.toString(responseEntity);

                // Phân tích JSON trả về từ Imgur API
                JSONObject jsonResponse = new JSONObject(result);
                JSONObject data = jsonResponse.getJSONObject("data");

                // Kiểm tra nếu video upload thành công
                if (response.getStatusLine().getStatusCode() == 200) {
                    // Lấy URL video đã upload
                    String videoUrl = data.getString("link");


                    // Thêm URL video vào danh sách
                    videoUrls.add(videoUrl);
                } else {
                    // Xử lý khi upload thất bại (có thể log thông tin hoặc throw exception tùy yêu cầu)
                    throw new IOException("Failed to upload video: " + data.getString("error"));
                }
            }
        }
        return videoUrls;
    }
}
