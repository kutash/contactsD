$(document).ready(function(){
        console.log("here");
    // Устанавливаем обработчик потери фокуса для всех полей ввода текста
    $('input#name, input#email, input#middname, input#surname, input#national, input#site, input#company, input#country, input#city, input#street, input#house, input#flat, input#index').unbind().blur( function(){

        var id = $(this).attr('id');
        var val = $(this).val();
        var rv_name = /^[a-zA-Zа-яА-Я]*[a-zA-Zа-яА-Я-\s]*$/;
        var rv_country_city = /^[а-яА-ЯёЁa-zA-Z]+\s*[а-яА-ЯёЁa-zA-Z]*-?\s*[а-яА-ЯёЁa-zA-Z]*$/;
        var rv_house_flat = /^[0-9][a-zA-Z0-9/]*/;
        switch(id)
        {
            case 'name':
                console.log(!rv_name.test(val));
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
                    $(this).removeClass('er').addClass('not_error');
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
                } else if (!rv_name.test(val)) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorMiddname').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorMiddname">This field must contain only letters<br></div>');
                } else {
                    $(this).removeClass('er').addClass('not_error');
                    $('#errorMiddname').remove();
                    $(this).css('borderColor','green');
                }
                break;

            case 'surname':

                if (val.length > 45) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorSurname').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorSurname">Maximum 45 characters<br></div>');
                } else if (val === '') {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorSurname').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorSurname">This field cannot be blank<br></div>');
                } else if (!rv_name.test(val)) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorSurname').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorSurname">This field must contain only letters<br></div>');
                } else {
                    $(this).removeClass('er').addClass('not_error');
                    $('#errorSurname').remove();
                    $(this).css('borderColor','green');
                }
                break;

            case 'national':

                if (val.length > 45) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorNational').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorNational">Maximum 45 characters<br></div>');
                } else if (val === '') {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorNational').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorNational">This field cannot be blank<br></div>');
                } else if (!rv_name.test(val)) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorNational').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorNational">This field must contain only letters<br></div>');
                } else {
                    $(this).removeClass('er').addClass('not_error');
                    $('#errorNational').remove();
                    $(this).css('borderColor','green');
                }
                break;

            case 'site':
                var rv_site = /^([a-zA-Z0-9]([a-zA-Z0-9\-]{0,61}[a-zA-Z0-9])?\.)+[a-zA-Z]{2,6}$/;
                if(val !== '') {
                    if (val.length > 45) {
                        $(this).removeClass('not_error').addClass('er');
                        $('#errorSite').remove();
                        $(this).css('borderColor', 'red');
                        $(this).after('<div class="err" id="errorSite">Maximum 45 characters<br></div>');
                    } else if (!rv_site.test(val)) {
                        $(this).removeClass('not_error').addClass('er');
                        $('#errorSite').remove();
                        $(this).css('borderColor', 'red');
                        $(this).after('<div class="err" id="errorSite">Invalid website<br></div>');
                    } else {
                        $(this).removeClass('er').addClass('not_error');
                        $('#errorSite').remove();
                        $(this).css('borderColor', 'green');
                    }
                }
                break;

            case 'email':
                var rv_email = /^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
                if (val.length > 90) {
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
                    $(this).removeClass('er').addClass('not_error');
                    $('#errorEmail').remove();
                    $(this).css('borderColor','green');
                }
                break;

            case 'company':
                var rv_company = /^[a-zA-Zа-яА-Я]*[a-zA-Z0-9а-яА-Я-_.\s]*$/;
                if (val.length > 45) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorCompany').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorCompany">Maximum 45 characters<br></div>');
                } else if (!rv_company.test(val)) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorCompany').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorCompany">Allowable characters: letters, digits, spaces, dash, underscore!<br></div>');
                } else {
                    $(this).removeClass('er').addClass('not_error');
                    $('#errorCompany').remove();
                    $(this).css('borderColor','green');
                }
                break;

            case 'country':

                if (val.length > 45) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorCountry').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorCountry">Maximum 45 characters<br></div>');
                } else if (val === '') {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorCountry').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorCountry">This field cannot be blank<br></div>');
                } else if (!rv_country_city.test(val)) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorCountry').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorCountry">Allowable characters: letters, space, dash!<br></div>');
                } else {
                    $(this).removeClass('er').addClass('not_error');
                    $('#errorCountry').remove();
                    $(this).css('borderColor','green');
                }
                break;

            case 'city':

                if (val.length > 45) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorCity').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorCity">Maximum 45 characters<br></div>');
                } else if (val === '') {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorCity').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorCity">This field cannot be blank<br></div>');
                } else if (!rv_country_city.test(val)) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorCity').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorCity">Allowable characters: letters, space, dash!<br></div>');
                } else {
                    $(this).removeClass('er').addClass('not_error');
                    $('#errorCity').remove();
                    $(this).css('borderColor','green');
                }
                break;

            case 'street':
                var rv_street = /^[а-яА-ЯёЁa-zA-Z0-9]*\s*[а-яА-ЯёЁa-zA-Z0-9]*-?\.?\s*\/*[а-яА-ЯёЁa-zA-Z0-9]*$/;
                if (val.length > 45) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorStreet').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorStreet">Maximum 45 characters<br></div>');
                } else if (val === '') {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorStreet').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorStreet">This field cannot be blank<br></div>');
                } else if (!rv_street.test(val)) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorStreet').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorStreet">Allowable characters: letters, digits, spaces, dash, dot, slash!<br></div>');
                } else {
                    $(this).removeClass('er').addClass('not_error');
                    $('#errorStreet').remove();
                    $(this).css('borderColor','green');
                }
                break;

            case 'house':

                if (val.length > 45) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorHouse').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorHouse">Maximum 45 characters<br></div>');
                } else if (val === '') {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorHouse').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorHouse">This field cannot be blank<br></div>');
                } else if (!rv_house_flat.test(val)) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorHouse').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorHouse">Allowable characters: letters, digits, slash!<br></div>');
                } else {
                    $(this).removeClass('er').addClass('not_error');
                    $('#errorHouse').remove();
                    $(this).css('borderColor','green');
                }
                break;

            case 'flat':

                if (val.length > 45) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorFlat').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorFlat">Maximum 45 characters<br></div>');
                } else if (val === '') {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorFlat').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorFlat">This field cannot be blank<br></div>');
                } else if (!rv_house_flat.test(val)) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorFlat').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorFlat">Allowable characters: letters, digits, slash!<br></div>');
                } else {
                    $(this).removeClass('er').addClass('not_error');
                    $('#errorFlat').remove();
                    $(this).css('borderColor','green');
                }
                break;

            case 'index':
                var rv_index = /\d*/;
                if (val.length > 45) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorIndex').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorIndex">Maximum 45 characters<br></div>');
                } else if (val === '') {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorIndex').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorIndex">This field cannot be blank<br></div>');
                } else if (!rv_index.test(val)) {
                    $(this).removeClass('not_error').addClass('er');
                    $('#errorIndex').remove();
                    $(this).css('borderColor','red');
                    $(this).after('<div class="err" id="errorIndex">This field must contain only digits!<br></div>');
                } else {
                    $(this).removeClass('er').addClass('not_error');
                    $('#errorIndex').remove();
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
