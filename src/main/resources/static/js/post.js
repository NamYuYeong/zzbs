let postObject = {
    init: function(){
        $("#btn-save").on("click", () =>{
            this.insertPost();
        });

        $("#btn-delete").on("click", () =>{
            this.deletePost();
        });

        $("#btn-update").on("click", () =>{
            this.updatePost();
        });
    },

    insertPost: function () {
        alert("포스트 등록 요청");
        let post = {
            title: $("#title").val(),
            content: $("#content").val(),
        };

        // done() : 성공 처리되었을 때
        // fail() : 실패했을 때
        $.ajax({
            type:"POST",
            url:"/post",
            data:JSON.stringify(post),
            contentType: "application/json; charset=utf-8"
        }).done(function(response){
            // console.log(response.data);
            if (response.status == 200) {
                alert(response.data);
                location = "/";
            } else {
                let warning = "";
                let errors = response.data;
                if(errors.title != null) warning = warning + errors.title + "\n";
                if(errors.content != null) warning = warning + errors.content;
                alert(warning);
            }
        }).fail(function(error){
            alert("에러 발생 : " + error);
        });

    },
    updatePost: function () {
        alert("포스트 수정 요청");
        let post = {
            id: $("#id").val(),
            title: $("#title").val(),
            content: $("#content").val(),
        };

        // done() : 성공 처리되었을 때
        // fail() : 실패했을 때
        $.ajax({
            type:"PUT",
            url:"/post",
            data:JSON.stringify(post),
            contentType: "application/json; charset=utf-8"
        }).done(function(response){
            // console.log(response.data);
            if (response.status == 200) {
                alert(response.data);
                location = "/";
            } else {
                alert(response.data);
            }
        }).fail(function(error){
            alert("에러 발생 : " + error);
        });
    },
    deletePost: function () {
        alert("포스트 삭제 요청");
        let id = $("#id").text();

        // done() : 성공 처리되었을 때
        // fail() : 실패했을 때
        $.ajax({
            type:"DELETE",
            url:"/post/" + id
        }).done(function(response){
            // console.log(response.data);
            if (response.status == 200) {
                alert(response.data);
                location = "/";
            } else {
                alert(response.data);
            }
        }).fail(function(error){
            alert("에러 발생 : " + error);
        });

    },


}

postObject.init();