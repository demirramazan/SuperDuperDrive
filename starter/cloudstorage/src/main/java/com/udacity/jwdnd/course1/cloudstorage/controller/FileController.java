package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.util.AppConstant;
import com.udacity.jwdnd.course1.cloudstorage.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.udacity.jwdnd.course1.cloudstorage.util.AppConstant.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;
    private final UtilService utilService;

    @PostMapping("/file-upload")
    public String fileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Model model) throws IOException {
        Integer userId = utilService.getUserId();
        if (fileUpload.isEmpty()) {
            model.addAttribute("files", fileService.getFiles(userId));
            model.addAttribute("errorMessage", "Cannot upload without selecting a file. ");
            return "/home";
        }
        File isFileExist = fileService.getFileByFileName(fileUpload.getOriginalFilename());
        if (isFileExist != null) {
            model.addAttribute("files", fileService.getFiles(userId));
            model.addAttribute("errorMessage", MAX_FILE_ALREADY_EXIST_EXCEPTION);
            return "/home";
        }
        if (fileUpload.getSize() > AppConstant.MAX_FILE_SIZE.longValue()) {
            model.addAttribute("files", fileService.getFiles(userId));
            model.addAttribute("errorMessage", MAX_FILE_SIZE_EXCEPTION);
            return "/home";
        }
        fileService.saveFile(fileUpload, userId);
        model.addAttribute("files", fileService.getFiles(userId));
        model.addAttribute("successMessage", "File upload is success.");
        return "/home";
    }

    @GetMapping("/{fileId}")
    @ResponseBody
    public ResponseEntity<Resource> getFileView(@PathVariable Integer fileId, Model model) {
        File file = fileService.getFileById(fileId);

        model.addAttribute("result", "success");
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFileName() + "\"").body(new ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, Model model) {
        fileService.deleteFile(fileId);
        model.addAttribute("files", fileService.getFiles(utilService.getUserId()));
        model.addAttribute("message", "Delete file is success..");
        model.addAttribute("result", "success");
        return "redirect:/home";
    }

}
