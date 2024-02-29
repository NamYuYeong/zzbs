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
            if (data !== null && data !== undefined) {
                document.getElementById("review_title").innerHTML = "#" + "<br>" +data[0].title;
                document.getElementById("review_body").innerHTML = "-"+ "<br>" + data[0].body;
                document.getElementById("review_rating").innerHTML = "별점 : " + data[0].rating;
                document.getElementById("review_image").setAttribute("src", "/boards/images/" + data[0].saved_filename);
                document.getElementById("review_image_link").setAttribute("href", "/boards/free/" + data[0].id);
                document.getElementById("review_title1").innerHTML = "#"+ "<br>"  + data[1].title;
                document.getElementById("review_body1").innerHTML = "-"+ "<br>" + data[1].body;
                document.getElementById("review_rating1").innerHTML = "별점 : " + data[1].rating;
                document.getElementById("review_image1").setAttribute("src", "/boards/images/" + data[1].saved_filename);
                document.getElementById("review_image_link1").setAttribute("href", "/boards/free/" + data[1].id);
                document.getElementById("review_title2").innerHTML = "#"+ "<br>"  + data[2].title;
                document.getElementById("review_body2").innerHTML = "-"+ "<br>" + data[2].body;
                document.getElementById("review_rating2").innerHTML = "별점 : " + data[2].rating;
                document.getElementById("review_image2").setAttribute("src", "/boards/images/" + data[2].saved_filename);
                document.getElementById("review_image_link2").setAttribute("href", "/boards/free/" + data[2].id);
                document.getElementById("review_title3").innerHTML = "#"+ "<br>" + data[3].title;
                document.getElementById("review_body3").innerHTML = "-"+ "<br>" + data[3].body;
                document.getElementById("review_rating3").innerHTML = "별점 : " + data[3].rating;
                document.getElementById("review_image3").setAttribute("src", "/boards/images/" + data[3].saved_filename);
                document.getElementById("review_image_link3").setAttribute("href", "/boards/free/" + data[3].id);
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