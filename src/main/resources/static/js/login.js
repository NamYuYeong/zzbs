let loginObject = {
    init: function(){
        $("#btn-login").on("click", () =>{
            this.login();
        });
    },

    login: function () {
        // alert("로그인 요청");
        let user = {
            username: $("#username").val(),
            password: $("#password").val(),
        };

        // done() : 성공 처리되었을 때
        // fail() : 실패했을 때
        $.ajax({
            type:"post",
            url:"/auth/login",
            data:JSON.stringify(user),
            contentType: "application/json; charset=utf-8"
        }).done(function(response){
            // console.log(response.data);
            if (response.status == 200) {
                location = "/";
            } else {
                alert(response.data);
            }
        }).fail(function(error){
            alert("에러 발생 : " + error);
        });

    },
}

loginObject.init();