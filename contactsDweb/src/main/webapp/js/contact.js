/**
 * Created by Galina on 19.03.2017.
 */


function previewFile() {
    var preview = document.getElementById('image');
    var file    = document.getElementById('photo').files[0];
    var reader  = new FileReader();

    reader.onloadend = function () {
        preview.src = reader.result;
    };

    if (file) {
        reader.readAsDataURL(file);
    } else {
        preview.src = "${pageContext.request.contextPath}/my-servlet?command=photo&amp;idContact=${contact.id}";
    }
}


window.onload = function () {

    var modalButton = document.querySelector(".show-modal");
    modalButton.addEventListener("click", function (event) {
        event.preventDefault();
        var modalWindow = document.querySelector(".modal");
        var fileName = document.getElementById("div_attachName");
        fileName.style.display = "none";
        var input_file = document.getElementById("div_attaches");
        input_file.style.display = "initial";
        modalWindow.style.display = "block";
        var form = document.getElementById('saveForm');
        form.attachButton.value = 'add';
    });


    var closeButtons = document.querySelectorAll(".cancel");
    for (var i = 0, length = closeButtons.length; i < length; i++) {
        var btn = closeButtons[i];
        btn.addEventListener("click", function (event) {
            event.preventDefault();
            var modalWindow = document.querySelector(".modal");
            modalWindow.style.display = "none";
        });
    }


    var submitButton = document.querySelector(".submit-button");
    submitButton.addEventListener("click", function (event) {
        event.preventDefault();
        var form = document.getElementById('saveForm');
        var input_file = document.getElementById("div_attaches");
        if(form.attach.value == "" && input_file.style.display == "initial") {
            alert("Select the file, please");
            return false;
        }
        form.command.value = 'attach';
        form.submit();
        var modalWindow = document.querySelector(".modal");
        modalWindow.style.display = "none";
    });


    var deleteButton = document.querySelector(".delete_attach");
    deleteButton.addEventListener("click", function (event) {
        event.preventDefault();
        var form = document.getElementById('saveForm');
        form.command.value = 'attach';
        form.attachButton.value = 'delete';
        form.submit();

    });


    var editButton = document.querySelector(".edit_attach");
    editButton.addEventListener("click", function (event) {
        event.preventDefault();
        var modalWindow = document.querySelector(".modal");
        modalWindow.style.display = "block";
        var comment= document.getElementById("popUp_comment");
        var fileName = document.getElementById("popUp_attachName");
        var table = document.getElementById("attachTable");
        var checkboxes = document.getElementsByName('attach_checkbox'), length = checkboxes.length;
        var form = document.getElementById('saveForm');
        form.attachButton.value = 'edit';
        var input_file = document.getElementById("div_attaches");
        var file_name = document.getElementById("div_attachName");
        input_file.style.display = "none";
        file_name.style.display = "initial";

        for (var i=0; i<length; i++) {
            if (checkboxes[i].checked) {
                var row = table.rows[i];
                fileName.value = row.cells[4].firstElementChild.value;
                comment.value = row.cells[3].firstElementChild.value;
            }
        }

    });




    var rowCount = 0;
    var flag = 0;



    var phoneShow = document.querySelector(".show-modalphone");
    phoneShow.addEventListener("click", function (event) {
        event.preventDefault();
        flag = 0;
        var modalWindow = document.querySelector(".modalPones");
        modalWindow.style.display = "block";
    });



    var phoneSubmit = document.querySelector(".save_phone");
    phoneSubmit.addEventListener("click", function (event) {
        event.preventDefault();
        var modalWindow = document.querySelector(".modalPones");
        var isValid = true;
        var fields = modalWindow.querySelectorAll(".field");
        for (var i2 = 0, length22 = fields.length; i2 < length22; i2++) {
            var field = fields[i2];
            var inputField = field.querySelector("input");
            if (inputField.value) {
                field.classList.remove("invalid");
            } else {
                field.classList.add("invalid");
                isValid = false;
            }
        }


        if (isValid) {
            var table = document.getElementById("phoneTable");
            var form = document.getElementById("telForm");
            modalWindow.style.display = "none";
            var i, row, cell1, cell2, cell3, cell4, cell5, cell6, cell7;

            if (flag == 0) {
                i = table.rows.length;
                row = table.insertRow(i);
                cell1 = row.insertCell(0);
                cell2 = row.insertCell(1);
                cell3 = row.insertCell(2);
                cell4 = row.insertCell(3);
                cell5 = row.insertCell(4);
                cell6 = row.insertCell(5);
                cell7 = row.insertCell(6);
            } else {
                i = rowCount;
                row = table.rows[i];
                cell1 = row.cells[0];
                cell2 = row.cells[1];
                cell3 = row.cells[2];
                cell4 = row.cells[3];
                cell5 = row.cells[4];
                cell6 = row.cells[5];
                cell7 = row.cells[6];
            }

            cell1.innerHTML = "<input type='checkbox'  name='phone_checkbox'/>";

            var fullPhone = form.countryCode.value + " " + form.operatorCode.value + " " + form.telephone.value;
            cell2.innerHTML = "<input type='text' form='saveForm' value='" + fullPhone + "' readonly/>";
            cell3.innerHTML = "<input type='text' form='saveForm' name='type" + i + "' value='" + form.phonetype.value + "' readonly/>";
            cell4.innerHTML = "<input type='text' form='saveForm' name='comment" + i + "' value='" + form.phoneComment.value + "' readonly/>";
            cell5.innerHTML = "<input type='hidden' form='saveForm' name='countryCode" + i + "' value='" + form.countryCode.value + "' />";
            cell6.innerHTML = "<input type='hidden' form='saveForm' name='operatorCode" + i + "' value='" + form.operatorCode.value + "' />";
            cell7.innerHTML = "<input type='hidden' form='saveForm' name='telephone" + i + "' value='" + form.telephone.value + "' />";

            form.reset();
        }
    });



    var phoneEdit = document.querySelector(".edit");
    phoneEdit.addEventListener("click", function (event) {
        event.preventDefault();

        var form= document.getElementById("telForm");
        var table = document.getElementById("phoneTable");
        var checkboxes = document.getElementsByName('phone_checkbox'), length = checkboxes.length;
        if(phoneCount()===0){
            alert("select phone please");
            return false;
        }
        var modalWindow = document.querySelector(".modalPones");
        modalWindow.style.display = "block";
        for (var i=0; i<length; i++) {
            if (checkboxes[i].checked) {
                var row = table.rows[i];
                form.countryCode.value = row.cells[4].childNodes[0].value;
                form.operatorCode.value = row.cells[5].childNodes[0].value;
                form.telephone.value = row.cells[6].childNodes[0].value;
                form.phonetype.value = row.cells[2].childNodes[0].value;
                form.phoneComment.value = row.cells[3].childNodes[0].value;
                rowCount = i;
                flag = 1;

                break;
            }
        }

    });



    function phoneCount() {
        "use strict";
        var checkboxes = document.getElementsByName("phone_checkbox")
        var length = checkboxes.length;
        var count = 0;
        for (var i = 0; i < length; i++)
            if (checkboxes[i].checked) count++;
        return count;
    }



    var phoneDelete = document.querySelector(".delete_phone");
    phoneDelete.addEventListener("click", function (event) {
        event.preventDefault();
        var table = document.getElementById("phoneTable");
        var checkboxes = document.getElementsByName('phone_checkbox'), length = checkboxes.length;
        for (var i=length - 1; i>=0; i--) {
            if (checkboxes[i].checked) {
                table.deleteRow(i);
            }
        }
    });



    var phoneClose = document.querySelectorAll(".cancelPhone");
    for (var j = 0, length2 = phoneClose.length; j < length2; j++) {
        var btn2 = phoneClose[j];
        btn2.addEventListener("click", function (event) {
            event.preventDefault();
            document.getElementById("telForm").reset();
            var modalWindow = document.querySelector(".modalPones");
            var fields = modalWindow.querySelectorAll(".field");
            for (var i2 = 0, length22 = fields.length; i2 < length22; i2++) {
                var field = fields[i2];
                var inputField = field.querySelector("input");
                field.classList.remove("invalid");
            }
            modalWindow.style.display = "none";
        });
    }



    var modalButton1 = document.querySelector(".delete-but");
    modalButton1.addEventListener("click", function (event) {
        event.preventDefault();
        var form = document.getElementById('chosen');
        if (form.idContact.value===""){
            alert("You must choose valid contact");
            return false;
        }
        form.command.value = 'delete';
        form.idChosen.value = '';
        form.submit();

    });


    var emailButton = document.querySelector(".email-but");
    emailButton.addEventListener("click", function (event) {
        event.preventDefault();
        var form = document.getElementById('chosen');
        form.command.value = 'email';
        form.idChosen.value = '';
        form.submit();

    });


    function countCont() {
        "use strict";
        var checkboxes = document.getElementsByName("idContact");
        var length = checkboxes.length;
        var count = 0;
        for (var i = 0; i < length; i++)
            if (checkboxes[i].checked) count++;
        return count;
    }


};






  
  






























