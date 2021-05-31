package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteMapper noteMapper;

    public List<Note> getNotes(Integer userId) {
        return noteMapper.getNotes(userId);
    }

    public int saveNote(Integer userId, Note note) {
        note.setUserId(userId);
        return noteMapper.insertNote(note);
    }

    public boolean updateNote(Integer noteId, String noteTitle, String description) {
        noteMapper.updateNote(noteId, noteTitle, description);
        return true;
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }
}
