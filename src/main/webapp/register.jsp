<form action="controller" method="post">
    <input type="hidden" name="action" value="register">

    Name: <input type="text" name="name"><br>
    Email: <input type="email" name="email"><br>
    Password: <input type="password" name="password"><br>

    Role:
    <select name="role">
        <option value="USER">User</option>
        <option value="SPONSOR">Sponsor</option>
        <option value="MAINTAINER">Maintainer</option>
    </select>

    <button type="submit">Register</button>
</form>
