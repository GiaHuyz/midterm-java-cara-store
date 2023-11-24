$(document).ready(function () {
    var token = localStorage.getItem("token");
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/users',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (response) {
            if (response.success) {
                var data = response.data;
                $("#full-name").val(data.fullName);
                $("#email").val(data.username);
                $("#address").val(data.address);
                $("#phone").val(data.phone);
            }
        },
        error: function (xhr) {
            if (xhr.status === 401) {
                localStorage.removeItem('token');
                window.location.replace('/login.html');
            }
        }
    });

    $('#edit-profile').click(function (event) {
        event.preventDefault();
        var data = {
            fullName: $('#full-name').val(),
            address: $('#address').val(),
            phone: $('#phone').val()
        };
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/api/users',
            contentType: 'application/json',
            data: JSON.stringify(data),
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function (response) {
                if (response.success) {
                    var data = response.data;
                    $("#full-name").val(data.fullName);
                    $("#address").val(data.address);
                    $("#phone").val(data.phone);
                    $("#message").removeClass("d-none alert-danger").addClass("alert-success").text("Update Successful").fadeTo(2000, 500).slideUp(500, function () {
                        $("#message").slideUp(500);
                    });
                }
            },
            error: function (xhr, status, error) {
                $("#message").removeClass("d-none alert-success").addClass("alert-danger").text(xhr.responseJSON.description).fadeTo(2000, 500).slideUp(500, function () {
                    $("#message").slideUp(500);
                });
            }
        });
    });

    $('#change-pass').click(function (event) {
        event.preventDefault();

        var curPass = $('#current-password').val();
        var confirmPassword = $('#new-password').val();
        var reConfirm = $('#re-new-password').val();

        if (confirmPassword !== reConfirm) {
            $("#message-pass").removeClass("d-none alert-success").addClass("alert-danger").text("Passwords do not match!").fadeTo(2000, 500).slideUp(500, function () {
                $("#message-pass").slideUp(500);
            });
            return;
        }

        $.ajax({
            url: "http://localhost:8080/api/users/password",
            type: "POST",
            contentType: "application/json",
            headers: {
                "Authorization": 'Bearer ' + token
            },
            data: JSON.stringify({
                currentPassword: curPass,
                newPassword: confirmPassword,
            }),
            success: function (response) {
                $("#message-pass").removeClass("d-none alert-danger").addClass("alert-success").text("Password updated successfully").fadeTo(2000, 500).slideUp(500, function () {
                    $("#message-pass").slideUp(500);
                });
            },
            error: function (xhr, status, error) {
                var message = JSON.parse(xhr.responseText);
                $("#message-pass").removeClass("d-none alert-success").addClass("alert-danger").text(message.description).fadeTo(2000, 500).slideUp(500, function () {
                    $("#message-pass").slideUp(500);
                });
            }
        });
    });
});