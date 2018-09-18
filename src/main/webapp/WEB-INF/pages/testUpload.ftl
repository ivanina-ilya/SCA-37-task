<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form name="testUpload"  action="/testUpload" method="POST">
    <input type="hidden" name="upload_f" value="test">
    <input type="submit" />
</form>


<hr/>

<form method="POST" action="/testUploadFile" enctype="multipart/form-data">
    <input name="file" type="file">
    <input type="submit" />
</form>

</body>
</html>