<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>List of users</title>
</head>
<body>
<a href="/">Home</a>
<p>FirstName: ${user.getFirstName()}</p>
<p>LastName: ${user.getLastName()}</p>
<p>Email: ${user.getEmail()}</p>

<a href="/user/tickets/${user.getId()}">Purchased Tickets </a>

</body>
</html>