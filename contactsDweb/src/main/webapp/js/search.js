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



     var buttonSave = document.querySelector(".button-save");
     buttonSave.addEventListener("click", function (event) {
         event.preventDefault();
         var form = document.getElementById("searchForm");
         var fields = document.querySelectorAll(".input-txt");
         var count =0;
         for (var i = 0, length = fields.length; i < length; i++) {

             if (fields[i].value) {
                 count++;
             }
         }
             var inputSel = document.getElementById("gender");
             if (inputSel.value != "") {
                 count++
             }
         var inputSel2 = document.getElementById("status");
         if (inputSel2.value != "") {
             count++
         }
             if (count === 0) {
                 alert("Select criteries");
                 return false;
             }

         form.submit();
     });

 };
