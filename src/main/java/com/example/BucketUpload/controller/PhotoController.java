package com.example.BucketUpload.controller;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.example.BucketUpload.model.Photo;
import com.example.BucketUpload.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class PhotoController {
    @Autowired
    S3Service s3Service;

    @GetMapping("/photo")
    public String photoUploadController(Model model) {
        try {
            String fileName = "OIG4.jpeg"; // Burada dosya adını belirleyin veya kullanıcıdan alın
            String photoUrl = s3Service.getPhotoUrlFromS3(fileName);
            model.addAttribute("photoUrl", photoUrl);
        } catch (AmazonServiceException e) {
            model.addAttribute("error", "Error retrieving photo from S3: " + e.getMessage());
            e.printStackTrace();
        } catch (SdkClientException e) {
            model.addAttribute("error", "Error retrieving photo from S3: " + e.getMessage());
            e.printStackTrace();
        }
        return "form";
    }

    @PostMapping("/photo")
    public String savePhotos(Model model,
                             @RequestParam("file") MultipartFile file) {
        String filename = file.getOriginalFilename();
        System.out.println(filename);
        Photo photo = new Photo();
        photo.setUrl(filename);

        try {
            s3Service.uploadPhotoToS3(file.getInputStream(), filename);
        }catch (AmazonServiceException e) {
            model.addAttribute("message", "AWS Exception "+e.getMessage());
            e.printStackTrace();
        }catch (SdkClientException e) {
            model.addAttribute("message", "SDK Exception "+e.getMessage());
            e.printStackTrace();
        }catch (IOException e) {
            model.addAttribute("message", "IOException "+e.getMessage());
            e.printStackTrace();
        }
        model.addAttribute("photo", "Success " +photo);
        return "form";
    }



}
