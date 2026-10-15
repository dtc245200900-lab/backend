package com.codegym.inotes;

import java.util.List;

public interface NoteStrategy {
    Note save(Note note);
    boolean delete(int id);
    List<Note> findAll();
    List<Note> search(String keyword);
    List<NoteType> findAllTypes();
    Note findById(int id);
}
