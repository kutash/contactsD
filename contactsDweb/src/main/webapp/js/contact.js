/**
 * Created by Galina on 19.03.2017.
 */


function previewFile() {
    var preview = document.getElementById('image');
    var file    = document.getElementById('photo').files[0];
    var reader  = new FileReader();

    reader.onloadend = function () {
        preview.src = reader.result;
    }

    if (file) {
        reader.readAsDataURL(file);
    } else {
        preview.src = "${pageContext.request.contextPath}/my-servlet?command=photo&amp;idContact=${contact.id}";
    }
}


function chooseContact() {
    "use strict";
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
}


function countCont() {
    "use strict";
    var checkboxes = document.getElementsByName("idContact")
    var length = checkboxes.length;
    var count = 0;
    for (var i=0; i<length; i++)
        if (checkboxes[i].checked)  count++;
    return count;
}

function openWindow() {
    var win = document.getElementById("win");
    win.style.display = "block";
}

function closeWindow() {
    var win = document.getElementById("win");
    win.style.display = "none";
}

function save() {
    var form = document.getElementById("saveForm");
    var file = form.attach.files[0];
    form.command.value = 'attach';
    form.submit();

}

