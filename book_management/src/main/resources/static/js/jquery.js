$("#login").on('click', function() {
    var email = $("#email").val();
    var password = $("#password").val();
    if (email == "") {
        $('#messageDiv').css("display", "none");
        alert("Username is required");
        return;
    }
    if (password == "") {
        $('#messageDiv').css("display", "none");
        alert("Password is required");
        return;
    }
    $.ajax({
        url: "/logining",
        type: 'get',
        data: {
            email: email,
            password: password
        },
        success: function(results) {
            if (results != null && results != "") {
                showMessage(results);
                $('#messageDiv').css("display", "block");
            } else {
                $('#messageDiv').css("display", "none");
                $('#messageDiv').html("");
                alert("Some exception occurred! Please try again.");
            }
        } ,
        error: function(error) {
        alert("asdsadasdas");
        }
    });
});

//function to display message to the user
function showMessage(results) {
    if (results == 'SUCCESS') {
        $('#messageDiv').html("<font color='green'>You are successfully logged in. </font>")
    } else if (results == 'FAILURE') {
        $('#messageDiv').html("<font color='red'>Username or password incorrect </font>")
    }
}