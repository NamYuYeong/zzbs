function searchPlaces() {
    var keyword = document.getElementById('keyword').value;
    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        alert('키워드를 입력해주세요!');
        return false;
    }

    // 검색 결과 페이지로 이동
    window.location.href = `/users/map?keyword=${encodeURIComponent(keyword)}`;
}