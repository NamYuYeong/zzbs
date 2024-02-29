let replyObject = {
    init: function(){
        $("#btn-save-reply").on("click", () =>{
            this.insertReply();
        });

    },

    insertReply: function () {
        alert("댓글 등록 요청");

        let post_id = $("#id").text();

        let reply = {
            content: $("#reply-content").val(),
        };

        $.ajax({
            type:"post",
            url:"/reply/"+post_id,
            data:JSON.stringify(reply),
            contentType: "application/json; charset=utf-8"
        }).done(function(response){
            // console.log(response.data);
            if (response.status == 200) {
                alert(response.data);
                location = "/post/"+post_id;
            } else {
                alert(response.data);
            }
        }).fail(function(error){
            alert("에러 발생 : " + error);
        });

    },

    deleteReply: function (reply_id, post_id) {
        $.ajax({
            type:"DELETE",
            url:"/reply/"+reply_id
        }).done(function(response){
            if (response.status == 200) {
                alert(response.data);
                location = "/post/"+post_id;
            } else {
                alert(response.data);
            }
        }).fail(function(error){
            alert("에러 발생 : " + error);
        });

    },


}

replyObject.init();