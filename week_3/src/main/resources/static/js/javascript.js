$(document).ready(function() {
    $('#tagsInputs').tagsInput({});
    $('div').addClass("taginput");
});

$(document).ready(function() {
    ClassicEditor
        .create(document.querySelector('.rich-text'))
        .catch(error => {
            console.error(error);
        });
});


$(document).ready(function() {
    $(".savepost").click(function() {
        var title = $("#title").val();
        var content = $("#content").val();
        var tag = $("#tags").val();

        if (title == '' || content == '' || tag == '') {
            alert('Bạn chưa nhập đủ thông tin');
            return false;
        }
    })
});

$(document).ready(function() {
    $(".signup").click(function() {
        var username = $("#username").val();
        var password = $("#password").val();
        var email = $("#email").val();

        if (username == '' || password == '' || email == '') {
            alert('Bạn chưa nhập đủ thông tin');
            return false;
        }
    })
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