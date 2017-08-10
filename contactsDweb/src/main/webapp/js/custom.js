$(function() {

    $("#saveForm").validate({

        rules: {
            firstName: {
                required: true
            },
            email: {
                required: true,
                email: true
            },
            middleName: {
                required: true,
                digits: true
            },
            lastName: {
                required: true,
                minlength: 6
            },
            citizenship: {
                required: true,
                minlength: 6
            }
        },
        messages: {
            form_name: {
                required: "Поле Имя обязательное для заполнения"
            },
            form_email: {
                required: "Поле E-mail обязательное для заполнения",
                email: "Введите пожалуйста корректный e-mail"
            }
        },
        focusCleanup: true,
        focusInvalid: false,
        invalidHandler: function(event, validator) {
            $(".js-form-message").text("Исправьте пожалуйста все ошибки.");
        },
        onkeyup: function(element) {
            $(".js-form-message").text("");
        },
        errorPlacement: function(error, element) {
            return true;
        },
        errorClass: "form-input_error",
        validClass: "form-input_success"
    });

});
