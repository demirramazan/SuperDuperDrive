package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileMapper fileMapper;

    public List<File> getFiles(Integer userId) {
        return fileMapper.getFiles(userId);
    }

    public File getFileById(Integer fileId) {
        return fileMapper.getFile(fileId)
                .orElse(null);
    }

    public File getFileByFileName(String fileName) {
        return fileMapper.getFileByFileName(fileName)
                .orElse(null);
    }

    public int saveFile(MultipartFile file, Integer userId) throws IOException {
        String filename = file.getOriginalFilename();
        File fileByFileName = getFileByFileName(filename);
        if (fileByFileName != null) {
            throw new IllegalArgumentException("Already filename is exist!");
        }
        File saveFile = File.builder()
                .fileName(filename)
                .fileSize(String.valueOf(file.getSize()))
                .contentType(file.getContentType())
                .userId(userId)
                .fileData(file.getBytes())
                .build();
        return fileMapper.insertFile(saveFile);
    }

    public void deleteFile(Integer fileId) {
        fileMapper.deleteFile(fileId);
    }
}
