<script setup>
import axios from 'axios';
import { onMounted, ref } from 'vue';

const serviceKey = 'YmLOCrrmdh8DGzVmGbT0Xu9jfrsXGgdJ6GsHZ%2FDGzKW82R5KrnlJU0dar8fqQ0nYXpb9fjuYjDQx1QuSigbTkw%3D%3D';
const areaUrl =
    'https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=' +
    serviceKey +
    '&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json';

const src =
    '//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=4fd0aaf2d6c6b86333a1b8f0115bac76&libraries=services,clusterer,drawing';
var map = ref(null);
const mapContainer = ref(null);

const sido_code = ref([{}]);
const gugun_code = ref([{}]);
const carousel = ref([{}]);

onMounted(() => {
    sidoCall();
    setMap();
});

function setMap() {
    // 카카오 맵 스크립트를 동적으로 로드합니다.
    const script = document.createElement('script');
    script.onload = () => initializeMap(); // 스크립트 로드가 완료되면 지도를 초기화합니다.
    script.src = src;
    document.head.appendChild(script);
}

function initializeMap() {
    // 카카오 맵 API가 로드된 후 호출될 함수입니다.
    kakao.maps.load(() => {
        const options = {
            center: new kakao.maps.LatLng(37.5665, 126.978), // 지도의 중심좌표
            level: 2, // 지도의 확대 레벨
        };
        // 지도를 생성합니다.
        map = new kakao.maps.Map(mapContainer.value, options);
    });
}

function sidoCall() {
    var url = '/api/restattrsido';
    axios
        .get(url)
        .then((response) => {
            sido_code.value = response.data.resmsg;
        })
        .catch((error) => {
            console.log(error);
        });
}

const sidochange = (e) => {
    console.log(e.target.value);

    var url = `/api/restattrgugun?sido_code=${e.target.value}`;
    axios
        .get(url)
        .then((response) => {
            gugun_code.value = response.data.resmsg;
        })
        .catch((error) => {
            console.log(error);
        });
};

const gugunchange = (e) => {
    console.log(e.target.value);
};

const Search = () => {
    markers.value.forEach((marker) => {
        marker.setMap(null);
    });
    positions = ref([]);
    let baseUrl = '/api/attrinfolist';

    let sidoCode = document.getElementById('search-area')?.value;
    let gugunCode = document.getElementById('search-gugun')?.value;

    if (document.getElementById('all-select').checked == false) {
        var contentTypeId = document.querySelectorAll('input[name="contentid"]:checked');
    } else {
        var contentTypeId = [];
    }
    let keyword = document.getElementById('search-keyword').value;
    let contentid = [];

    contentTypeId.forEach((id) => {
        contentid.push(id.value);
    });

    var param = {
        sido_code: sidoCode,
        gugun_code: gugunCode,
        contentid: contentid,
        word: keyword,
    };

    axios.post(baseUrl, param).then((response) => makeList(response.data));
};
var markers = ref([]);
var positions = ref([]); // marker 배열.
var overlays = ref([]);

function makeList(data) {
    carousel.value = data.resdata;
    console.log(data.resdata);

    positions = [];
    data.resdata.forEach((area) => {
        let markerInfo = {
            title: area.title,
            latlng: new kakao.maps.LatLng(area.latitude, area.longitude),
            content_type_id: area.content_type_id,
            addr1: area.addr1,
        };
        positions.push(markerInfo);
    });
    displayMarker();
}

function closeOverlay(overlay) {
    overlay.setMap(null);
}

function displayMarker() {
    // 마커 이미지의 이미지 주소입니다

    for (var i = 0; i < positions.length; i++) {
        // 마커 이미지의 이미지 크기 입니다
        var imageSize = new kakao.maps.Size(40, 40);
        var marker = '';
        var markertxt = '';
        switch (positions[i].content_type_id) {
            case '12':
                marker = '/src/assets/images/marker/관광.png';
                markertxt = '관광';
                break;
            case '14':
                marker = 'src/assets/images/marker/문화시설.png';
                markertxt = '문화시설';
                break;
            case '15':
                marker = 'src/assets/images/marker/행사_공연_축제.png';
                markertxt = '행사/공연/축제';
                break;
            case '25':
                marker = 'src/assets/images/marker/여행코스.png';
                markertxt = '여행코스';
                break;
            case '28':
                marker = 'src/assets/images/marker/레포츠.png';
                markertxt = '레포츠';
                break;
            case '32':
                marker = 'src/assets/images/marker/숙박.png';
                markertxt = '숙박';
                break;
            case '38':
                marker = 'src/assets/images/marker/쇼핑.png';
                markertxt = '쇼핑';
                break;
            case '39':
                marker = 'src/assets/images/marker/음식점.png';
                markertxt = '음식점';
                break;
            default:
                break;
        }
        // 마커 이미지를 생성합니다
        var markerImage = new kakao.maps.MarkerImage(marker, imageSize);

        var content =
            '<div class="card">' +
            '    <div class="card-title">여행지</div>' +
            '<div onclick="closeOverlay(' +
            overlays[i] +
            ')" title="닫기" >닫기</div>' +
            '    <div class="card-text">' +
            '        <div class="card-text">이름:' +
            positions[i].title +
            '</div>' +
            '    </div>' +
            '    <ul>' +
            '        <li class="up">' +
            '            <span class="card-text">종류</span>' +
            '            <span class="title">' +
            markertxt +
            '</span>' +
            '        </li>' +
            '        <li>' +
            '            <span class="number">주소</span>' +
            '            <span class="title">' +
            positions[i].addr1 +
            '</span>' +
            '        </li>' +
            '    </ul>' +
            '</div><br/><br/>';

        // 마커를 생성합니다
        markers.value.push(
            new kakao.maps.Marker({
                map: map, // 마커를 표시할 지도
                position: positions[i].latlng, // 마커를 표시할 위치
                title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                image: markerImage, // 마커 이미지
            })
        );
        var overlayPosition = new kakao.maps.LatLng(positions[i].latlng.lat, positions[i].latlng.lat);

        overlays.value.push(
            new kakao.maps.CustomOverlay({
                map: map,
                position: positions[i].latlng,
                content: content,
                yAnchor: 1,
            })
        );
    }

    // 커스텀 오버레이를 생성합니다

    // 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
    map.setCenter(positions[0].latlng);
    map.setLevel(6);
}

const moveCenter = (lat, lng) => {
    map.setCenter(new kakao.maps.LatLng(lat, lng));
};

const selectall = (e) => {
    var checkboxes = document.getElementsByName('contentid');
    if (e.target.checked) {
        checkboxes.forEach((ch) => {
            ch.setAttribute('disabled', 'true');
        });
    } else {
        checkboxes.forEach((ch) => {
            ch.removeAttribute('disabled');
        });
    }
};
</script>

<template>
    <main>
        <!-- title -->
        <h1 class="map-title">우리동네 구석구석</h1>

        <!-- address options -->
        <div class="map-select-list">
            <select class="map-select" name="search-area" id="search-area" required @change="sidochange">
                <option value="" disabled selected>시도 선택</option>
                <option v-for="sido in sido_code" :key="sido.sido_code" :value="sido.sido_code">
                    {{ sido.sido_name }}
                </option>
            </select>
            <select class="map-select" name="search-gugun" id="search-gugun" required @change="gugunchange">
                <option value="" disabled selected>구군 선택</option>
                <option v-for="gugun in gugun_code" :key="gugun.gugun_code" :value="gugun.gugun_code">
                    {{ gugun.gugun_name }}
                </option>
            </select>
            <input class="map-select" name="search-keyword" id="search-keyword" placeholder="검색어를 입력해주세요." />
            <button id="btn-search" style="font-size: 1.5rem; cursor: pointer" @click="Search">검색</button>
        </div>

        <!-- result filter options -->
        <form class="map-form">
            <input type="checkbox" id="all-select" @click="selectall" />
            <label for="all-select">모두선택</label>

            <input type="checkbox" id="tour-spot" name="contentid" value="12" />
            <label for="tour-spot">관광지</label>

            <input type="checkbox" id="cultural" name="contentid" value="14" />
            <label for="cultural">문화시설</label>

            <input type="checkbox" id="enjoy" name="contentid" value="15" />
            <label for="enjoy">행사 / 공연 / 축제</label>

            <input type="checkbox" id="travel-course" name="contentid" value="25" />
            <label for="travel-course">여행코스</label>

            <input type="checkbox" id="leisure" name="contentid" value="28" />
            <label for="leisure">레포츠</label>

            <input type="checkbox" id="domitory" name="contentid" value="32" />
            <label for="domitory">숙박</label>

            <input type="checkbox" id="shopping" name="contentid" value="38" />
            <label for="shopping">쇼핑</label>

            <input type="checkbox" id="resturant" name="contentid" value="39" />
            <label for="resturant">음식점</label>
        </form>

        <!-- kakao map -->
        <div class="container">
            <!-- kakao map start -->
            <div ref="mapContainer" style="width: 100%; height: 700px"></div>
            <!-- kakao map end -->
        </div>

        <!-- carousel wrap -->
        <div class="carousel-wrap">
            <!-- carousel left button -->
            <button class="carousel-button carousel-button-left">&lt;</button>
            <!-- carousel images -->
            <ul class="carousel-flex">
                <template v-if="carousel.length > 1">
                    <li
                        v-for="caro in carousel"
                        :key="caro.title"
                        class="carousel-single-wrap"
                        @click="moveCenter(caro.latitude, caro.longitude)"
                    >
                        <p><img :src="caro.first_image" style="border-radius: 10px; width: 100%; height: 150px" /></p>
                        <p>{{ caro.title }}</p>
                        <p>{{ caro.addr1 }} {{ caro.addr2 }}</p>
                        <p>{{ caro.latitude }}</p>
                        <p>{{ caro.longitude }}</p>
                    </li>
                </template>
            </ul>
            <!-- carousel right button -->
            <button class="carousel-button carousel-button-right">&gt;</button>
        </div>
    </main>
</template>

<style scoped>
select option[value=''][disabled] {
    display: none;
}
label {
    margin: 10px;
}
</style>
