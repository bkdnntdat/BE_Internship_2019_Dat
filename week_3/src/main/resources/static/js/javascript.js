$(document).ready(function() {
    ClassicEditor
        .create(document.querySelector('.rich-text'))
        .then(editor => {
            document.editor = editor
        }).catch(error => {
            console.error(error);
        });
});


$(document).ready(function() {
    $(".savepost").click(function() {
        var title = $("#title").val();
        var content = document.editor.getData();
        var tag = $("#tags").val();

        if (title == '' || content == '' || tag == '') {
            alert('Bạn chưa nhập đủ thông tin');
            return false;
        }
        return true;
    })
});

$(document).ready(function() {
    $(".signup").click(function() {
        var username = $("#username").val();
        var password = $("#password").val();
        var firstname = $("#firstname").val();
        var lastname = $("#lastname").val();
        var email = $("#email").val();
        var phonenumber = $("#phonenumber").val();

        if (username == '') {
            alert("Bạn chưa nhập username");
            $('#username').focus();
            return false;
        } else
        if (password == '') {
            alert("Bạn chưa nhập password");
            $('#password').focus();
            return false;
        } else
        if (firstname == '') {
            alert("Bạn chưa nhập firstname");
            $('#firstname').focus();
            return false;
        } else
        if (lastname == '') {
            alert("Bạn chưa nhập lastname");
            $('#lastname').focus();
            return false;
        } else
        if (email == '') {
            alert("Bạn chưa nhập email");
            $('#email').focus();
            return false;
        } else
        if (phonenumber == '') {
            alert("Bạn chưa nhập phonenunber");
            $('#phonenumber').focus();
            return false;
        }
        return true;

        // if (username == '' || password == '' || email == '' || firstname == '' || lastname == '' || phonenumber == '') {
        //     alert('Bạn chưa nhập đủ thông tin');
        //     return false;
        // }
    })
});

$(document).ready(function() {
    $('.tagsInputs').tagsInput({});
    $('div').addClass("taginput");
});

$(document).ready(function() {
    $(".delete_post").click(function() {
        swal({
                title: "Are you sure?",
                text: "Once deleted, you will not be able to recover this imaginary file!",
                icon: "warning",
                buttons: true,
                dangerMode: true,
            })
            .then((willDelete) => {
                if (willDelete) {
                    swal("Poof! Your imaginary file has been deleted!", {
                        icon: "success",
                    });
                } else {
                    swal("Your imaginary file is safe!");
                }
            });
    })
})