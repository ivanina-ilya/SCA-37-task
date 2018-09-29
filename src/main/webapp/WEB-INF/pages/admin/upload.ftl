<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Upload data</title>
</head>
<body>
<a href="/">Home</a>
<form method="post" enctype="multipart/form-data">
    <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />-->

    <p>Type of data for upload:
    <select name="type">
        <option value="users">Users</option>
        <option value="event">Event</option>
    </select>
    </p>
    <p>XML file: <input name="file" type="file"></p>
    <input type="submit" />

</form>

</body>
</html>