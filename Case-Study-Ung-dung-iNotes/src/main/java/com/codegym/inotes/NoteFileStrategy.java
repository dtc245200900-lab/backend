package com.codegym.inotes;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * File storage format: id|title|content|typeId
 * Pipe and newlines in user content are escaped to keep one record per line.
 */
public class NoteFileStrategy implements NoteStrategy {
    private final Path notesPath;
    private final Path typesPath;

    public NoteFileStrategy() {
        this(Path.of("data", "notes.txt"), Path.of("data", "note-types.txt"));
    }

    public NoteFileStrategy(Path notesPath, Path typesPath) {
        this.notesPath = notesPath;
        this.typesPath = typesPath;
        try {
            Files.createDirectories(notesPath.toAbsolutePath().getParent());
            Files.createDirectories(typesPath.toAbsolutePath().getParent());
            if (Files.notExists(notesPath)) Files.createFile(notesPath);
            if (Files.notExists(typesPath)) {
                Files.write(typesPath, List.of("1|Cá nhân|Ghi chú cá nhân",
                        "2|Công việc|Ghi chú công việc", "3|Học tập|Ghi chú học tập"), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new RuntimeException("Không thể khởi tạo file dữ liệu: " + e.getMessage(), e);
        }
    }

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("|", "\\p")
                .replace("\r", "\\r").replace("\n", "\\n");
    }
    private String unesc(String s) {
        StringBuilder out = new StringBuilder();
        boolean slash = false;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (slash) {
                if (ch == 'p') out.append('|');
                else if (ch == 'n') out.append('\n');
                else if (ch == 'r') out.append('\r');
                else out.append(ch);
                slash = false;
            } else if (ch == '\\') slash = true;
            else out.append(ch);
        }
        if (slash) out.append('\\');
        return out.toString();
    }
    private List<Note> readNotes() {
        try {
            List<Note> result = new ArrayList<>();
            for (String line : Files.readAllLines(notesPath, StandardCharsets.UTF_8)) {
                String[] p = line.split("\\|", 4);
                if (p.length == 4) result.add(new Note(Integer.parseInt(p[0]), unesc(p[1]), unesc(p[2]), Integer.parseInt(p[3])));
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Không thể đọc file ghi chú: " + e.getMessage(), e);
        }
    }
    private void writeNotes(List<Note> notes) {
        List<String> lines = notes.stream().map(n -> n.getId() + "|" + esc(n.getTitle()) + "|" +
                esc(n.getContent()) + "|" + n.getTypeId()).collect(Collectors.toList());
        try {
            Files.write(notesPath, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Không thể ghi file ghi chú: " + e.getMessage(), e);
        }
    }
    @Override
    public synchronized Note save(Note note) {
        List<Note> all = readNotes();
        if (note.getId() > 0) {
            boolean found = false;
            for (int i = 0; i < all.size(); i++) {
                if (all.get(i).getId() == note.getId()) { all.set(i, note); found = true; break; }
            }
            if (!found) throw new IllegalArgumentException("Không tìm thấy ghi chú để cập nhật.");
        } else {
            int nextId = all.stream().mapToInt(Note::getId).max().orElse(0) + 1;
            note.setId(nextId);
            all.add(note);
        }
        writeNotes(all);
        return note;
    }
    @Override
    public synchronized boolean delete(int id) {
        List<Note> all = readNotes();
        boolean removed = all.removeIf(n -> n.getId() == id);
        if (removed) writeNotes(all);
        return removed;
    }
    @Override
    public List<Note> findAll() {
        return readNotes().stream().sorted(Comparator.comparingInt(Note::getId).reversed()).collect(Collectors.toList());
    }
    @Override
    public List<Note> search(String keyword) {
        String q = keyword == null ? "" : keyword.toLowerCase(Locale.ROOT).trim();
        return findAll().stream().filter(n -> n.getTitle().toLowerCase(Locale.ROOT).contains(q)
                || (n.getContent() != null && n.getContent().toLowerCase(Locale.ROOT).contains(q)))
                .collect(Collectors.toList());
    }
    @Override
    public List<NoteType> findAllTypes() {
        try {
            List<NoteType> types = new ArrayList<>();
            for (String line : Files.readAllLines(typesPath, StandardCharsets.UTF_8)) {
                String[] p = line.split("\\|", 3);
                if (p.length == 3) types.add(new NoteType(Integer.parseInt(p[0]), p[1], p[2]));
            }
            return types;
        } catch (IOException e) {
            throw new RuntimeException("Không thể đọc file danh mục: " + e.getMessage(), e);
        }
    }
    @Override
    public Note findById(int id) {
        return readNotes().stream().filter(n -> n.getId() == id).findFirst().orElse(null);
    }
}
