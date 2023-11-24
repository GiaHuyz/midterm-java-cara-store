function parseJwt (token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}

var token = localStorage.getItem("token");

if (token) {
    if(parseJwt(token).roles === "ROLE_ADMIN") {
        adminNavbar();
    } else {
        authNavbar();
    }
    logout();
} else {
    unAuthNavbar();
}

function authNavbar() {
    var html = `<li><a href="#">Logout</a></li>
                <li><a href='account.html'>Account</a></li>`;
    $("#navbar li:first-child").after(html);
}

function adminNavbar() {
    var html = `<li><a href="#">Logout</a></li>`;
    $("#navbar li:first-child").after(html);
}

function unAuthNavbar() {
    var html = `<li id="login-tag"><a href="login.html">Login</a></li>`;
    $("#navbar li:first-child").after(html);
}

function logout() {
    var logoutLink = $("#navbar li a:contains('Logout')");
    logoutLink.click(function () {
        localStorage.removeItem("token");
        window.location.replace('/login.html');
    });
}
