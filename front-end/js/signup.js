$(document).ready(function () {
    $('#btn-signup').click(function (e) {
        e.preventDefault();

        $('.error-message').remove();
        $('#error').remove();

        var phone = $('#phone').val();
        var password = $('#password').val();
        var repassword = $('#repassword').val();
        var isError = false;
        console.log(phone, password, repassword);

        if (phone.length !== 10) {
            $('#phone').after('<span class="error-message text-danger">Phone number must be 10 digits.</span>');
            isError = true;
        }

        if (password !== repassword) {
            $('#repassword').parent().after('<div class="error-message text-danger mb-3">Passwords do not match.</div>');
            isError = true;
        }

        if (!isError) {
            var signupData = {
                fullName: $('#fullName').val(),
                username: $('#username').val(),
                phone: $('#phone').val(),
                gender: $('input[name="inlineRadioOptions"]:checked').val() === 'option1' ? 'Female' : 'Male',
                address: $('#address').val(),
                password: $('#password').val(),
                repassword: $('#repassword').val()
            };

            $.ajax({
                url: 'http://localhost:8080/auth/signup',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(signupData),
                success: function (response) {
                    if (response && response.success === true) {
                        window.location.replace('login.html');
                    } else {
                        $('#error').val('Signup failed: ' + (response.description || 'Unknown error'));
                    }
                },
                error: function (xhr, status, error) {
                    $('#error').val(error);
                }
            });
        }
    });
});
