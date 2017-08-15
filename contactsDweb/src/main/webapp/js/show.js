window.onload = function () {

    var message = document.getElementById('emailMessage').value;
    if(message !== '')  alert(message);

    var modalButtonEdit = document.querySelector('.edit-but');
    modalButtonEdit.addEventListener("click", function (event) {
        event.preventDefault();
        var form = document.getElementById('chosen');
        form.command.value = 'edit';
        form.idChosen.value = '';
        if( countCont() === 0 ) {
            alert('Select contact');
            return false;
        }
        if( countCont() > 1) {
            alert('You can select only one contact');
            return false;
        }
        form.submit();
    });

    var modalButtonDelete = document.querySelector('.delete-but');
    modalButtonDelete.addEventListener('click', function (event) {
        event.preventDefault();
        var form = document.getElementById('chosen');
        form.command.value = 'delete';
        if (countCont() > 0) {
            var doDelete = confirm('Are you sure you want to delete this contacts?');
            if(doDelete == false) {
                event.preventDefault();
            }else {
                form.submit();
            }
        } else {
            alert('Choose contacts');
        }
    });

    var modalButton = document.querySelector('.email-but');
    modalButton.addEventListener('click', function (event) {
        event.preventDefault();
        var form = document.getElementById('chosen');
        form.command.value = 'email';
        form.idChosen.value = '';
        form.submit();

    });

};

function countCont() {
    'use strict';
    var checkboxes = document.getElementsByName('idContact');
    var length = checkboxes.length;
    var count = 0;
    for (var i = 0; i < length; i++)
        if (checkboxes[i].checked) count++;
    return count;
}



















