function parseJwt (token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}

$(document).ready(function () {
    $("#btn-signin").click(function () {
        $('#error').addClass('d-none');

        var username = $("#username").val();
        var password = $("#password").val();
        $.ajax({
            url: "http://localhost:8080/auth/login",
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                username: username,
                password: password
            }),
            success: function (response) {
                if (response.success) {
                    var token = response.data
                    localStorage.setItem("token", token);
                    
                    if(parseJwt(token).roles === "ROLE_ADMIN") {
                        window.location.replace('/admin.html');
                    } else {
                        var cartItems = JSON.parse(localStorage.getItem("cart"));
                        if (cartItems && cartItems.length > 0) {
                            var cartRequest = cartItems.map(function (cartItem) {
                                return {
                                    productId: cartItem.id,
                                    detailId: cartItem.detailId,
                                    quantity: cartItem.quantity,
                                    price: cartItem.price
                                };
                            });
                            sendAddCartRequest(token, cartRequest);
                        }
                        window.location.replace('/index.html');
                    }
                } else {
                    $('#error').removeClass('d-none').text(response.description);
                }
            },
            error: function (xhr) {
                if (xhr.status === 401) {
                    $('#error').removeClass('d-none').text('Invalid username or password.');
                } else {
                    $('#error').removeClass('d-none').text('An error occurred: ' + xhr.status + ' ' + xhr.responseText);
                }
            }
        });
    });

    function sendAddCartRequest(token, cartItems) {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/api/carts',
            contentType: 'application/json',
            headers: {
                'Authorization': 'Bearer ' + token
            },
            data: JSON.stringify(cartItems),
            success: function (addCartResponse) {
                if (addCartResponse.success) {
                    localStorage.removeItem("cart");
                }
            }
        });
    }
});
