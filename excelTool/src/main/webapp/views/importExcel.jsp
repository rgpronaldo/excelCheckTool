<%@ page language="java" pageEncoding="UTF-8"%>
<form action="<%=request.getContextPath()%>/user/add" method="POST" enctype="multipart/form-data">
           邮箱所在的列数：<input type="text" name="emailColInt"><br/>
   excel列表: <input type="file" name="myfile"/><br/>  
	<input type="submit" value="上传检查"/>
</form>