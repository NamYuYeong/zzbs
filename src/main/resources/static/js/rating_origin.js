// 페이지 로딩이 완료되면 실행
document.addEventListener("DOMContentLoaded", function() {
    // API 엔드포인트 URL
    var apiUrl = "/api/rating";
    var apiUrl1 = "/api/rating1";

    // 현재 페이지 URL에서 가게 이름 파라미터 가져오기
    var placeName = decodeURIComponent(getParameterByName("place_name"));

    // API 호출하여 평균 평점 가져오기
    fetch(apiUrl + "?place_name=" + placeName)
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            console.log(data)
            // 평균 평점이 있는 경우
            if (data !== null && data !== undefined) {
                document.getElementById("averageRating").innerText = "별점 : " + Math.round(data * 10) / 10 + "점";
            }
        })
        .catch(function(error) {
            document.getElementById("averageRating").innerText = "별점이 존재하지 않습니다";
        });

    fetch(apiUrl1 + "?place_name=" + placeName)
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            console.log(data);
            console.log(data[0].uploadImage);
            if (data !== null && data !== undefined) {
                document.getElementById("review_title").innerText = "제목 : " + data[0].title;
                document.getElementById("review_body").innerText = "내용 : " + data[0].body;
                document.getElementById("review_image").src = "/boards/images/" + data[0].uploadImage;
                // document.getElementById("review_image").src = "/boards/images/2aa1d5f5-f24d-4397-876a-bc238c35e77f.jpg"
                document.getElementById("review_image").alt = "리뷰 이미지";
            }
        })
        .catch(function(error) {
            // 에러 처리
            console.error("리뷰를 가져오는 중 오류가 발생했습니다:", error);
        });
});

// URL에서 파라미터 값을 가져오는 함수
function getParameterByName(name) {
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(location.search);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}
