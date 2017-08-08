
function setTemplate(t) {
    var sel = document.getElementById("template");
    var optionValue = t.value;
    switch (optionValue) {
        case "1":
            document.getElementById("textarea").value = document.getElementById("ST1").value;
            break;
        case "2":
            document.getElementById("textarea").value = document.getElementById("ST2").value;
            break;
        case "0":
            document.getElementById("textarea").value = "";
            break;
    }
}

window.onload = function () {

    var button = document.querySelector(".button-cancel");
    button.addEventListener("click", function (event) {
        event.preventDefault();
        var form = document.getElementById("emailForm");
        form.command.value = "email";
        form.idContact.value = "";
        form.submit();
    });

};


