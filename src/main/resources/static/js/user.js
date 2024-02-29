let userObject = {
    init: function(){
        let _this = this;

        $("#btn-save").on("click", () =>{
            _this.insertUser();
        });
    },

    insertUser: function () {
        alert("회원 가입 요청");
        let user = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        }

        console.log(user);

        // done() : 성공 처리되었을 때
        // fail() : 실패했을 때
        $.ajax({
            type:"post",
            url:"/auth/register",
            data:JSON.stringify(user),
            contentType: "application/json; charset=utf-8"
        }).done(function(response){
            // console.log(response);
            let warning = "";
            let errors = response.data; // errorMap
            if(errors.username != null) warning = warning + errors.username + "\n";
            if(errors.email != null) warning = warning + errors.email;
            alert(warning);

        }).fail(function(error){
            alert("에러 발생 : " + error);
        });

    },
}

userObject.init();