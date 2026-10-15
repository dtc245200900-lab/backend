<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.codegym.product.Product,java.math.BigDecimal" %>
<%!
String esc(String s) {
    if (s == null) return "";
    return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;").replace("'","&#39;");
}
%>
<%
Product p=(Product)request.getAttribute("product");
if(p==null)p=new Product();
Object ro=request.getAttribute("readonly");
boolean readonly=Boolean.TRUE.equals(ro);
boolean edit=p.getId()>0;
String error=(String)request.getAttribute("error");
%>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title><%=readonly?"Chi tiết":edit?"Sửa":"Thêm mới"%> sản phẩm</title>
<style>
*{box-sizing:border-box}body{margin:0;background:#f4f7fb;color:#172033;font-family:Segoe UI,Arial,sans-serif}.wrap{max-width:700px;margin:38px auto;padding:0 18px}.back{color:#4f46e5;text-decoration:none;font-weight:650;font-size:14px}.card{margin-top:18px;background:#fff;border:1px solid #e5eaf1;border-radius:16px;padding:30px;box-shadow:0 8px 25px #17203308}h1{margin:0 0 8px;font-size:27px}.muted{color:#64748b;font-size:14px;margin-bottom:25px}.field{margin-bottom:20px}label{display:block;font-weight:700;font-size:14px;margin-bottom:8px}input{width:100%;border:1px solid #d8deea;border-radius:10px;padding:12px;font:inherit}input:focus{outline:3px solid #c7d2fe;border-color:#6366f1}.alert{padding:12px;border-radius:10px;background:#fff7ed;color:#9a3412;margin-bottom:18px}.actions{display:flex;justify-content:flex-end;gap:10px;flex-wrap:wrap}.btn{display:inline-block;border:0;border-radius:9px;padding:12px 16px;text-decoration:none;font-weight:700;font-size:14px;cursor:pointer;background:#eef2ff;color:#3730a3}.primary{background:#4f46e5;color:white}@media(max-width:600px){.wrap{margin:22px auto}.card{padding:20px}}
</style></head><body><div class="wrap"><a class="back" href="<%=request.getContextPath()%>/products">← Quay lại danh sách</a><section class="card"><h1><%=readonly?"Chi tiết sản phẩm":edit?"Cập nhật sản phẩm":"Thêm sản phẩm mới"%></h1><div class="muted">Nhập đầy đủ thông tin sản phẩm bên dưới.</div>
<%if(error!=null){%><div class="alert"><%=esc(error)%></div><%}%>
<form method="post" action="<%=request.getContextPath()%>/products"><input type="hidden" name="action" value="<%=edit?"update":"create"%>"><%if(edit){%><input type="hidden" name="id" value="<%=p.getId()%>"><%}%>
<div class="field"><label for="name">Tên sản phẩm</label><input id="name" name="name" maxlength="255" required value="<%=esc(p.getName())%>" <%=readonly?"readonly":""%> placeholder="Ví dụ: Bàn phím cơ"></div>
<div class="field"><label for="price">Giá (VNĐ)</label><input id="price" name="price" type="number" min="0" step="0.01" required value="<%=p.getPrice()==null?"0":p.getPrice().toPlainString()%>" <%=readonly?"readonly":""%>></div>
<div class="field"><label for="quantity">Số lượng</label><input id="quantity" name="quantity" type="number" min="0" step="1" required value="<%=p.getQuantity()%>" <%=readonly?"readonly":""%>></div>
<div class="actions"><a class="btn" href="<%=request.getContextPath()%>/products"><%=readonly?"Đóng":"Hủy bỏ"%></a><%if(readonly){%><a class="btn primary" href="<%=request.getContextPath()%>/products?action=edit&id=<%=p.getId()%>">Chỉnh sửa</a><%}else{%><button class="btn primary" type="submit">Lưu sản phẩm</button><%}%></div></form></section></div></body></html>
