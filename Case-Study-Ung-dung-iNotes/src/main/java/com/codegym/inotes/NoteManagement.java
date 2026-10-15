package com.codegym.inotes;

import java.util.List;

public class NoteManagement {
    private NoteStrategy strategy;

    public NoteManagement(NoteStrategy strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(NoteStrategy strategy) {
        if (strategy == null) throw new IllegalArgumentException("Strategy không được null");
        this.strategy = strategy;
    }

    public NoteStrategy getStrategy() { return strategy; }

    public List<Note> searchNotes(String keyword) {
        return (keyword == null || keyword.isBlank()) ? strategy.findAll() : strategy.search(keyword);
    }

    public Note addNote(String title, String content, int typeId) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Tiêu đề không được để trống.");
        return strategy.save(new Note(title.trim(), content == null ? "" : content.trim(), typeId));
    }

    public Note updateNote(int id, String title, String content, int typeId) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Tiêu đề không được để trống.");
        Note note = strategy.findById(id);
        if (note == null) throw new IllegalArgumentException("Không tìm thấy ghi chú.");
        note.setTitle(title.trim());
        note.setContent(content == null ? "" : content.trim());
        note.setTypeId(typeId);
        return strategy.save(note);
    }

    public boolean deleteNote(int id) {
        return strategy.delete(id);
    }

    public List<NoteType> getNoteTypes() { return strategy.findAllTypes(); }
    public Note getNote(int id) { return strategy.findById(id); }
}
