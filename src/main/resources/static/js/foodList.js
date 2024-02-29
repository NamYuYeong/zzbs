let foodObj = {
    init: function(){
        let modals = document.getElementById("modal");
        let closeBtn = document.querySelector(".modal_close_btn");
        let tbody = document.querySelector(".listArea");

        tbody.onclick = function(event){
            let target = event.target;

            if(target.className === 'modify-Btn'){
                modals.style.display = "flex";
                let tr =target.parentNode.parentNode;
                let fid = tr.childNodes[1].innerText;
                let fNation = tr.childNodes[3].innerText;
                let fName = tr.childNodes[5].innerText;

                $("#fid").val(fid);
                $("#fNation").val(fNation);
                $("#fName").val(fName);
            }

            if(target.className === 'delete-Btn'){
                let tr =target.parentNode.parentNode;
                let bid = tr.childNodes[1];
            }

        }

        // 닫기 버튼 클릭하면 Modal이 닫힌다.
        closeBtn.onclick = function() {
            modals.style.display = "none";
        };

        // 수정
        $("#modify-Btn-process").on("click", ()=>{
            this.updateFood();
        });

        // 삭제
        $(".delete-Btn").on("click", ()=>{
            if (confirm("정말 삭제하시겠습니까??") == true){    //확인
                this.deleteFood();
            }else{   //취소
                return false;
            }
        });
    },

    updateFood : function(){
        let food = {
            bid:$("#fid").val(),
            nation:$("#fNation").val(),
            food:$("#fName").val()
        };
        console.log(food);
        $.ajax({
            url:"/admin/CategoryEdit",
            type:"post",
            data:JSON.stringify(food),
            contentType: "application/json; charset=utf-8",
        }).done(function (response){
            let status = response.status;
            if(status === 200){
                alert(response.data);
                location = "/admin/foodCategoryList";
            }
            // else
            // {
            //     console.log(warning);
            //     alert(warning);
            // }
        }).fail(function (error){
            alert("에러발생 = " + error);
        });
    },

    /*삭제*/

    deleteFood : function(){
        let bid = $("#bid").text();

        $.ajax({
            url:"/admin/CategoryDelete/"+ bid,
            type:"DELETE",
        }).done(function (response){
            let status = response.status;
            if(status === 200){
                alert(response.data);
                location = "/admin/foodCategoryList";
            }

        }).fail(function (error){
            alert("에러발생 = " + error);
        });
    },
}

foodObj.init();