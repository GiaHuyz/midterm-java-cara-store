$(document).ready(function () {
    var urlParams = new URLSearchParams(window.location.search);
    var id = urlParams.get("id");
    var detailsArray = [];

    $.ajax({
        url: "http://localhost:8080/api/products/details/" + id,
        type: "GET",
        contentType: "application/json",
        success: function (resp) {
            if (resp) {
                var product = resp.data;
                var singleProDetails = $('.single-pro-details');

                var smallImg = '';
                $.each(product.images, function (index, value) {
                    smallImg += `<div class="small-img-col">
                                    <img src="http://localhost:8080/api/products/file/${value}" width="100%" class="small-img">
                                </div>`;
                });
                var imageDetails = `
                                    <img src="http://localhost:8080/api/products/file/${product.images[0]}" width="100%" id="MainImg">
                                    <div class="small-img-group d-flex justify-content-between mt-2" style="margin: 0 -5px;">
                                        ${smallImg}
                                    </div>`;

                $('.single-pro-image').append(imageDetails);

                var productDetails = `<h6 class="mt-3">Home / ${product.category} </h6>
                                    <h4 class="pb-0 product-name">${product.productName}</h4>
                                    <p>${product.brand}</p>
                                    <h2>$ ${product.price}</h2>`;

                singleProDetails.append(productDetails);


                var uniqueSizesSet = new Set();
                var uniqueColorsSet = new Set();

                $.each(product.details, function (index, value) {
                    detailsArray.push(value);
                    uniqueSizesSet.add(value.sizes);
                    uniqueColorsSet.add(value.colors);
                });

                var size = '';
                $.each(Array.from(uniqueSizesSet), function (index, value) {
                    size += `<option value="${value}">${value}</option>`;
                });
                var sizes = `<div id="size" class="d-flex align-items-center">
                                <span>Size: </span>
                                <select name="size" id="size-select">
                                    ${size}
                                </select>
                            </div>`;
                singleProDetails.append(sizes);

                var colors = `<div id="color" class="d-flex align-items-center">
                                <span>Color: </span>
                                <ul class="d-flex p-0 list-unstyled p-3" style="margin-bottom: 5px;">
                                    
                                </ul>
                            </div>
                            <input type="number" value="1" min="1">
                            <button id="add-to-cart" class="normal">Add To Cart</button>
                            <div id="error-color" class="d-none alert alert-danger mt-3"></div>
                            <h4>Product Details</h4>`;
                singleProDetails.append(colors);

                var desc = `<span>${product.description}</span>`;
                singleProDetails.append(desc);
                showColorsForSelectedSize($("#size-select").val());
            }
        },
        error: function (xhr, status, error) {
            $("#error").val(error);
        },
    });

    function findDetailIdByColorsAndSizes(details, sizes, colors) {
        for (var i = 0; i < details.length; i++) {
            var detail = details[i];
            if (detail.sizes === sizes && detail.colors === colors) {
                return detail.detailId;
            }
        }
        return null;
    }

    function addToCart() {
        var colorSpan = $("#color li span");
        if (!colorSpan.hasClass("active")) {
            $("#error-color").removeClass("d-none alert-success").addClass("alert-danger").text("Please select a color").fadeTo(2000, 500).slideUp(500, function () {
                $("#error-color").slideUp(500);
            });
            return;
        }

        var id = new URLSearchParams(window.location.search).get("id");
        var name = $(".single-pro-details .product-name").text();
        var img = $("#MainImg").attr("src");
        var price = $(".single-pro-details h2").text().replace("$ ", "");
        var quantity = $(".single-pro-details input[type='number']").val();
        var size = $(".single-pro-details select[name='size']").val();
        var color = $("#color li span.active").parent().css("background-color");
        color = color.replace(/\s/g, "");
        var detailId = findDetailIdByColorsAndSizes(detailsArray, size, color);

        var cartItem = {
            id: id,
            detailId: detailId,
            productName: name,
            img: img,
            price: price,
            quantity: quantity,
            size: size,
            color: color
        };

        var cartItems = localStorage.getItem("cart") ? JSON.parse(localStorage.getItem("cart")) : [];

        var existingIndex = -1;
        for (var i = 0; i < cartItems.length; i++) {
            if (cartItems[i].id == cartItem.id && cartItems[i].size == cartItem.size && cartItems[i].color == cartItem.color) {
                existingIndex = i;
                break;
            }
        }

        if (existingIndex != -1) {
            cartItems[existingIndex].quantity = parseInt(cartItems[existingIndex].quantity, 10)
            cartItems[existingIndex].quantity += parseInt(cartItem.quantity, 10);
        } else {
            cartItems.push(cartItem);
        }
        localStorage.setItem("cart", JSON.stringify(cartItems));
        $("#error-color").removeClass("d-none alert-danger").addClass("alert-success").text("The product has been added to cart.").fadeTo(2000, 500).slideUp(500, function () {
            $("#error-color").slideUp(500);
        });
    }

    function showColorsForSelectedSize(selectedSize) {
        var detailsForSelectedSize = detailsArray.filter(function (detail) {
            return detail.sizes === selectedSize;
        });

        var colorList = '';
        $.each(detailsForSelectedSize, function (index, value) {
            colorList += `<li tabindex="${index + 1}" style="background-color: ${value.colors}; border: 1px solid black;">  
                            <span>✓</span>
                        </li>`;
        });

        $("#color ul").html(colorList);
    }

    $(document).on("click", "#add-to-cart", function () {
        var colorSpan = $("#color li span");
        if (!colorSpan.hasClass("active")) {
            $("#error-color").removeClass("d-none alert-success").addClass("alert-danger").text("Please select a color").fadeTo(2000, 500).slideUp(500, function () {
                $("#error-color").slideUp(500);
            });
            return;
        }

        var token = localStorage.getItem("token");
        var productId = new URLSearchParams(window.location.search).get("id");
        var size = $(".single-pro-details select[name='size']").val();
        var color = $("#color li span.active").parent().css("background-color");
        color = color.replace(/\s/g, "");
        var detailId = findDetailIdByColorsAndSizes(detailsArray, size, color);
        var quantity = $(".single-pro-details input[type='number']").val();
        var price = $(".single-pro-details h2").text().replace("$ ", "");

        var cartItem = [{
            productId: productId,
            detailId: detailId,
            quantity: quantity,
            price: price
        }];

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/api/carts',
            contentType: 'application/json',
            headers: {
                'Authorization': 'Bearer ' + token
            },
            data: JSON.stringify(cartItem),
            success: function (response) {
                if (response.success) {
                    $("#error-color").removeClass("d-none alert-danger").addClass("alert-success").text("Sản phẩm đã được thêm vào giỏ hàng!").fadeTo(2000, 500).slideUp(500, function () {
                        $("#error-color").slideUp(500);
                    });
                }
            },
            error: function (xhr) {
                if (xhr.status === 401) {
                    addToCart();
                }
            }
        });
    });

    $(document).on("click", ".small-img", function () {
        $("#MainImg").attr('src', $(this).attr('src'));
    });

    $(document).on("click", "#color ul li", function () {
        var colorLi = $("#color li span");
        colorLi.removeClass("active");
        $(this).find("span").addClass("active");
    });

    $(document).on("change", "#size-select", function () {
        var selectedSize = $(this).val();
        showColorsForSelectedSize(selectedSize);
    });
});
