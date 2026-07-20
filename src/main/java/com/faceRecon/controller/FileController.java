package com.faceRecon.controller;

import java.io.File;
import java.io.FileOutputStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        String filePath = System.getProperty("user.dir") + "/uploads" +
                File.separator + file.getOriginalFilename();
        String fileUploadStatus;

        try {

            FileOutputStream fout = new FileOutputStream(filePath);
            fout.write(file.getBytes());

            fout.close();
            fileUploadStatus = "File Uploaded Successfully";

        }

        catch (Exception e) {
            e.printStackTrace();
            fileUploadStatus = "Error in uploading file: " + e;
        }
        return fileUploadStatus;
    }

    @RequestMapping(value = "/getFiles", method = RequestMethod.GET)
    public String[] getFiles() {
        String folderPath = System.getProperty("user.dir") + "/uploads";

        File directory = new File(folderPath);

        String[] filenames = directory.list();

        return filenames;

    }
}