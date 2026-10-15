<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,com.codegym.product.Product" %>
<%!
String esc(String s) {
    if (s == null) return "";
    return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;").replace("'","&#39;");
}
%>
<%
List<Product> products = (List<Product>) request.getAttribute("products");
String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title>Quản lý sản phẩm</title>
<style>
*{box-sizing:border-box}body{margin:0;background:#f4f7fb;color:#172033;font-family:Segoe UI,Arial,sans-serif}.top{background:#fff;border-bottom:1px solid #e5eaf1}.nav{max-width:1080px;margin:auto;padding:22px;display:flex;justify-content:space-between;align-items:center;gap:15px;flex-wrap:wrap}.brand{font-size:24px;font-weight:800}.brand span{color:#4f46e5}.sub{color:#64748b;font-size:13px;margin-top:4px}main{max-width:1080px;margin:34px auto;padding:0 22px}.heading{display:flex;justify-content:space-between;align-items:end;gap:15px;flex-wrap:wrap;margin-bottom:20px}h1{margin:0 0 7px;font-size:28px}.muted{color:#64748b;font-size:14px}.btn{display:inline-flex;align-items:center;justify-content:center;text-decoration:none;border:0;border-radius:9px;padding:10px 13px;background:#eef2ff;color:#3730a3;font-weight:650;font-size:13px;cursor:pointer}.primary{background:#4f46e5;color:white}.danger{background:#fff1f2;color:#be123c}.card{background:white;border:1px solid #e5eaf1;border-radius:15px;overflow:hidden;box-shadow:0 8px 24px #17203308}.alert{margin-bottom:15px;background:#ecfdf5;color:#047857;border-radius:10px;padding:12px 15px}.alert.error{background:#fff7ed;color:#9a3412}.summary{display:flex;gap:12px;padding:18px;border-bottom:1px solid #edf0f5;align-items:center;justify-content:space-between}.count{font-size:13px;color:#64748b}table{width:100%;border-collapse:collapse}th,td{padding:16px 18px;text-align:left;border-bottom:1px solid #edf0f5}th{background:#fafbfe;color:#64748b;font-size:12px;text-transform:uppercase;letter-spacing:.05em}tbody tr:last-child td{border-bottom:0}.actions{display:flex;gap:7px;flex-wrap:wrap}.money{font-variant-numeric:tabular-nums;font-weight:650}.empty{text-align:center;padding:46px;color:#64748b}.foot{padding:14px 18px;color:#64748b;font-size:12px;border-top:1px solid #edf0f5}@media(max-width:620px){main{padding:0 12px;margin:22px auto}.nav{padding:16px 12px}th,td{padding:12px 9px}.hide-mobile{display:none}}
</style></head><body>
<header class="top"><div class="nav"><div><div class="brand">Product<span>Manager</span></div><div class="sub">Hệ thống quản lý sản phẩm · JSP & Servlet</div></div><a class="btn primary" href="<%=request.getContextPath()%>/product-form.jsp">＋ Thêm sản phẩm</a></div></header>
<main><div class="heading"><div><h1>Danh sách sản phẩm</h1><div class="muted">Theo dõi thông tin và số lượng sản phẩm trong kho.</div></div></div>
<% if(error!=null){%><div class="alert error"><%=esc(error)%></div><%}%>
<% if(request.getParameter("message")!=null){%><div class="alert"><%=esc(request.getParameter("message"))%></div><%}%>
<section class="card"><div class="summary"><strong>Sản phẩm trong hệ thống</strong><span class="count">Tổng: <strong><%=products==null?0:products.size()%></strong> sản phẩm</span></div>
<%if(products==null||products.isEmpty()){%><div class="empty"><div style="font-size:36px">▤</div><strong>Chưa có sản phẩm</strong><p>Thêm sản phẩm đầu tiên để bắt đầu quản lý.</p><a class="btn primary" href="<%=request.getContextPath()%>/product-form.jsp">Thêm sản phẩm</a></div><%}else{%>
<div style="overflow-x:auto"><table><thead><tr><th>ID</th><th>Tên sản phẩm</th><th>Giá (VNĐ)</th><th>Số lượng</th><th>Thao tác</th></tr></thead><tbody>
<%for(Product p:products){%><tr><td>#<%=p.getId()%></td><td><strong><%=esc(p.getName())%></strong></td><td class="money"><%=p.getPrice().toPlainString()%></td><td><%=p.getQuantity()%></td><td><div class="actions"><a class="btn" href="<%=request.getContextPath()%>/products?action=view&id=<%=p.getId()%>">Xem</a><a class="btn" href="<%=request.getContextPath()%>/products?action=edit&id=<%=p.getId()%>">Sửa</a><form method="post" action="<%=request.getContextPath()%>/products" onsubmit="return confirm('Bạn chắc chắn muốn xóa sản phẩm này?')"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="<%=p.getId()%>"><button class="btn danger" type="submit">Xóa</button></form></div></td></tr><%}%>
</tbody></table></div><%}%><div class="foot">Java Servlet · JSP · JDBC · MySQL</div></section></main></body></html>
