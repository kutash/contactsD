/**
 * Created by Galina on 03.04.2017.
 */


window.onload = function () {


    var modalButton = document.querySelector(".edit-but");
    modalButton.addEventListener("click", function (event) {
        event.preventDefault();
        var form = document.getElementById('chosen');
        form.command.value = 'edit';
        form.idChosen.value = '';
        if( countCont() === 0 ) {
            alert("Select contact");
            return false;
        }
        if( countCont() > 1) {
            alert("You can select only one contact");
            return false;
        }
        form.submit();
    });




    var modalButton = document.querySelector(".delete-but");
    modalButton.addEventListener("click", function (event) {
        event.preventDefault();
        var form = document.getElementById('chosen');
        form.command.value = 'delete';
        /*form.mode.value = 'delete';*/
        if (countCont() > 0) {
            form.submit();
        } else {
            alert("Choose contacts");
        }
    });


    var modalButton = document.querySelector(".email-but");
    modalButton.addEventListener("click", function (event) {
        event.preventDefault();
        var form = document.getElementById('chosen');
        form.command.value = 'email';
        form.idChosen.value = '';
        form.submit();

    });


    function countCont() {
        "use strict";
        var checkboxes = document.getElementsByName("idContact")
        var length = checkboxes.length;
        var count = 0;
        for (var i = 0; i < length; i++)
            if (checkboxes[i].checked) count++;
        return count;
    }


    var modalButton = document.querySelector(".next");
    modalButton.addEventListener("click", function (event) {
        event.preventDefault();
        var form = document.getElementById("showForm");
        form.command.value="show";
        if( form.currentPage.value < form.currentPage.max){
            form.currentPage.value ++ ;
            form.submit();
        }
    });



    var modalButton = document.querySelector(".prev");
    modalButton.addEventListener("click", function (event) {
        event.preventDefault();
        var form = document.getElementById("showForm");
        form.command.value="show";
        if( form.currentPage.value > 1) {
            form.currentPage.value -- ;
            form.submit();
        }
    });


    var modalButton = document.querySelector(".str");
    modalButton.addEventListener("change", function (event) {
        event.preventDefault();
        var form = document.getElementById("showForm");
        form.command.value="show";
        form.submit();
    });





};

    window.addEventListener("load", function (event) {
        event.preventDefault();
        var message = document.getElementById("emailMessage").value;
        if(message !== "")  alert(message);
    });


















