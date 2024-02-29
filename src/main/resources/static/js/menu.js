let menuObject = {
    init: function(){
        $("#rouletter-btn").on("click", () =>{
            this.getMenu();
        });
    },
    getMenu: function () {
        let menu = {
            bid: $("#bid").val(),
            nation: $("#nation").val(),
            food: $("#food").val(),
        };

        $.ajax({
            type:"get",
            url:"/",
            data:JSON.stringify(menu),
            contentType: "application/json; charset=utf-8"
        }).done(function(response){
            if (response.status == 200) {
                alert(response.data);
                location = "users/getMenu?bid="+menu.bid;
            } else {
                alert(response.data);
            }
        }).fail(function(error){
            alert("에러 발생 : " + error);
        });
    },
}

menuObject.init();