<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h3>Enter Username and Password</h3><form name='f' action='/login' method='POST'>
    <div style=" display: none">
    <pre>
        <#list .data_model?keys as key>
            ${key}<br/>
        </#list>
    </pre>
    </div>


    <table>
        <tr><td>User:</td><td><input type='text' name='username' value=''></td></tr>
        <tr><td>Password:</td><td><input type='password' name='password'/></td></tr>
        <tr><td><input type='checkbox' name='remember-me'/></td><td>Remember me.</td></tr>
        <tr><td colspan='2'><input name="submit" type="submit" value="Login"/></td></tr>
        <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />
    </table>
</body>
</html>