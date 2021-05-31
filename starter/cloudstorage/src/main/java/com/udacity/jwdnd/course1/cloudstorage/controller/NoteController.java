package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    private final UtilService utilService;

    @GetMapping
    public String getNotes(Model model) {
        Integer userId = utilService.getUserId();
        if (userId == null) {
            model.addAttribute("result", "error");
            model.addAttribute("message", "User is not found");
        }
        List<Note> notes = noteService.getNotes(userId);
        model.addAttribute("notes", notes);
        model.addAttribute("result", true);
        return "redirect:/home";
    }


    @PostMapping
    public String saveNote(Authentication authentication, Note note, Model model) {
        Integer userId = utilService.getUserId();
        if (userId == null) {
            model.addAttribute("result", "error");
        }
        if (note.getNoteId() == null) {
            noteService.saveNote(userId, note);
        } else {
            noteService.updateNote(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription());
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Model model) {
        noteService.deleteNote(noteId);
        List<Note> noteList = noteService.getNotes(utilService.getUserId());
        model.addAttribute("notes", noteList);
        model.addAttribute("message", "Note deleted successfully!");
        return "redirect:/home";
    }
}
