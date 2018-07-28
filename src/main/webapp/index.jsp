<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<body>
<h2>Hello World!</h2>
<dir>firstProject上传文件测试</dir>

<form name="from" action="/firstProject/activity/uploadActivityPicture.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_activity_picture_file"/>
    <input type="submit" value="firstProject上传文件测试"/>
</form>
</body>
</html>
