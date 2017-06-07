<!DOCTYPE html>

<html lang="en">

<body>
<#list userList as user>

User: ${user.username}! <br>
Q:Why I like? <br>
A:${user.password}!
<br/>
</#list>
</body>

</html>