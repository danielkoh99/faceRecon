package com.faceRecon.service;

import java.io.File;

public class FileService {
    public boolean deleteFile(String filePath) {
        File myObj = new File(filePath);
        return myObj.delete();
    }
}
