$(document).ready(function () {
    var token = localStorage.getItem('token');

    loadUserOrders();
    
    function loadUserOrders() {
        if (token) {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/api/orders',
                contentType: 'application/json',
                headers: {
                    'Authorization': 'Bearer ' + token
                },
                success: function (resp) {
                    if (resp.success) {
                        $.each(resp.data, function (index, value) {
                            html = `<tr>
                                        <td>${value.orderId}</td>
                                        <td>${value.date}</td>
                                        <td>$${value.totalPrice.toFixed(2)}</td>
                                        <td><button class="view-order btn btn-primary" data-id="${value.orderId}" data-status="view">View</button></td>
                                    </tr>`;

                            $('#order').append(html);

                            var detailHtml = ''
                            $.each(value.orderDetails, function (index, detail) {
                                detailHtml += `<tr>
                                                    <td><img src="http://localhost:8080/api/products/file/${detail.image}" alt=""></td>
                                                    <td>${detail.productName}</td>
                                                    <td><input style="text-align: center;" type="number" readonly value="${detail.quantity}"></td>
                                                    <td><span class="rounded-circle" style="background-color: ${detail.color}"></span></td>
                                                    <td>${detail.size}</td>
                                                    <td>$ ${detail.price}</td>
                                                    <td>$ ${detail.price * detail.quantity}</td>
                                                </tr>`;
                            });

                            var trDetail = `<tr class="d-none" data-id="${value.orderId}">
                                            <td colspan="4" class="p-0">
                                                <div id="order-detail" style="overflow-x: auto;">
                                                    <table id="cart-table">
                                                        <thead>
                                                            <tr>
                                                                <td>Image</td>
                                                                <td>Product</td>
                                                                <td>Quantity</td>
                                                                <td>Color</td>
                                                                <td>Size</td>
                                                                <td>Price</td>
                                                                <td>Subtotal</td>
                                                            </tr>
                                                        </thead>
                                                        <tbody">
                                                            ${detailHtml}
                                                        </tbody>
                                                        </thead>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>`
                            $('#order').append(trDetail);
                        });
                    }
                },
                error: function (xhr) {
                    if (xhr.status === 401) {
                        localStorage.removeItem('token');
                        window.location.replace('/login.html');
                    }
                }
            });
        }
    }

    var openedButton = null;
    $(document).on('click', ".view-order", function () {
        if (openedButton != null && $(this).data("status") !== "close") {
            openedButton.closest('tr').next('tr').addClass("d-none");
    
            openedButton.removeClass("btn-secondary").addClass("btn-primary");
            openedButton.text("View");
            openedButton.data("status", "view");
        }
    
        openedButton = $(this);
        var status = openedButton.data("status");
    
        if (status == "view") {
            $(this).closest('tr').next('tr').removeClass("d-none");
    
            openedButton.removeClass("btn-primary").addClass("btn-secondary");
            openedButton.text("Close");
            openedButton.data("status", "close");
        } else {
            $(this).closest('tr').next('tr').addClass("d-none");

            openedButton.removeClass("btn-secondary").addClass("btn-primary");
            openedButton.text("View");
            openedButton.data("status", "view");
        }
    });

});