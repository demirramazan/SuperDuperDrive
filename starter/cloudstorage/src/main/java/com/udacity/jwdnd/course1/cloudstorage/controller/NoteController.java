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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            model.addAttribute("errorMessage", "User is not found");
            return "redirect:/login";
        }
        List<Note> notes = noteService.getNotes(userId);
        model.addAttribute("notes", notes);
        model.addAttribute("result", true);
        return "redirect:/home";
    }


    @PostMapping
    public String saveNote(Note note, RedirectAttributes redirectAttributes) {
        Integer userId = utilService.getUserId();
        if (userId == null) {
            redirectAttributes.addAttribute("errorMessage", "User is not log in!");
            return "redirect:/login";
        }
        if (note.getNoteId() == null) {
            noteService.saveNote(userId, note);
            redirectAttributes.addFlashAttribute("successMessage", "Note added successfully!");
        } else {
            noteService.updateNote(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription());
            redirectAttributes.addFlashAttribute("successMessage", "Note updated successfully!");
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, RedirectAttributes redirectAttributes) {
        noteService.deleteNote(noteId);
        redirectAttributes.addFlashAttribute("successMessage", "Note deleted successfully!");
        return "redirect:/home";
    }
}
