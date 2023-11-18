<script setup>
import { onMounted, onBeforeUnmount,ref } from 'vue';
import axios from 'axios';
import cheerio from "cheerio";

var index = 0;
var timer = setInterval(displayImages, 4000);
const crawlings = ref([{}]);

onMounted(() => {
    displayImages();
    getNews();
});

function displayImages() {
    let i;
    const images = document.getElementsByClassName('image');
    for (i = 0; i < images.length; i++) {
        images[i].style.display = 'none';
    }
    index++;
    if (index > images.length) {
        index = 1;
    }
    images[index - 1].style.display = 'block';
}

function getNews() {
    axios.get('/test').then(response => {
        //console.log(response.request.response)
        const data = response.request.response;
        const $ = cheerio.load(data);
        //console.log(data);
        const result = $(".card_group > li > figure").toArray();
        //console.log(result);
        for (let index = 5; index < 14; index++) {
            let row = {
                href: result[index].children[1].attribs.href,
                alt: result[index].children[1].children[1].attribs.alt,
                src: result[index].children[1].children[1].attribs.src
            }
            crawlings.value.push(row);
        }
    })
}

onBeforeUnmount(() => {
    clearInterval(timer);
});
</script>

<template>
    <div>
        <!--슬라이더-->
        <div class="image-slideshow">
            <div class="image fadeslide">
                <img src="@/assets/images/top2.png" alt="Palm Trees" />
            </div>
            <div class="image-on-text">
                <p>최고의 여행 계획을 원하세요? </p>
                <p>Recrip</p>
                <p>에게 맡기세요.</p>
            </div>
            <div class="image fadeslide">
                <img src="@/assets/images/top.png" alt="Mountain Top" />
            </div>
            <div class="image-on-text">
                <p>최고의 여행 계획을 원하세요? </p>
                <p>Recrip</p>
                <p>에게 맡기세요.</p>
            </div>
        </div>
        <div class="wrapper">
            <div class="sidebar">
                <div class="divider"></div>
                <div class="love">
                    <p>지역사랑!</p>
                    <h2>우리 지역 관광지</h2>
                    <p>우리지역의 숨어있는 아름다운 관광지를 알려드립니다.</p>
                    <p>관광지 주변의 맛집, 숙박업소와 여행코스, 지역 축제등을 보실수도 있습니다.</p>
                </div>
                <div style="height: 16px"></div>
                <button class="custom-button">더보기..</button>
            </div>
            <div class="box wrapper2">
                <div class="main-image-container">
                    <div
                        class="center-grid-image"
                        style="background-image: url(http://tong.visitkorea.or.kr/cms/resource/25/2747925_image2_1.JPG)"
                    ></div>
                    <p>멋 진</p>
                </div>
                <div class="main-image-container">
                    <div
                        class="center-grid-image"
                        style="background-image: url(http://tong.visitkorea.or.kr/cms/resource/62/1588262_image2_1.jpg)"
                    ></div>
                    <p>곳으로</p>
                </div>
                <div class="main-image-container">
                    <div
                        class="center-grid-image"
                        style="background-image: url(http://tong.visitkorea.or.kr/cms/resource/87/2782287_image2_1.jpg)"
                    ></div>
                    <p>놀 러</p>
                </div>
                <div class="main-image-container">
                    <div
                        class="center-grid-image"
                        style="background-image: url(http://tong.visitkorea.or.kr/cms/resource/91/1944891_image2_1.jpg)"
                    ></div>
                    <p>가보자</p>
                </div>
                <div class="main-image-container">
                    <div
                        class="center-grid-image"
                        style="background-image: url(http://tong.visitkorea.or.kr/cms/resource/86/2762786_image2_1.JPG)"
                    ></div>
                    <p>야</p>
                </div>
                <div class="main-image-container">
                    <div
                        class="center-grid-image"
                        style="background-image: url(http://tong.visitkorea.or.kr/cms/resource/96/2705196_image2_1.jpg)"
                    ></div>
                    <p>호</p>
                </div>
            </div>
        </div>
        <div class="divider-container" style="background-color: #ffffff">
            <div class="divider"></div>
            <h2>나만의 여행 계획!!!</h2>
            <h3>여행 경로, 숙박, 광광지, 예상금액등 나만의 멋진 계획을 세워 공유해 주세요!!!</h3>
        </div>
        <div class="plan-wrapper">
            <div class="card">
                <img src="@/assets/images/maps/map1.png" alt="Avatar" style="width: 100%" />
                <div class="card-info">
                    <h4>
                        <b>플랜 A</b>
                    </h4>
                    <p>즐거운 여행</p>
                </div>
            </div>
            <div class="card">
                <img src="@/assets/images/maps/map2.png" alt="Avatar" style="width: 100%" />
                <div class="card-info">
                    <h4>
                        <b>플랜 B</b>
                    </h4>
                    <p>즐거운 여행</p>
                </div>
            </div>
            <div class="card">
                <img src="@/assets/images/maps/map3.png" alt="Avatar" style="width: 100%" />
                <div class="card-info">
                    <h4>
                        <b>플랜 C</b>
                    </h4>
                    <p>즐거운 여행</p>
                </div>
            </div>
            <div class="card">
                <img src="@/assets/images/maps/map4.png" alt="Avatar" style="width: 100%" />
                <div class="card-info">
                    <h4>
                        <b>플랜 D</b>
                    </h4>
                    <p>즐거운 여행</p>
                </div>
            </div>
        </div>
        <div class="divider-container">
            <div class="divider"></div>
            <h2>여행 뉴스</h2>
        </div>
        <div class="place-wrapper" > 
            <template v-for="(data, index) in crawlings" :key="data.href">
                <a-card hoverable v-if="index != 0" class="place-card">    
                    <template #cover>
                        <a :href="data.href" target="_blank">
                            <img
                                :src="data.src"
                                alt=""
                                style="width: 100%; height: 80%; object-fit: cover;"
                            />
                        </a>
                        </template>
                        <a :href="data.href" style="text-decoration: none; " >
                            <a-card-meta :title="data.alt" style="text-align: center; text-decoration: none;">
                        </a-card-meta></a>
                </a-card>
            </template>
        </div>

        
        <div class="bottom_contact">
            <p>언제든 연락주세요</p>
            <p>Recrip은 여러분의 많은 참여를 기다립니다.</p>
            <p>숨어있는 지역 명소와 함께 즐기고 싶은 여행일정이 있다면 언제든지 환영입니다.</p>
            <p>02-1234-5678</p>
        </div>
    </div>
</template>

<style scoped></style>
