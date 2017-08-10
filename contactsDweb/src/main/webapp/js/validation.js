$(document).ready(function(){
        console.log("here");
    // Устанавливаем обработчик потери фокуса для всех полей ввода текста
    $('input#name, input#email, input#middname').unbind().blur( function(){

        var id = $(this).attr('id');
        var val = $(this).val();
        var rv_name = /^[a-zA-Zа-яА-Я]+$/;
        switch(id)
        {
            case 'name':

                if (val.length > 45) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorName').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorName">Maximum 45 characters<br></div>');
                } else if (val === '') {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorName').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorName">This field cannot be blank<br></div>');
                } else if (!rv_name.test(val)) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorName').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorName">This field must contain only letters<br></div>');
                } else {
                    $(this).addClass('not_error');
                    $('#errorName').remove();
                    $(this).css('borderColor','green');
                }
                break;

            case 'middname':

                if (val.length > 45) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorMiddname').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorMiddname">Maximum 45 characters<br></div>');
                } else if (val === '') {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorMiddname').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorMiddname">This field cannot be blank<br></div>');
                } else if (!rv_name.test(val)) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorMiddname').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorMiddname">This field must contain only letters<br></div>');
                } else {
                    $(this).addClass('not_error');
                    $('#errorMiddname').remove();
                    $(this).css('borderColor','green');
                }
                break;

            case 'email':
                var rv_email = /^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
                if (val.length > 45) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorEmail').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorEmail">Maximum 45 characters<br></div>');
                } else if (val === '') {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorEmail').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorEmail">This field cannot be blank<br></div>');
                } else if (!rv_email.test(val)) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorEmail').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorEmail">Invalid email<br></div>');
                } else {
                    $(this).addClass('not_error');
                    $('#errorEmail').remove();
                    $(this).css('borderColor','green');
                }
                break;

        } // end switch(...)

    }); // end blur()

    /*// Теперь отправим наше письмо с помощью AJAX
    $('form#feedback-form').submit(function(e){

     // Запрещаем стандартное поведение для кнопки submit
     e.preventDefault();

     // После того, как мы нажали кнопку "Отправить", делаем проверку,
     // если кол-во полей с классом .not_error равно 3 (так как у нас всего 3 поля), то есть все поля заполнены верно,
     // выполняем наш Ajax сценарий и отправляем письмо адресату

     if($('.not_error').length == 3)
{
    // Eще одним моментом является то, что в качестве указания данных для передачи обработчику send.php, мы обращаемся $(this) к нашей форме,
    // и вызываем метод .serialize().
    // Это очень удобно, т.к. он сразу возвращает сгенерированную строку с именами и значениями выбранных элементов формы.

    $.ajax({
    url: 'send.php',
    type: 'post',
    data: $(this).serialize(),

    beforeSend: function(xhr, textStatus){
    $('form#feedback-form :input').attr('disabled','disabled');
},

    success: function(response){
    $('form#feedback-form :input').removeAttr('disabled');
    $('form#feedback-form :text, textarea').val('').removeClass().next('.error-box').text('');
    alert(response);
}
}); // end ajax({...})
}

    // Иначе, если количество полей с данным классом не равно значению 3, мы возвращаем false,
    // останавливая отправку сообщения в невалидной форме
    else
{
    return false;
}

}); // end submit()*/

}); // end script
