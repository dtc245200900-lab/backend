package com.codegym.inotes;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/notes", "/notes/new", "/notes/view", "/notes/edit", "/notes/delete", "/settings/storage"})
public class NoteServlet extends HttpServlet {
    private NoteManagement management;
    private String storageMode;

    @Override
    public void init() {
        storageMode = getServletContext().getInitParameter("storageMode");
        if (storageMode == null) storageMode = "file";
        management = new NoteManagement(createStrategy(storageMode));
    }

    private NoteStrategy createStrategy(String mode) {
        if ("db".equalsIgnoreCase(mode)) return new NoteDBStrategy();
        return new NoteFileStrategy();
    }

    private void switchStrategy(String mode) {
        storageMode = mode;
        management.setStrategy(createStrategy(mode));
        getServletContext().setAttribute("storageMode", mode);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();
        try {
            if ("/settings/storage".equals(path)) {
                String mode = req.getParameter("mode");
                if (!"db".equalsIgnoreCase(mode) && !"file".equalsIgnoreCase(mode)) mode = "file";
                switchStrategy(mode);
                resp.sendRedirect(req.getContextPath() + "/notes?message=Đã%20chuyển%20nguồn%20lưu%20trữ");
                return;
            }
            if ("/notes/view".equals(path) || "/notes/edit".equals(path)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Note note = management.getNote(id);
                if (note == null) { resp.sendError(404, "Không tìm thấy ghi chú"); return; }
                req.setAttribute("note", note);
                req.setAttribute("types", management.getNoteTypes());
                req.getRequestDispatcher("/note-form.jsp").forward(req, resp);
                return;
            }
            if ("/notes/new".equals(path)) {
                req.setAttribute("types", management.getNoteTypes());
                req.getRequestDispatcher("/note-form.jsp").forward(req, resp);
                return;
            }
            req.setAttribute("notes", management.searchNotes(req.getParameter("q")));
            req.setAttribute("types", management.getNoteTypes());
            req.setAttribute("keyword", req.getParameter("q") == null ? "" : req.getParameter("q"));
            req.setAttribute("storageMode", storageMode);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (RuntimeException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("notes", java.util.List.of());
            req.setAttribute("types", management.getNoteTypes());
            req.setAttribute("keyword", "");
            req.setAttribute("storageMode", storageMode);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        try {
            if ("delete".equals(action)) {
                management.deleteNote(Integer.parseInt(req.getParameter("id")));
            } else if ("save".equals(action)) {
                String id = req.getParameter("id");
                String title = req.getParameter("title");
                String content = req.getParameter("content");
                int typeId = Integer.parseInt(req.getParameter("typeId"));
                if (id == null || id.isBlank()) management.addNote(title, content, typeId);
                else management.updateNote(Integer.parseInt(id), title, content, typeId);
            }
            resp.sendRedirect(req.getContextPath() + "/notes?message=Thao%20tác%20thành%20công");
        } catch (RuntimeException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("types", management.getNoteTypes());
            req.setAttribute("note", new Note(0, req.getParameter("title"), req.getParameter("content"),
                    parseType(req.getParameter("typeId"))));
            req.getRequestDispatcher("/note-form.jsp").forward(req, resp);
        }
    }

    private int parseType(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return 1; }
    }
}
