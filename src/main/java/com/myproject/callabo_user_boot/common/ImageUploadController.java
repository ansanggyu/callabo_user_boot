package com.myproject.callabo_user_boot.common;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api2/images")
@RequiredArgsConstructor
public class ImageUploadController {

    private final S3Uploader s3Uploader;

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadImages(@RequestPart("imageFiles") List<MultipartFile> imageFiles) {
        try {
            // 각 파일을 업로드하고 URL 리스트 생성
            List<String> uploadedUrls = imageFiles.stream()
                    .map(file -> s3Uploader.upload(file, "uploads"))
                    .toList();

            // 업로드된 이미지 URL 리스트 반환
            return ResponseEntity.ok(uploadedUrls);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(List.of("Image upload failed: " + e.getMessage()));
        }
    }

}
