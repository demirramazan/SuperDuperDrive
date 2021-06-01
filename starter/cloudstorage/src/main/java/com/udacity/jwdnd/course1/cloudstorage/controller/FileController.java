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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import static com.udacity.jwdnd.course1.cloudstorage.util.AppConstant.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;
    private final UtilService utilService;

    @PostMapping("/file-upload")
    public String fileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, RedirectAttributes redirectAttributes) throws IOException {
        Integer userId = utilService.getUserId();
        if (fileUpload.isEmpty()) {
            redirectAttributes.addAttribute("errorMessage", "Cannot upload without selecting a file. ");
            return "redirect:/home";
        }
        File isFileExist = fileService.getFileByFileName(fileUpload.getOriginalFilename());
        if (isFileExist != null) {
            redirectAttributes.addFlashAttribute("errorMessage", MAX_FILE_ALREADY_EXIST_EXCEPTION);
            return "redirect:/home";
        }
        if (fileUpload.getSize() > AppConstant.MAX_FILE_SIZE.longValue()) {
            redirectAttributes.addAttribute("errorMessage", MAX_FILE_SIZE_EXCEPTION);
            return "redirect:/home";
        }
        fileService.saveFile(fileUpload, userId);
        redirectAttributes.addFlashAttribute("successMessage", "File upload is success.");
        return "redirect:/home";
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
    public String deleteFile(@PathVariable Integer fileId,  RedirectAttributes redirectAttributes) {
        fileService.deleteFile(fileId);
        redirectAttributes.addAttribute("successMessage", "Delete file is success..");
        return "redirect:/home";
    }

}
