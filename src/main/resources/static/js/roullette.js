var rolLength =1; // 해당 룰렛 콘텐츠 갯수
var setNum; // 랜덤숫자 담을 변수
var hiddenInput = document.createElement("input");
hiddenInput.className = "hidden-input";

// 랜덤숫자부여
const rRandom = () => {
    var min = Math.ceil(0);
    var max = Math.floor(rolLength - 1);
    return Math.floor(Math.random() * (max - min)) + min;
};

// 애니메이션 완료 후 결과 가져오기
const onAnimationComplete = () => {
    fetchRouletteResult();
    changeButtonTextToRetry();
};

const rRotate = () => {
    var panel = document.querySelector(".rouletter-wacu");
    var btn = document.querySelector(".rouletter-btn");
    var deg = [];

    // 룰렛 각도 설정(rolLength = 4)
    for (var i = 1, len = rolLength; i <= len; i++) {
        deg.push((360 / len) * i);
    }

    // 랜덤 생성된 숫자를 히든 인풋에 넣기
    var num = 0;
    document.body.append(hiddenInput);
    setNum = hiddenInput.value = rRandom();

    // 애니설정
    var ani = setInterval(() => {
        num++;
        panel.style.transform = "rotate(" + 360 * num + "deg)";
        btn.disabled = true;
        btn.style.pointerEvents = "none";

        if (num === 50) {
            clearInterval(ani);
            panel.style.transform = `rotate(${deg[setNum]}deg)`;
            // 애니메이션 완료 후 결과 가져오기
            onAnimationComplete();
        }

    }, 50);
};

// 결과 가져오기
const fetchRouletteResult = () => {
    fetch("/getRandomFood")
        .then(response => response.json())
        .then(data => {
            let dnation = data.nation;
            let dfood = data.food;
            // const resultText = "<h3>쩝쩝박사의 오늘의 추천!</h3>" +"<p><img src=\"/images/cats.png\" alt=\"\"></p>" + data.nation  + data.food;
            const resultText = "<h5>쩝쩝박사의 오늘의 추천!</h5>" +"<p><img src=\"/images/cats.png\" width='250px' alt=\"\"></p>" +"<br>"
                +"<h5>"+ data.nation+"</h5>" + "<br>"+"<h1>"+ data.food + "</h1>";
            document.getElementById("result").classList.add('result_st');
            document.getElementById("resultText").innerHTML = resultText;

            // 추천 버튼에 클릭 이벤트 추가
            const recommendButton = document.querySelector(".rouletter-re-btn");
            recommendButton.addEventListener("click", () => {
                // 헤더의 검색어 입력란에 값을 설정
                const headerSearchInput = document.getElementById('keyword');
                headerSearchInput.value = `${data.food}`;

                // 검색 버튼 클릭
                const searchButton = document.querySelector('.img-button');
                searchButton.click();
            });

            enableRecommendButton(); // 추천 버튼 활성화
        })
        .catch(error => console.error('Error fetching roulette result:', error));
};

// 추천 버튼 활성화
const enableRecommendButton = () => {
    document.querySelector(".rouletter-re-btn").disabled = false;
    document.querySelector(".rouletter-re-btn").style.pointerEvents = "auto";
};

// 버튼 텍스트를 retry로 변경
const changeButtonTextToRetry = (result) => {
    // 변경된 부분
    var resultTxt = document.querySelector("#resultText");
    resultTxt.innerText=" ";
    var btn = document.querySelector(".rouletter-btn");


    btn.innerText = "retry";
    btn.removeEventListener("click", rRotate);
    btn.addEventListener("click", () => {
        // 결과가 있을 때만 마지막 결과를 보여줌
        if (result !== null) {
            const resultText = "<h5>쩝쩝박사의 오늘의 추천!</h5>" +"<p><img src=\"/images/cats.png\" width='250px' alt=\"\"></p>" +"<br>"
                +"<h5>"+ result.nation+"</h5>" + "<br>"+"<h1>"+ result.food + "</h1>";
            document.getElementById("resultText").innerHTML = resultText;
            document.getElementById("result").classList.add('result_st');
        }
    });
    document.getElementById("result").classList.remove('result_st');


};

// 룰렛 이벤트 클릭 버튼
document.addEventListener("click", function (e) {
    var target = e.target;
    if (target.tagName === "BUTTON" && target.classList.contains("rouletter-btn")) {
        rRotate();
        rReset(target);
    }
});

// 초기화
const rReset = (ele) => {
    setTimeout(() => {
        ele.disabled = false;
        ele.style.pointerEvents = "auto";
        hiddenInput.remove();
    }, 5500);
};

// 검색을 위한 함수
function searchPlaces() {
    var keyword = document.getElementById('keyword').value;
    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        alert('키워드를 입력해주세요!');
        return false;
    }

    // 검색 결과 페이지로 이동
    window.location.href = `/users/map?keyword=${encodeURIComponent(keyword)}`;
}