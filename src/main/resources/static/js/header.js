// 페이지 로딩이 완료되면 실행
document.addEventListener("DOMContentLoaded", function() {
    // Board API 엔드포인트 URL
    var boardApiUrl = "/api/header/images";

    // Board API 호출하여 rating이 5인 항목들의 이미지 URL 목록 가져오기
    fetch(boardApiUrl)
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            console.log(data);
            // 이미지 URL 목록이 있는 경우
            if (data !== null && data.length > 0) {
                // 슬라이더에 이미지 추가
                var swiperWrapper = document.querySelector(".swiper-wrapper");
                data.forEach(function(board) {
                    var Url = board;
                    var split = Url.split('|');
                    console.log(split[0]);
                    var slideHTML = '<div class="swiper-slide">'+'<p>'+'<a href='+split[2]+'>'+'<img src="' + split[1] + '" alt="">'+'</a>'+'</p>'+'<p>'+split[0]+'</p>'+'</div>';

                    // var slideHTML = '<div class="swiper-slide"><img src="/boards/images/61aa95d1-b2d0-4551-b5e4-a78376aecea0.jpg" alt=""></div>';
                    swiperWrapper.insertAdjacentHTML('beforeend', slideHTML);
                });
            }
        })
        .catch(function(error) {
            // 에러 처리
            console.error("이미지를 가져오는 중 오류가 발생했습니다:", error);
        });
});