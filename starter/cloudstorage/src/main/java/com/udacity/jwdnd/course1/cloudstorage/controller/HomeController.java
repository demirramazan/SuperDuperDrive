package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.util.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final NoteService noteService;
    private final FileService fileService;
    private final CredentialService credentialService;
    private final UtilService utilService;

    @GetMapping("/home")
    public String getHome(Model model) {
        Integer userId = utilService.getUserId();
        if (userId == null) {
            model.addAttribute("errorMessage", "User is not log in!");
            return "redirect:/login";
        }
        List<Note> notes = noteService.getNotes(userId);
        List<File> files = fileService.getFiles(userId);
        List<Credential> credentials = credentialService.getCredentials(userId);
        model.addAttribute("files", files);
        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);

        return "/home";
    }

}
