$(document).ready(function () {
    var queryParams = loadFiltersFromURL();
    var pageParams = new URLSearchParams(window.location.search);
    var curPage = pageParams.get('page')

    if (queryParams.toString()) {
        loadProducts(queryParams.toString(), curPage);
    } else {
        loadProducts(null, 1, '');
    }

    function loadFiltersFromURL() {
        var queryParams = new URLSearchParams(window.location.search);

        var sort = queryParams.get('sort');
        var minPrice = queryParams.get('minPrice');
        var maxPrice = queryParams.get('maxPrice');
        var size = queryParams.get('size');

        if (size) $('#size').val(size).change();
        if (sort) $('#sort').val(sort).change();
        if (size) $('#size').val(size).change();
        if (minPrice || maxPrice) {
            if (minPrice === '50' && !maxPrice) {
                $('#price').val('>50').change();
            } else {
                $('#price').val(minPrice + '-' + maxPrice).change();
            }
        }

        return queryParams;
    }


    $('#filter').click(function (event) {
        event.preventDefault();

        var brand = $('#brand').val();
        var category = $('#categories').val();
        var sort = $('#sort').val();
        var priceRange = $('#price').val();
        var size = $('#size').val();

        var queryParams = new URLSearchParams();

        if (brand && brand !== 'all') queryParams.set('brand', brand);
        if (category && category !== 'all') queryParams.set('category', category);
        if (size && size !== 'all') queryParams.set('size', size);
        if (sort && sort !== 'none') queryParams.set('sort', sort);

        if (priceRange && priceRange !== 'all') {
            if (priceRange === '>50') {
                queryParams.set('minPrice', '50');
            } else {
                var priceParts = priceRange.split('-');
                queryParams.set('minPrice', priceParts[0]);
                queryParams.set('maxPrice', priceParts[1]);
            }
        }

        var newUrl = window.location.protocol + "//" + window.location.host + window.location.pathname + '?' + queryParams.toString() + '&page=1';
        window.history.pushState({ path: newUrl }, '', newUrl);

        loadProducts(queryParams.toString(), 1);
    });

    $(document).on("click", ".pro", function () {
        var id = $(this).data("id");
        window.location.href = "sproduct.html?id=" + id;
    });

    function loadProducts(data, page) {
        $.ajax({
            url: 'http://localhost:8080/api/products',
            type: 'GET',
            contentType: 'application/json',
            data: data + '&page=' + page,
            success: function (resp) {
                if (resp) {
                    $("#product").empty();
                    $.each(resp.data, function (index, value) {
                        var html = `<div class="col-sm-12 col-md-6 col-lg-6 col-xl-3">
                                        <div class="pro" data-id="${value.productId}">
                                            <img src="http://localhost:8080/api/products/file/${value.mainImage}" alt="">
                                            <div class="des">
                                                <span>${value.brand}</span>
                                                <h5>${value.productName}</h5>
                                                <div class="star">
                                                    <i class="fas fa-star"></i>
                                                    <i class="fas fa-star"></i>
                                                    <i class="fas fa-star"></i>
                                                    <i class="fas fa-star"></i>
                                                    <i class="fas fa-star"></i>
                                                </div>
                                                <h4>$${value.price}</h4>
                                            </div>
                                            <a href="#"><i class="fal fa-shopping-cart cart"></i></a>
                                        </div>
                                    </div>`;
                        $("#product").append(html)
                    });
                    renderPagination(resp.totalPages);
                }
            },
            error: function (xhr, status, error) {
                alert(xhr.responseText);
            }
        });
    }


    function renderPagination(totalPages) {
        $('#pagination').empty();
        var paginationHtml = '';
        var currentSearchParams = new URLSearchParams(window.location.search);

        for (var i = 1; i <= totalPages; i++) {
            currentSearchParams.set('page', i);
            paginationHtml += `<a href="#" class="p-link" data-page="${i}" data-query="${currentSearchParams.toString()}">${i}</a>`;
        }

        $('#pagination').append(paginationHtml);
    }

    $(document).on('click', '.p-link', function (e) {
        e.preventDefault();

        var page = $(this).data('page');
        var query = $(this).data('query');

        var queryParams = $(this).data('query');
        var newUrl = window.location.protocol + "//" + window.location.host + window.location.pathname + '?' + queryParams;
        window.history.pushState({ path: newUrl }, '', newUrl);

        loadProducts(query, page, '');
    });

    $(document).on('click', '#search-button', function (e) {
        e.preventDefault();

        var keyword = $('#keyword').val();

        var queryParams = new URLSearchParams(window.location.search);

        queryParams.set('keyword', keyword); 
        queryParams.set('page', 1); 

        var newUrl = window.location.protocol + "//" + window.location.host + window.location.pathname + '?' + queryParams.toString();
        window.history.pushState({ path: newUrl }, '', newUrl);

        console.log(queryParams.toString());

        loadProducts(queryParams.toString(), 1);
    });
});