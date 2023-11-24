$(document).ready(function () {
    var queryParams = new URLSearchParams(window.location.search);
    var brand = queryParams.get('brand');
    var category = queryParams.get('category');

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/categories',
        contentType: 'application/json',
        success: function (resp) {
            if (resp.success) {
                $.each(resp.data, function (index, value) {
                    var html = `<option value="${value.id}">${value.name}</option>`;
                    $("#categories").append(html)
                });
                if (category) {
                    $('#categories').val(category).change();
                }             
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
                    $("#brand").append(html)
                });
                if (brand) {
                    $('#brand').val(brand).change();
                } 
            }
        }
    });
});