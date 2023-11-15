<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <% String
root = request.getContextPath(); %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="./css/style.css" />
    <link rel="stylesheet" href="./css/main.css" />
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  </head>
  <body>
    <jsp:include page="./header.jsp"></jsp:include>

    <main>
      <!-- title -->
      <h1 class="map-title">우리동네 구석구석</h1>

      <!-- address options -->
      <div class="map-select-list">
        <select class="map-select" name="search-area" id="search-area">
          <option value="">시도선택</option>
        </select>
        <select class="map-select" name="search-content-id" id="search-content-id">
          <option value="0" selected>관광지 유형</option>
          <option value="12">관광지</option>
          <option value="14">문화시설</option>
          <option value="15">축제공연행사</option>
          <option value="25">여행코스</option>
          <option value="28">레포츠</option>
          <option value="32">숙박</option>
          <option value="38">쇼핑</option>
          <option value="39">음식점</option>
        </select>
        <input
          class="map-select"
          name="search-keyword"
          id="search-keyword"
          placeholder="검색어를 입력해주세요."
        />
        <button id="btn-search" style="font-size: 1.5rem; cursor: pointer">검색</button>
      </div>

      <!-- result filter options -->
      <form class="map-form">
        <input type="radio" id="all-select" />
        <label for="all-select">모두선택</label>
        <input type="checkbox" id="tour-spot" />
        <label for="tour-spot">관광지</label>
        <input type="checkbox" id="cultural" />
        <label for="cultural">문화시설</label>
        <input type="checkbox" id="enjoy" />
        <label for="enjoy">행사 / 공연 / 축제</label>
        <input type="checkbox" id="travel-course" />
        <label for="travel-course">여행코스</label>
        <input type="checkbox" id="leisure" />
        <label for="leisure">레포츠</label>
        <input type="checkbox" id="domitory" />
        <label for="domitory">숙박</label>
        <input type="checkbox" id="shopping" />
        <label for="shopping">쇼핑</label>
        <input type="checkbox" id="resturant" />
        <label for="resturant">음식점</label>
      </form>

      <!-- kakao map -->
      <div class="container">
        <!-- kakao map start -->
        <div id="map" class="mt-3" style="width: 100%; height: 700px"></div>
        <!-- kakao map end -->
      </div>

      <!-- carousel wrap -->
      <div class="carousel-wrap">
        <!-- carousel left button -->
        <button class="carousel-button carousel-button-left">&lt;</button>
        <!-- carousel images -->
        <ul class="carousel-flex"></ul>
        <!-- carousel right button -->
        <button class="carousel-button carousel-button-right">&gt;</button>
      </div>
    </main>

    <jsp:include page="./footer.jsp"></jsp:include>

    <script
      type="text/javascript"
      src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b9279a4bc661b8f08442322e95e06d92&libraries=services,clusterer,drawing"
    ></script>
    <script>
      const serviceKey =
        "YmLOCrrmdh8DGzVmGbT0Xu9jfrsXGgdJ6GsHZ%2FDGzKW82R5KrnlJU0dar8fqQ0nYXpb9fjuYjDQx1QuSigbTkw%3D%3D";
      let areaUrl =
        "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
        serviceKey +
        "&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";

      // fetch(areaUrl, { method: "GET" }).then(function (response) { return response.json() }).then(function (data) { makeOption(data); });
      fetch(areaUrl, { method: "GET" })
        .then((response) => response.json())
        .then((data) => makeOption(data));

      function makeOption(data) {
        let areas = data.response.body.items.item;
        console.log(areas);

        let sel = document.querySelector("#search-area");
        areas.forEach((area) => {
          let opt = document.createElement("option");
          opt.setAttribute("value", area.code);
          opt.appendChild(document.createTextNode(area.name));

          sel.appendChild(opt);
        });
      }

      // 검색 버튼을 누르면..
      // 지역, 유형, 검색어 얻기.
      // 위 데이터를 가지고 공공데이터에 요청.
      // 받은 데이터를 이용하여 화면 구성.
      document.getElementById("btn-search").addEventListener("click", () => {
        let baseUrl = "<%=root%>/enjoyboardlist?";

        let areaCode = document.getElementById("search-area")?.value;
        let contentTypeId = document.getElementById("search-content-id")?.value;
        let keyword = document.getElementById("search-keyword").value;

        console.log(areaCode, contentTypeId, keyword);

        if (parseInt(areaCode)) baseUrl += "&sido=" + areaCode;
        if (parseInt(contentTypeId)) baseUrl += "&content=" + contentTypeId;
        if (keyword) {
          baseUrl += "&word=" + keyword;
        }

        console.log(baseUrl);

        fetch(baseUrl)
          .then((response) => response.json())
          .then((data) => makeList(data));

        map = new kakao.maps.Map(mapContainer, mapOption);
      });

      var positions; // marker 배열.
      function makeList(data) {
        console.log("지도 출력");
        console.log(data);

        let tripList = "";
        positions = [];
        data.resdata.forEach((area) => {
          console.log(area);
          tripList +=
            "<li class='carousel-single-wrap' onclick='moveCenter(" +
            area.latitude +
            "," +
            area.longitude +
            ")'>" +
            "<p><img src=' " +
            area.first_image +
            "' height='150px' style='border-radius: 10px'></p>" +
            "<p>" +
            area.title +
            "</p>" +
            "<p>" +
            area.addr1 +
            " " +
            area.addr2 +
            "</p>" +
            "<p>" +
            area.latitude +
            "</p>" +
            "<p>" +
            area.longitude +
            "</p>" +
            "</li>";

          let markerInfo = {
            title: area.title,
            latlng: new kakao.maps.LatLng(area.latitude, area.longitude),
          };
          positions.push(markerInfo);
        });

        document.querySelector(".carousel-flex").innerHTML = tripList;
        displayMarker();
      }

      // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
      var map = new kakao.maps.Map(mapContainer, mapOption);

      function displayMarker() {
        // 마커 이미지의 이미지 주소입니다
        var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

        for (var i = 0; i < positions.length; i++) {
          // 마커 이미지의 이미지 크기 입니다
          var imageSize = new kakao.maps.Size(24, 35);

          // 마커 이미지를 생성합니다
          var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

          // 마커를 생성합니다
          var marker = new kakao.maps.Marker({
            map: map, // 마커를 표시할 지도
            position: positions[i].latlng, // 마커를 표시할 위치
            title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            image: markerImage, // 마커 이미지
          });
        }

        // 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
        map.setCenter(positions[0].latlng);
      }

      function moveCenter(lat, lng) {
        map.setCenter(new kakao.maps.LatLng(lat, lng));
      }
    </script>
    <script src="./script/carousel.js"></script>
    <script src="./script/address.js"></script>
  </body>
</html>
