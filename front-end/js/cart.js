$(document).ready(function () {

    var token = localStorage.getItem("token");

    $("#checkout").on("click", function () {
        if (token) {
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/api/orders',
                contentType: 'application/json',
                headers: {
                    'Authorization': 'Bearer ' + token
                },
                success: function (resp) {
                    if (resp.success) {
                        window.location.replace('/account.html');
                    }
                },
                error: function (xhr) {
                    if (xhr.status === 401) {
                        localStorage.removeItem('token');
                        window.location.replace('/login.html');
                    } else {
                        alert(xhr.responseJSON.description);
                    }
                }
            });
        } else {
            window.location.replace('/login.html');
        }
    });


    function loadCartItems() {
        var cartItems = localStorage.getItem("cart") ? JSON.parse(localStorage.getItem("cart")) : [];
        var tbody = $("#cart table tbody");
        tbody.empty();
        var total = 0
        $.each(cartItems, function (index, cartItem) {
            var id = cartItem.id;
            var name = cartItem.productName;
            var img = cartItem.img;
            var price = cartItem.price;
            var quantity = cartItem.quantity;
            var size = cartItem.size;
            var color = cartItem.color;
            var subtotal = price * quantity;
            total += subtotal;
            var tr = `<tr>
                        <td><img src="${img}" alt=""></td>
                        <td>${name}</td>
                        <td><input type="number" min="1" value="${quantity}"></td>
                        <td><span class="rounded-circle" style="background-color: ${color}"></span></td>
                        <td>${size}</td>
                        <td>$ ${price}</td>
                        <td>$ ${subtotal}</td>
                        <td><a class="btn-remove" href="#" data-id=${id}><i class="far fa-times-circle" style="font-size: 30px"></i></a></td>
                    </tr>`;
            tbody.append(tr);
        });
        $('#total').text(`$${total}`);
    }

    function loadCartItemsUser() {
        if (token) {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/api/carts',
                contentType: 'application/json',
                headers: {
                    'Authorization': 'Bearer ' + token
                },
                success: function (response) {
                    if (response.success) {
                        var cartItems = response.data
                        var tbody = $("#cart table tbody");
                        tbody.empty();

                        var total = 0;
                        $.each(cartItems, function (index, cartItem) {
                            var id = cartItem.id;
                            var name = cartItem.productName;
                            var img = cartItem.image;
                            var price = cartItem.price;
                            var quantity = cartItem.quantity;
                            var size = cartItem.size;
                            var color = cartItem.color;
                            var subtotal = price * quantity;
                            total += subtotal;

                            var tr = `<tr>
                                        <td><img src="http://localhost:8080/api/products/file/${img}" alt=""></td>
                                        <td>${name}</td>
                                        <td><input type="number" min="1" value="${quantity}"></td>
                                        <td><span class="rounded-circle" style="background-color: ${color}"></span></td>
                                        <td>${size}</td>
                                        <td>$ ${price}</td>
                                        <td data-subtotal="${subtotal}">$ ${subtotal}</td>
                                        <td><a class="btn-remove" data-id=${id} href="#"><i class="far fa-times-circle" style="font-size: 30px"></i></a></td>
                                    </tr>`;

                            tbody.append(tr);
                        });

                        $('#total').text(`$${total}`);
                    }
                },
                error: function (xhr) {
                    if (xhr.status === 401) {
                        localStorage.removeItem('token');
                        window.location.replace('/login.html');
                    }
                }
            });
        } else {
            loadCartItems();
        }
    }

    $(document).on('click', '.btn-remove', function (event) {
        event.preventDefault();
        var btn = $(this)
        if (btn.data('id')) {
            $.ajax({
                type: 'DELETE',
                url: 'http://localhost:8080/api/carts/' + btn.data('id'),
                contentType: 'application/json',
                headers: {
                    'Authorization': 'Bearer ' + token
                },
                success: function (response) {
                    if (response.success) {
                        btn.closest('tr').remove();
                        var subtotal = parseFloat($('#total').text().slice(1)) - btn.closest('tr').find('td[data-subtotal]').data('subtotal');
                        $('#total').text(`$${subtotal}`);
                    }
                },
                error: function (xhr) {
                    if (xhr.status === 401) {
                        btn.closest('tr').remove();
                        var cart = JSON.parse(localStorage.getItem('cart'));
                        var updatedCart = cart.filter(item => item.id !== btn.data('id').toString());
                        localStorage.setItem('cart', JSON.stringify(updatedCart));
                        cart = JSON.parse(localStorage.getItem('cart'));
                        var total = cart.reduce((acc, item) => acc + item.price * item.quantity, 0);
                        $('#total').text(`$${total.toFixed(2)}`);
                    }
                }
            });
        }
    });

    window.addEventListener('storage', function (e) {
        if (e.key === 'cart') {
            loadCartItems();
        }
    });

    if (window.location.pathname == "/cart.html") {
        loadCartItemsUser();
    }
});