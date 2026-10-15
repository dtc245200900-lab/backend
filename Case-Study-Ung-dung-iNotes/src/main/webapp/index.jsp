<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,com.codegym.inotes.Note,com.codegym.inotes.NoteType" %>
<%!
String esc(String s) {
    if (s == null) return "";
    return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;").replace("'","&#39;");
}
String typeName(List<NoteType> types, int id) {
    for (NoteType t : types) if (t.getId() == id) return t.getName();
    return "Không xác định";
}
%>
<%
List<Note> notes = (List<Note>) request.getAttribute("notes");
List<NoteType> types = (List<NoteType>) request.getAttribute("types");
String keyword = (String) request.getAttribute("keyword");
String mode = (String) request.getAttribute("storageMode");
String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="vi"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title>iNotes - Quản lý ghi chú</title>
<style>
:root{--ink:#172033;--muted:#64748b;--brand:#4f46e5;--line:#e6eaf0;--bg:#f5f7fb}
*{box-sizing:border-box}body{margin:0;background:var(--bg);color:var(--ink);font-family:Inter,Segoe UI,Arial,sans-serif}
header{background:#fff;border-bottom:1px solid var(--line)}.top{max-width:1100px;margin:auto;padding:20px 22px;display:flex;align-items:center;justify-content:space-between;gap:14px;flex-wrap:wrap}
.logo{font-size:25px;font-weight:800;letter-spacing:-.8px}.logo span{color:var(--brand)}.sub{color:var(--muted);font-size:13px;margin-top:3px}
main{max-width:1100px;margin:32px auto;padding:0 22px}.heading{display:flex;align-items:end;justify-content:space-between;gap:16px;flex-wrap:wrap;margin-bottom:22px}
h1{font-size:28px;margin:0 0 6px}.muted{color:var(--muted)}.btn{display:inline-flex;align-items:center;justify-content:center;text-decoration:none;border:0;border-radius:10px;padding:11px 15px;font-weight:650;font-size:14px;cursor:pointer;background:#eef2ff;color:#3730a3}.primary{background:var(--brand);color:#fff}.danger{background:#fff1f2;color:#be123c}.small{padding:7px 10px;font-size:13px}
.card{background:#fff;border:1px solid var(--line);border-radius:16px;box-shadow:0 8px 24px #17203308;overflow:hidden}.toolbar{padding:18px;display:flex;gap:12px;justify-content:space-between;flex-wrap:wrap;border-bottom:1px solid var(--line)}
.search{display:flex;gap:8px;flex:1;max-width:520px}.search input{min-width:0;flex:1;border:1px solid #d9dfeb;border-radius:10px;padding:11px 13px;font-size:14px}.storage{display:flex;align-items:center;gap:8px;flex-wrap:wrap;font-size:13px;color:var(--muted)}
table{width:100%;border-collapse:collapse}th,td{text-align:left;padding:16px 18px;border-bottom:1px solid #edf0f5}th{font-size:12px;text-transform:uppercase;letter-spacing:.06em;color:var(--muted);background:#fafbfe}tbody tr:last-child td{border-bottom:0}.num{color:#94a3b8;width:60px}.pill{display:inline-block;border-radius:999px;padding:5px 10px;background:#eef2ff;color:#4338ca;font-size:12px;font-weight:700}.actions{display:flex;gap:7px;flex-wrap:wrap}.titlecell{font-weight:700}.preview{font-size:13px;color:var(--muted);margin-top:5px;max-width:470px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis}.empty{text-align:center;padding:50px 18px;color:var(--muted)}.alert{padding:12px 15px;margin-bottom:15px;border-radius:10px;background:#fff7ed;color:#9a3412}.footer{padding:15px 18px;border-top:1px solid var(--line);font-size:13px;color:var(--muted)}
@media(max-width:650px){main{margin:22px auto;padding:0 12px}.top{padding:16px 12px}h1{font-size:24px}.toolbar{padding:12px}th,td{padding:12px 10px}.hide-mobile{display:none}.actions{min-width:100px}}
</style></head><body>
<header><div class="top"><div><div class="logo">i<span>Notes</span></div><div class="sub">Không bỏ lỡ những điều quan trọng</div></div><a class="btn primary" href="<%=request.getContextPath()%>/notes/new">＋ Thêm ghi chú</a></div></header>
<main><div class="heading"><div><h1>Ghi chú của bạn</h1><div class="muted">Quản lý, phân loại và tìm kiếm tất cả ghi chú tại một nơi.</div></div></div>
<% if (error != null) { %><div class="alert"><%=esc(error)%></div><% } %>
<% if (request.getParameter("message") != null) { %><div class="alert" style="background:#ecfdf5;color:#047857"><%=esc(request.getParameter("message"))%></div><% } %>
<section class="card"><div class="toolbar">
<form class="search" method="get" action="<%=request.getContextPath()%>/notes"><input type="search" name="q" value="<%=esc(keyword)%>" placeholder="Tìm theo tiêu đề hoặc nội dung..."><button class="btn primary" type="submit">Tìm kiếm</button><% if(keyword!=null&&!keyword.isBlank()){ %><a class="btn" href="<%=request.getContextPath()%>/notes">Xóa lọc</a><% } %></form>
<div class="storage"><span>Lưu trữ: <strong><%= "db".equalsIgnoreCase(mode) ? "MySQL" : "File TXT" %></strong></span><a class="btn small" href="<%=request.getContextPath()%>/settings/storage?mode=file">Dùng File</a><a class="btn small" href="<%=request.getContextPath()%>/settings/storage?mode=db">Dùng MySQL</a></div>
</div>
<% if(notes==null||notes.isEmpty()){ %><div class="empty"><div style="font-size:36px;margin-bottom:8px">✎</div><strong>Chưa có ghi chú phù hợp</strong><p>Hãy thêm ghi chú mới hoặc thử từ khóa khác.</p><a class="btn primary" href="<%=request.getContextPath()%>/notes/new">Tạo ghi chú đầu tiên</a></div><% } else { %>
<div style="overflow-x:auto"><table><thead><tr><th class="num">#</th><th>Tiêu đề</th><th>Phân loại</th><th>Thao tác</th></tr></thead><tbody>
<% int i=1; for(Note n:notes){ %><tr><td class="num"><%=i++%></td><td><div class="titlecell"><%=esc(n.getTitle())%></div><div class="preview"><%=esc(n.getContent())%></div></td><td><span class="pill"><%=esc(typeName(types,n.getTypeId()))%></span></td><td><div class="actions"><a class="btn small" href="<%=request.getContextPath()%>/notes/view?id=<%=n.getId()%>">Xem</a><a class="btn small" href="<%=request.getContextPath()%>/notes/edit?id=<%=n.getId()%>">Sửa</a><form method="post" action="<%=request.getContextPath()%>/notes" onsubmit="return confirm('Bạn chắc chắn muốn xóa ghi chú này?')"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="<%=n.getId()%>"><button class="btn small danger" type="submit">Xóa</button></form></div></td></tr><% } %>
</tbody></table></div><% } %><div class="footer">Tổng cộng: <strong><%=notes==null?0:notes.size()%></strong> ghi chú · iNotes</div></section></main></body></html>
