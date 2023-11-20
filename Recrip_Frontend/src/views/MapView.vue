<script setup>
import axios from 'axios';
import { onMounted, ref, h } from 'vue';
import { SettingOutlined, EditOutlined, EllipsisOutlined } from '@ant-design/icons-vue';
import { SearchOutlined } from '@ant-design/icons-vue';
import { LoginInfo } from '../store/login';
const info = LoginInfo();
const { isLogin, loginInfo, IsLogin, getLoginInfo, } = info;

const serviceKey = 'YmLOCrrmdh8DGzVmGbT0Xu9jfrsXGgdJ6GsHZ%2FDGzKW82R5KrnlJU0dar8fqQ0nYXpb9fjuYjDQx1QuSigbTkw%3D%3D';
const areaUrl =
    'https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=' +
    serviceKey +
    '&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json';

//const src =
//    '//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=4fd0aaf2d6c6b86333a1b8f0115bac76&libraries=services,clusterer,drawing';

const src =
    '//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=068c718ed6d3a38278cc1820746952f0&libraries=services,clusterer,drawing';


var map = ref(null);
const mapContainer = ref(null);

const sido_code = ref([{}]);
const gugun_code = ref([{}]);
const carousel = ref([]);

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
    if (polyline.value != null) {
        polyline.value.setMap(null);
    }
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
          content_id: area.content_id,
          img: area.first_image,
        };
        positions.push(markerInfo);
    });
    displayMarker();
}



function displayMarker() {
    // 마커 이미지의 이미지 주소입니다

    for (let i = 0; i < positions.length; i++) {
        // 마커 이미지의 이미지 크기 입니다
        let imageSize = new kakao.maps.Size(40, 40);
        let marker = '';
        let markertxt = '';
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
        let markerImage = new kakao.maps.MarkerImage(marker, imageSize);

        // 마커를 생성합니다
        let markerobject = new kakao.maps.Marker({
          map: map, // 마커를 표시할 지도
          position: positions[i].latlng, // 마커를 표시할 위치
          title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: markerImage, // 마커 이미지
        });

        markers.value.push(markerobject);

      var closeBtn = document.createElement('button');
      closeBtn.appendChild(document.createTextNode('닫기'));
      // closeBtn.setAttribute('data-bs-toggle', "offcanvas");
      // closeBtn.setAttribute('data-bs-target',"#offcanvasScrolling");
      // closeBtn.setAttribute('aria-controls',"offcanvasScrolling");

          // 닫기 이벤트 추가
      closeBtn.onclick = function () {
        overlay.setMap(null);
      };

      var addBtn = document.createElement('button');
      addBtn.appendChild(document.createTextNode('찜'));

        addBtn.onclick = function () {
            //찜 누름
            selectspot.value.push(positions[i]);
            console.log(positions[i].latlng);
            let url = `/api/addscore?content_id=${positions[i].content_id}`;
            axios.get(url).then(response => console.log(response)).catch(error => console.log(error));

            if (getLoginInfo.userid != null) {
                let url2 = '/api/wishinsert';
                axios.post(url2, {
                    userid: getLoginInfo.userid,
                    contentid: positions[i].content_id
                }).then(response => console.log(response)).catch(error => console.log(error))
            }
          
        overlay.setMap(null);
      };

      let content = document.createElement('div');
      content.setAttribute('class', 'card');

      let cardtitle = document.createElement('div');
      cardtitle.setAttribute('class', 'card-title');
      cardtitle.appendChild(document.createTextNode("관광지"));

      content.appendChild(cardtitle);
      content.appendChild(addBtn);
      content.appendChild(closeBtn);

      let cardtext = document.createElement('div');
      cardtext.setAttribute('class', 'card-text');
      cardtext.appendChild(document.createTextNode('이름 : ' + positions[i].title));
      content.appendChild(cardtext);

      let ul = document.createElement('ul');
      let li = document.createElement('li');
      li.setAttribute('class', 'up');
      ul.appendChild(li);

      let span1 = document.createElement('span');
      span1.setAttribute('class', 'card-text');
      span1.appendChild(document.createTextNode('종류 : ' + markertxt));
      li.appendChild(span1);

      let li2 = document.createElement('li');
      let span2 = document.createElement('span');
      span2.setAttribute('class', 'card-text');
      span2.appendChild(document.createTextNode('주소 : ' + positions[i].addr1));
      li2.appendChild(span2);

      ul.appendChild(li2);
      content.appendChild(ul);

        let overlay = new kakao.maps.CustomOverlay({
          map: map,
          position: positions[i].latlng,
          content: content,
          yAnchor: 1,
        });

        overlay.setMap(null);

      

      kakao.maps.event.addListener(markerobject, 'click', function () {
          overlay.setMap(map);
          if (getLoginInfo.userid != null) {
              let url = '/api/historyinsert';
              axios.post(url, {
                  userid: getLoginInfo.userid,
                  contentid: positions[i].content_id
              }).then(response => console.log(response)).catch(error => console.log(error))
          }
      });

      overlays.value.push(overlay);
    }

  console.log(markers.value);
  console.log(overlays.value);

    for (let index = 0; index < markers.value.length; index++) {
      
    }
    // 커스텀 오버레이를 생성합니다

    // 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
    map.setCenter(positions[0].latlng);
    map.setLevel(6);

    const closeOverlay = () => {
        console.log(123123)
    }
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

const selectspot = ref([]);
const selectcourse = ref([]);

const selectdelete = (index) => {
  selectspot.value.splice(index, 1);
}

const selectadd = (index) => {
  console.log(selectspot.value[index]);
  selectcourse.value.push(selectspot.value[index]);
    //계획 추가
    let url = `/api/addscore?content_id=${selectspot.value[index].content_id}`;
    axios.get(url).then(response => console.log(response)).catch(error => console.log(error));
}

const coursedelete = (index) => {
    selectcourse.value.splice(index, 1);
}

const polyline = ref();

const coursesave = () => {
    let course = [];
    selectcourse.value.forEach((attr) => {
        let c = {
            content_id: attr.content_id,
            userid: getLoginInfo.userid
        };
        course.push(c);
    });
    console.log(course);
    let url = '/api/courseinsert';
    axios.post(url, {
        headers: {
            "Content-Type": "application/json",
        }, dto: JSON.stringify(course)
    }).then(response => {
        if (response.data.resdata == 1) {
            alert("여행 계획을 저장했습니다. 해당 계획을 지도에 출력합니다.");
        }
    }).catch(error => console.log(error));

    let mobility = 'https://apis-navi.kakaomobility.com/v1/waypoints/directions';
    let key = '935d83ed14edef82a34131e921e9f2bd';

    let waypoints = [];
    console.log(selectcourse.value);

    for (let i = 1; i < selectcourse.value.length - 1; i++) {
        let way = {
            "name": selectcourse.value[i].title,
            "x": selectcourse.value[i].latlng.La,
            "y": selectcourse.value[i].latlng.Ma
        }
        waypoints.push(way);
    }

    console.log(waypoints);

    axios.post(mobility, {
        "origin": {
            "name": selectcourse.value[0].title,
            "x": selectcourse.value[0].latlng.La,
            "y": selectcourse.value[0].latlng.Ma
        },
        "destination": {
            "name":selectcourse.value[selectcourse.value.length-1].title,
            "x": selectcourse.value[selectcourse.value.length-1].latlng.La,
            "y": selectcourse.value[selectcourse.value.length-1].latlng.Ma
        },
        "waypoints": waypoints,
        "priority": "RECOMMEND",
        "car_fuel": "GASOLINE",
        "car_hipass": false,
        "alternatives": false,
        "road_details": false
    }, {
        headers: {
            "Content-Type": 'application/json',
            "Authorization": `KakaoAK ${key}`
        }
    }).then(response => {
        console.log(response);
        if (polyline.value != null) {
            polyline.value.setMap(null);
        }
        markers.value.forEach((marker) => {
            marker.setMap(null);
        });

        console.log("마커", markers.value[0]);

        for (let i = 0; i < selectcourse.value.length; i++) {
            for (let j = 0; j < markers.value.length; j++) {
                if (selectcourse.value[i].title == markers.value[j].Gb) {
                    markers.value[j].setMap(map);
                    break;
                }
            }
        }

        var sections = response.data.routes[0].sections;
        console.log(response.data.routes[0].sections[0].roads);
        var linepath = [];

        sections.forEach(roads => {
            roads.roads.forEach((road) => {
                for (let i = 0; i < road.vertexes.length; i += 2) {
                    linepath.push(new kakao.maps.LatLng(road.vertexes[i + 1], road.vertexes[i]));
                }
            })
        })
        
        console.log(linepath);

        // 지도에 표시할 선을 생성합니다
        polyline.value = new kakao.maps.Polyline({
            path: linepath, // 선을 구성하는 좌표배열 입니다
            strokeWeight: 5, // 선의 두께 입니다
            strokeColor: '#000000', // 선의 색깔입니다
            strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle: 'solid' // 선의 스타일입니다
        });

        // 지도에 선을 표시합니다 
        polyline.value.setMap(map); 

        map.setCenter(linepath[0]);
        map.setLevel(3);
    });

    

        
}


</script>

<template>
    <main>
        <!-- title -->
        <h1 class="map-title">우리동네 구석구석</h1>

        <!-- address options -->
        <div class="map-select-list">
          <button class="btn btn-info" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling" style="margin: 30px;">찜목록</button>
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
            <a-button type="primary" :icon="h(SearchOutlined)" @click="Search">Search</a-button>
          <button class="btn btn-info" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrollingend" aria-controls="offcanvasScrollingend" style="margin: 30px;">여행 계획</button>
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
        <div>
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
    

    <div class="offcanvas offcanvas-start" data-bs-scroll="true" data-bs-backdrop="false" id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel">
      <div class="offcanvas-header">
        <h1>찜 목록</h1>
        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
      </div>
      <div class="offcanvas-body">
        <div v-for="(data , index) in selectspot.slice().reverse()" :key="data.content_id" style="margin: 20px;">
          <a-card hoverable style="width: 100%">
            <template #cover>
              <img alt="no image" :src="data.img"/>
            </template>
            <template #actions>
              <button @click="selectadd(selectspot.length - index - 1)">일정 추가</button>
              <button @click="selectdelete(selectspot.length - index - 1)">삭제</button>
            </template>
            <p><a-rate :value="data.content_type_id" allow-half disabled/></p>
            <a-card-meta :title="data.title" :description="data.addr1"></a-card-meta>
          </a-card>
        </div>
      </div>
    </div>

    <div class="offcanvas offcanvas-end" data-bs-scroll="true" data-bs-backdrop="false" id="offcanvasScrollingend" aria-labelledby="offcanvasScrollingLabel">
      <div class="offcanvas-header">
        <h1>여행 계획</h1>
        <p><button class="btn btn-primary" @click="coursesave">여행 계획 저장</button></p>
        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
      </div>
      <div class="offcanvas-body">
        <div v-for="data , index in selectcourse" :key="data.content_id" style="margin: 20px;">
          
          <a-card hoverable style="width: 100%">
            <template #cover>
              <img alt="no image" :src="data.img" />
            </template>
            <template #actions>
                <button @click="coursedelete(index)">삭제</button>
            </template>
            <a-card-meta :title="data.title" :description="data.addr1"></a-card-meta>
          </a-card>
          
        </div>
      </div>
    </div>
</template>

<style scoped>
select option[value=''][disabled] {
    display: none;
}
label {
    margin: 10px;
}
</style>
