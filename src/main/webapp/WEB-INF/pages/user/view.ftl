<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>List of users</title>
</head>
<body>

<table class="datatable">
    <tr>
        <th>Email</th>
        <th>Name</th>
    </tr>
    <#list model["usersList"] as user >
        <tr>
            <td>${user.email}</td>
            <td>${user.firstName} ${user.lastName}</td>
        </tr>
    </#list>
</table>

</body>
</html>