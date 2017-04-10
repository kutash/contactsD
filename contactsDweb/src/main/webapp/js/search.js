/**
 * Created by Galina on 08.04.2017.
 */


 window.onload = function () {

     var emailButton = document.querySelector(".email-but");
     emailButton.addEventListener("click", function (event) {
         event.preventDefault();
         var form = document.getElementById('chosen');
         form.command.value = 'email';
         form.submit();

     });



     var button = document.querySelector(".button-cancel");
     button.addEventListener("click", function (event) {
         event.preventDefault();
         var form = document.getElementById("searchForm");
         form.reset();
     });

 };
