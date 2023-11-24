function parseJwt (token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}

var token = localStorage.getItem("token");

if(parseJwt(token).roles !== "ROLE_ADMIN") {
    window.location.replace('/index.html');
}

var currentTrProduct;
function editProduct(productId, element) {
    loadProductData(productId);
    currentTrProduct = $(element).closest('tr');
}

function deleteProduct(productId, element) {
    $('#detele-product-id').val(productId);
    currentTrProduct = $(element).closest('tr');
}

function loadProductData(productId) {
    $.ajax({
        url: "http://localhost:8080/api/products/details/" + productId,
        type: 'GET',
        success: function (response) {
            var data = response.data;

            $('#productId').val(data.productId);
            $('#editProductName').val(data.productName);
            $("#categories option").filter(function () {
                return $(this).text() == data.category;
            }).prop('selected', true);
            $("#brand option").filter(function () {
                return $(this).text() == data.brand;
            }).prop('selected', true);
            $('#editDescription').val(data.description);
            $('#editPrice').val(data.price);
            $('#images').empty();
            data.images.forEach(function (image) {
                var images = `<div class="row mb-2">
                                <div class="col">
                                    <img data-image="${image}" style="height: 150px; width: 100%;" src="http://localhost:8080/api/products/file/${image}" alt="Product Image"/>
                                </div>
                                <div class="col d-flex flex-column">
                                    <input class="form-control formFile" type="file">
                                    <button data-bs-toggle="modal" data-bs-target="#deteleImageModal" class="btn btn-danger mt-2 btn-delete-image align-item-center" data-image="${image}">Delete</button>
                                </div>
                            </div>`;
                $('#images').append(images);
            });

            $('#productDetailsSection').empty();
            data.details.forEach(function (detail) {
                var detailHtml = `<div class="row mb-3">
                                    <div class="col d-flex align-items-center">
                                    <p style="margin: 0">Color: </p>
                                    <span class="data-colors" data-colors="${detail.colors}" style="display:inline-block; height: 20px; width: 20px; border: 1px solid black; border-radius: 20px; background-color: ${detail.colors}"></span>
                                    </div>
                                    <div data-sizes="${detail.sizes}" class="col d-flex align-items-center data-sizes">Size: ${detail.sizes}</div>
                                    <div class="col d-flex align-items-center">
                                        <button data-bs-toggle="modal" data-bs-target="#editDetailModal" class="btn btn-primary btn-edit-detail" data-id="${detail.detailId}">Edit</button>
                                    </div>
                                    <div class="col d-flex align-items-center">
                                        <button data-bs-toggle="modal" data-bs-target="#deteleDetailModal" class="btn btn-danger btn-delete-detail" data-id="${detail.detailId}">Delete</button>
                                    </div>
                                </div>`;
                $('#productDetailsSection').append(detailHtml);
            });

        },
        error: function (xhr) {
            if(xhr.status === 401) {
                window.location.replace('/index.html');
            }
        }
    });
}

$(document).on('click', '#show-detail', function (e) {
    if($('#productDetailsSection').hasClass('d-none')) {
        $('#productDetailsSection').removeClass('d-none');
    } else {
        $('#productDetailsSection').addClass('d-none');
    }
});

var currentEditingDiv;
$(document).on('click', '.btn-edit-detail', function (e) {
    e.preventDefault();
    e.stopPropagation();

    var rowDiv = $(this).closest('.row');
    var color = rgbToHex(rowDiv.find('.data-colors').data('colors'));
    var sizes = rowDiv.find('.data-sizes').data('sizes');

    $('#edit-color').val(color);
    $("#edit-sizes option").filter(function () {
        return $(this).text() === sizes;
    }).prop('selected', true);

    $('#detailId').val($(this).data('id'));
    currentEditingDiv = rowDiv;
});

var currentDeletingDiv;
$(document).on('click', '.btn-delete-detail', function (e) {
    e.preventDefault();
    e.stopPropagation();

    var rowDiv = $(this).closest('.row');

    $('#detele-detailId').val($(this).data('id'));
    currentDeletingDiv = rowDiv;
});

$(document).on('click', '#delete-detail', function (e) {
    e.preventDefault();
    e.stopPropagation();
    var id = $('#detele-detailId').val();

    $.ajax({
        url: 'http://localhost:8080/api/products/details/' + id,
        type: 'DELETE',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (response) {
            currentDeletingDiv.remove();
            $('#deteleDetailModal').modal('toggle');
        },
        error: function (xhr, status, error) {
            alert("An error occurred: " + error);
        }
    });
})

$(document).on('click', '#save-edit-detail', function (e) {
    e.preventDefault();
    e.stopPropagation();

    var color = hexToRgb($('#edit-color').val());
    var sizes = $('#edit-sizes').val();
    var detailId = $('#detailId').val();

    var detailData = {
        detailId: detailId,
        color: color,
        size: sizes
    };

    $.ajax({
        url: 'http://localhost:8080/api/products/details',
        type: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        data: JSON.stringify(detailData),
        success: function (response) {
            var detail = response.data;
            var detailHtml = `<div class="col d-flex align-items-center">
                                <p style="margin: 0">Color: </p>
                                <span class="data-colors" data-colors="${detail.colors}" style="display:inline-block; height: 20px; width: 20px; border: 1px solid black; border-radius: 20px; background-color: ${detail.colors}"></span>
                                </div>
                                <div data-sizes="${detail.sizes}" class="col d-flex align-items-center data-sizes">Size: ${detail.sizes}</div>
                                <div class="col d-flex align-items-center">
                                    <button data-bs-toggle="modal" data-bs-target="#editDetailModal" class="btn btn-primary btn-edit-detail" data-id="${detail.detailId}">Edit</button>
                                </div>
                                <div class="col d-flex align-items-center">
                                    <button class="btn btn-danger">Delete</button>
                            </div>`;
            currentEditingDiv.html(detailHtml);
            $('#editDetailModal').modal('toggle');
        },
        error: function (xhr, status, error) {
            alert("An error occurred: " + xhr.responseText);
        }
    });
});

$(document).on('click', '#edit-product', function (e) {
    e.preventDefault();
    e.stopPropagation();

    var id = $('#productId').val();
    var name = $('#editProductName').val();
    var category = $('#categories').val();
    var brand = $('#brand').val();
    var price = $('#editPrice').val();
    var description = $('#editDescription').val();

    var productData = {
        id: id,
        name: name,
        categoryId: category,
        brandId: brand,
        price: price,
        description: description
    };

    $.ajax({
        url: 'http://localhost:8080/api/products',
        type: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        data: JSON.stringify(productData),
        success: function (response) {
            var data = response.data;
            var table = $('#productTable').DataTable();
            var row = table.row(currentTrProduct);
            row.data({
                productId: data.productId,
                productName: data.productName,
                description: data.description,
                price: data.price,
                categoryName: data.category,
                brandName: data.brand,
            }).draw();
            $('#editModal').modal('toggle');
        },
        error: function (xhr, status, error) {
            alert("An error occurred: " + xhr.responseJSON.description);
        }
    });
});

$(document).on('change', '.formFile', function () {
    var file = this.files[0]; // Lấy file được chọn
    var img = $(this)
    var oldFile = $(this).closest('.row').find('img').data('image');
    var productId = $('#productId').val();

    if (file) {
        var formData = new FormData();
        formData.append('filename', file); 

        $.ajax({
            url: `http://localhost:8080/api/products/images/${productId}/${oldFile}`,
            type: 'POST',
            headers: {
                'Authorization': 'Bearer ' + token
            },
            data: formData,
            contentType: false, 
            processData: false,
            success: function (response) {
                var newUrl = `http://localhost:8080/api/products/file/${file.name}`;
                $(img).closest('.row').find('img').attr('src', newUrl).data('image', file.name);
            },
            error: function (xhr, status, error) {
                alert("An error occurred: " + xhr.responseText);
            }
        });
    }
});

$(document).on('change', '#addImage', function () {
    var file = this.files[0]; // Lấy file được chọn
    var productId = $('#productId').val();

    if (file) {
        var formData = new FormData();
        formData.append('filename', file); 

        $.ajax({
            url: `http://localhost:8080/api/products/images/${productId}`,
            type: 'POST',
            headers: {
                'Authorization': 'Bearer ' + token
            },
            data: formData,
            contentType: false, 
            processData: false,
            success: function (response) {
                var newUrl = `http://localhost:8080/api/products/file/${file.name}`;
                var html = `<div class="row mb-2">
                                <div class="col">
                                    <img data-image="${file.name}" style="height: 150px; width: 100%;" src="${newUrl}" alt="Product Image"/>
                                </div>
                                <div class="col d-flex flex-column">
                                    <input class="form-control formFile" type="file">
                                    <button data-bs-toggle="modal" data-bs-target="#deteleImageModal" class="btn btn-danger mt-2 btn-delete-image align-item-center" data-image="${file.name}">Delete</button>
                                </div>
                            </div>`;
                $('#images').append(html);
            },
            error: function (xhr, status, error) {
                alert("An error occurred: " + xhr.responseText);
            }
        });
    }
});

var currentDeletingImage;
$(document).on('click', '.btn-delete-image', function (e) {
    e.preventDefault();
    e.stopPropagation();

    var rowDiv = $(this).closest('.row');

    $('#detele-name-image').val($(this).data('image'));
    currentDeletingImage = rowDiv;
});

$(document).on('click', '#delete-image', function (e) {
    e.preventDefault();
    e.stopPropagation();
    
    var filename = $('#detele-name-image').val();
    var productId = $('#productId').val();

    $.ajax({
        url: `http://localhost:8080/api/products/${productId}/images/${filename}`,
        type: 'DELETE',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        contentType: 'application/json',
        success: function (response) {
            currentDeletingImage.remove();
            $('#deteleImageModal').modal('toggle');
        },
        error: function (xhr, status, error) {
            alert("An error occurred: " + xhr.responseJSON);
        }
    });
});

$(document).on('click', '#save-add-product', function (e) {
    e.preventDefault();
    e.stopPropagation();

    var name = $('#addProductName').val();
    var category = $('#addCategory').val();
    var brand = $('#addBrand').val();
    var price = $('#addPrice').val();
    var description = $('#addDescription').val();

    var productData = {
        name: name,
        categoryId: category,
        brandId: brand,
        price: price,
        description: description
    };

    $.ajax({
        url: 'http://localhost:8080/api/products',
        type: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        data: JSON.stringify(productData),
        success: function (response) {
            if(response.success) {
                var data = response.data;
                var table = $('#productTable').DataTable();
                table.row.add({
                    productId: data.productId,
                    productName: data.productName,
                    description: data.description,
                    price: data.price,
                    categoryName: data.category,
                    brandName: data.brand,
                }).draw();
                $('#addProductModal').modal('toggle');
            }
            else {
                console.log(response);
            }
        },
        error: function (xhr, status, error) {
            alert("An error occurred: " + xhr.responseJSON.description);
        }
    });
});


$(document).on('click', '#delete-product', function (e) {
    e.preventDefault();
    e.stopPropagation();

    var proId = $('#detele-product-id').val();

    $.ajax({
        url: 'http://localhost:8080/api/products/' + proId,
        type: 'DELETE',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (response) {
            if(response.success) {
                currentTrProduct.remove();
                $('#deleteProductModal').modal('toggle');
            }
            else {
                console.log(response);
            }
        },
        error: function (xhr, status, error) {
            alert("An error occurred: " + xhr.responseJSON.description);
        }
    });
});



function hexToRgb(hex) {
    var shorthandRegex = /^#?([a-f\d])([a-f\d])([a-f\d])$/i;
    hex = hex.replace(shorthandRegex, function (m, r, g, b) {
        return r + r + g + g + b + b;
    });

    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    return result ? 'rgb(' + parseInt(result[1], 16) + ', ' + parseInt(result[2], 16) + ', ' + parseInt(result[3], 16) + ')' : null;
}

function rgbToHex(rgb) {
    // Chuyển đổi từ dạng rgb(x, y, z) sang mảng [x, y, z]
    var rgbValues = rgb.match(/\d+/g).map(Number);

    // Chuyển đổi từng thành phần RGB sang Hex và ghép lại
    var hex = rgbValues.map(function (val) {
        var hexPart = val.toString(16);
        return hexPart.length == 1 ? "0" + hexPart : hexPart;
    }).join('');

    return "#" + hex;
}

$(document).ready(function () {
    
    $('#productTable').DataTable({
        "processing": true,
        "serverSide": true,
        "info": false,
        "sort": false,
        "ajax": {
            "url": "http://localhost:8080/api/products/admin",
            "headers": {
                'Authorization': 'Bearer ' + token
            },
            "dataSrc": function (json) {
                json.recordsTotal = json.data.totalElements;
                json.recordsFiltered = json.data.totalElements;
                return json.data.content;
            }
        },
        "pageLength": 5,
        "lengthMenu": [5, 10, 15, 20],
        "columns": [
            { "data": "productId" },
            { "data": "productName" },
            { "data": "description" },
            { "data": "price" },
            { "data": "categoryName" },
            { "data": "brandName" },
            {
                "data": null,
                "defaultContent": "",
                "render": function (data, type, row) {
                    return `<div class="d-flex">
                    <button class="btn btn-primary mx-2" data-bs-toggle="modal" data-bs-target="#editModal" onclick="editProduct(${row.productId}, this)">Edit</button>
                    <button class="btn btn-danger mx-2" data-bs-toggle="modal" data-bs-target="#deleteProductModal" onclick="deleteProduct(${row.productId}, this)">Delete</button>
                    </div>`;
                }
            }
        ]
    });

    $('#save-add-detail').click(function () {
        var detailData = {
            productId: $('#productId').val(),
            color: hexToRgb($('#color').val()),
            size: $('#sizes').val()
        };

        $.ajax({
            url: 'http://localhost:8080/api/products/details',
            type: 'POST',
            headers: {
                'Authorization': 'Bearer ' + token
            },
            contentType: 'application/json',
            data: JSON.stringify(detailData),
            success: function (response) {
                var detail = response.data;
                var detailHtml = `<div class="row mb-3">
                                    <div class="col d-flex align-items-center">
                                    <p style="margin: 0">Color: </p>
                                    <span class="data-colors" data-colors="${detail.colors}" style="display:inline-block; height: 20px; width: 20px; border: 1px solid black; border-radius: 20px; background-color: ${detail.colors}"></span>
                                    </div>
                                    <div data-sizes="${detail.sizes}" class="col d-flex align-items-center data-sizes">Size: ${detail.sizes}</div>
                                    <div class="col d-flex align-items-center">
                                        <button data-bs-toggle="modal" data-bs-target="#editDetailModal" class="btn btn-primary btn-edit-detail" data-id="${detail.detailId}">Edit</button>
                                    </div>
                                    <div class="col d-flex align-items-center">
                                        <button data-bs-toggle="modal" data-bs-target="#deteleDetailModal" class="btn btn-danger btn-delete-detail" data-id="${detail.detailId}">Delete</button>
                                    </div>
                                </div>`;
                $('#productDetailsSection').append(detailHtml);
            },
            error: function (xhr, status, error) {
                alert("An error occurred: " + xhr.responseJSON);
            }
        });
    });



    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/categories',
        contentType: 'application/json',
        success: function (resp) {
            if (resp.success) {
                $.each(resp.data, function (index, value) {
                    var html = `<option value="${value.id}">${value.name}</option>`;
                    $("#categories").append(html);
                    $("#addCategory").append(html);
                });
            }
        }
    });

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/brands',
        contentType: 'application/json',
        success: function (resp) {
            if (resp.success) {
                $.each(resp.data, function (index, value) {
                    var html = `<option value="${value.id}">${value.name}</option>`;
                    $("#brand").append(html);
                    $("#addBrand").append(html);
                });
            }
        }
    });


});