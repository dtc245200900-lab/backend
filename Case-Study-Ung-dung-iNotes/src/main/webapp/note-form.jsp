<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,com.codegym.inotes.Note,com.codegym.inotes.NoteType" %>
<%!
String esc(String s) {
 if(s==null)return "";
 return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;").replace("'","&#39;");
}
%>
<%
Note note=(Note)request.getAttribute("note");
if(note==null) note=new Note();
List<NoteType> types=(List<NoteType>)request.getAttribute("types");
String path=request.getServletPath();
boolean view="/notes/view".equals(path);
boolean edit="/notes/edit".equals(path);
boolean readonly=view;
String error=(String)request.getAttribute("error");
%>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title><%=view?"Xem ghi chú":edit?"Sửa ghi chú":"Thêm ghi chú"%> - iNotes</title>
<style>
*{box-sizing:border-box}body{margin:0;background:#f5f7fb;color:#172033;font-family:Inter,Segoe UI,Arial,sans-serif}.wrap{max-width:760px;margin:42px auto;padding:0 18px}.back{color:#4f46e5;text-decoration:none;font-weight:650;font-size:14px}.card{background:white;border:1px solid #e6eaf0;border-radius:18px;padding:30px;margin-top:18px;box-shadow:0 10px 28px #1720330a}h1{margin:0 0 8px;font-size:27px}.muted{color:#64748b;margin-bottom:25px;font-size:14px}.field{margin-bottom:20px}label{display:block;font-size:14px;font-weight:700;margin-bottom:8px}input,select,textarea{width:100%;border:1px solid #d9dfeb;border-radius:10px;padding:12px 13px;font:inherit;background:#fff;color:#172033}textarea{min-height:200px;resize:vertical;line-height:1.6}.alert{padding:12px;border-radius:10px;background:#fff7ed;color:#9a3412;margin-bottom:18px}.actions{display:flex;gap:10px;justify-content:flex-end;flex-wrap:wrap}.btn{border:0;border-radius:10px;padding:12px 16px;text-decoration:none;display:inline-block;cursor:pointer;font-weight:700;font-size:14px;background:#eef2ff;color:#3730a3}.primary{background:#4f46e5;color:white}input:focus,select:focus,textarea:focus{outline:3px solid #c7d2fe;border-color:#6366f1}@media(max-width:600px){.wrap{margin:22px auto}.card{padding:20px}}
</style></head><body><div class="wrap"><a class="back" href="<%=request.getContextPath()%>/notes">← Quay lại danh sách</a><section class="card"><h1><%=view?"Chi tiết ghi chú":edit?"Cập nhật ghi chú":"Tạo ghi chú mới"%></h1><div class="muted">Ghi lại điều quan trọng để bạn dễ dàng tìm thấy sau này.</div>
<%if(error!=null){%><div class="alert"><%=esc(error)%></div><%}%>
<form method="post" action="<%=request.getContextPath()%>/notes">
<input type="hidden" name="action" value="save"><%if(note.getId()>0){%><input type="hidden" name="id" value="<%=note.getId()%>"><%}%>
<div class="field"><label for="title">Tiêu đề</label><input id="title" name="title" maxlength="255" required value="<%=esc(note.getTitle())%>" <%=readonly?"readonly":""%> placeholder="Ví dụ: Họp giao ban đầu tuần"></div>
<div class="field"><label for="typeId">Phân loại</label><select id="typeId" name="typeId" required <%=readonly?"disabled":""%>><%if(types!=null)for(NoteType t:types){%><option value="<%=t.getId()%>" <%=t.getId()==note.getTypeId()?"selected":""%>><%=esc(t.getName())%></option><%}%></select></div>
<div class="field"><label for="content">Nội dung</label><textarea id="content" name="content" <%=readonly?"readonly":""%> placeholder="Nhập nội dung ghi chú..."><%=esc(note.getContent())%></textarea></div>
<div class="actions"><a class="btn" href="<%=request.getContextPath()%>/notes"><%=view?"Đóng":"Hủy bỏ"%></a><%if(!view){%><button class="btn primary" type="submit">Lưu ghi chú</button><%}else{%><a class="btn primary" href="<%=request.getContextPath()%>/notes/edit?id=<%=note.getId()%>">Chỉnh sửa</a><%}%></div></form></section></div></body></html>
