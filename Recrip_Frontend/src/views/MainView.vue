<script setup>
import { onBeforeMount, onMounted, onBeforeUnmount, ref } from 'vue';
import axios from 'axios';
import cheerio from 'cheerio';

var index = 0;
var timer = setInterval(displayImages, 4000);
const crawlings = ref([{}]);
var attrRank = ref([]);
var reviewList = ref([]);
var reviewPics = ref([]);

onBeforeMount(() => {
    getNews();
    getAttrRank();
    getReviewMostLikes();
    getReviewPictures();
});
onMounted(() => {
    displayImages();
});

function getReviewMostLikes() {
    var url = '/api/reviewboardmostlikes';
    async function reviewLikes(url) {
        const response = await axios.get(url);
        reviewList.value = response.data.resmsg;
    }
    reviewLikes(url).catch((error) => {
        console.log(error);
    });
}

function getReviewPictures() {
    for (let index = 0; index < reviewList.value.length; index++) {
        let url = `/api/reviewboardfilelist?articleno=${reviewList.value[index].articleno}`;
        axios
            .get(url)
            .then((response) => {
                console.log(response.data.resdata[0]);
                if (typeof response.data.resdata[0] != 'undefined')
                    reviewPics.value[index] = response.data.resdata[0].url;
                else reviewPics.value[index] = '/src/assets/images/Recrip.JPG';
            })
            .catch((error) => {
                console.log(error);
            });
    }
}

function getAttrRank() {
    var url = '/api/attrranklist';
    async function rankList(url) {
        const response = await axios.get(url);
        attrRank.value = response.data.resdata;
    }
    rankList(url).catch((error) => {
        console.log(error);
    });
}

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
    axios.get('/test').then((response) => {
        const data = response.request.response;
        const $ = cheerio.load(data);
        const result = $('.card_group > li > figure').toArray();
        for (let index = 5; index < 14; index++) {
            let row = {
                href: result[index].children[1].attribs.href,
                alt: result[index].children[1].children[1].attribs.alt,
                src: result[index].children[1].children[1].attribs.src,
            };
            crawlings.value.push(row);
        }
    });
}

onBeforeUnmount(() => {
    clearInterval(timer);
});
</script>

<template>
    <div class="container">
        <!--슬라이더-->
        <div class="image-slideshow">
            <div class="image fadeslide">
                <img src="https://source.unsplash.com/1920x1080?beach" alt="Palm Trees" />
            </div>
            <div class="image-on-text">
                <p>최고의 여행 계획을 원하세요?</p>
                <div><p>Recrip</p></div>
                <p>에게 맡기세요.</p>
            </div>
            <div class="image fadeslide">
                <img src="https://source.unsplash.com/1920x1080?city" alt="Mountain Top" />
            </div>
            <div class="image-on-text">
                <p>최고의 여행 계획을 원하세요?</p>
                <div><p>Recrip</p></div>
                <p>에게 맡기세요.</p>
            </div>
            <div class="image fadeslide">
                <img src="https://source.unsplash.com/1920x1080?mountain" alt="Palm Trees" />
            </div>
            <div class="image-on-text">
                <p>최고의 여행 계획을 원하세요?</p>
                <div><p>Recrip</p></div>
                <p>에게 맡기세요.</p>
            </div>
            <div class="image fadeslide">
                <img src="https://source.unsplash.com/1920x1080?castle" alt="Mountain Top" />
            </div>
            <div class="image-on-text">
                <p>최고의 여행 계획을 원하세요?</p>
                <div><p>Recrip</p></div>
                <p>에게 맡기세요.</p>
            </div>
        </div>
        <div class="wrapper">
            <div class="sidebar">
                <div class="divider"></div>
                <div class="love">
                    <p></p>
                    <h2>관광지 랭킹</h2>
                    <p>지금 가장 핫한 관광지를 소개합니다!</p>
                    <p>관광지 주변의 맛집, 숙박업소와 여행코스, 지역 축제등을 보실수도 있습니다.</p>
                </div>
                <div style="height: 16px"></div>
            </div>
            <div class="box wrapper2">
                <div class="main-image-container">
                    <div
                        class="center-grid-image"
                        :style="{ backgroundImage: `url(${attrRank[0].first_image})` }"
                    ></div>
                    <p>1위: {{ attrRank[0].title }}</p>
                </div>
                <div class="main-image-container">
                    <div
                        class="center-grid-image"
                        :style="{ backgroundImage: `url(${attrRank[1].first_image})` }"
                    ></div>
                    <p>2위: {{ attrRank[1].title }}</p>
                </div>
                <div class="main-image-container">
                    <div
                        class="center-grid-image"
                        :style="{ backgroundImage: `url(${attrRank[2].first_image})` }"
                    ></div>
                    <p>3위: {{ attrRank[2].title }}</p>
                </div>
                <div class="main-image-container">
                    <div
                        class="center-grid-image"
                        :style="{ backgroundImage: `url(${attrRank[3].first_image})` }"
                    ></div>
                    <p>4위: {{ attrRank[3].title }}</p>
                </div>
                <div class="main-image-container">
                    <div
                        class="center-grid-image"
                        :style="{ backgroundImage: `url(${attrRank[4].first_image})` }"
                    ></div>
                    <p>5위: {{ attrRank[4].title }}</p>
                </div>
                <div class="main-image-container">
                    <div
                        class="center-grid-image"
                        :style="{ backgroundImage: `url(${attrRank[5].first_image})` }"
                    ></div>
                    <p>6위: {{ attrRank[5].title }}</p>
                </div>
            </div>
        </div>
        <div class="divider-container" style="background-color: #ffffff">
            <div class="divider"></div>
            <h2>인기 리뷰</h2>
            <h3>제일 핫한 후기들을 둘러보세요!</h3>
        </div>
        <div class="plan-wrapper">
            <router-link
                :to="{ name: 'ReviewBoardView', params: { articleno: reviewList[0].articleno } }"
                class="article-title link-dark"
                style="text-decoration: none"
            >
                <div class="card" style="height: 430px">
                    <img :src="reviewPics[0]" alt="Avatar" style="width: 100%" />
                    <div class="card-info">
                        <h4 class="d-inline-block text-truncate" style="max-width: 240px">
                            {{ reviewList[0].subject }}
                        </h4>
                        <p>추천수: {{ reviewList[0].likes }}</p>
                    </div>
                </div>
            </router-link>
            <router-link
                :to="{ name: 'ReviewBoardView', params: { articleno: reviewList[1].articleno } }"
                class="article-title link-dark"
                style="text-decoration: none"
            >
                <div class="card" style="height: 430px">
                    <img :src="reviewPics[1]" alt="Avatar" style="width: 100%" />
                    <div class="card-info">
                        <h4 class="d-inline-block text-truncate" style="max-width: 240px">
                            {{ reviewList[1].subject }}
                        </h4>
                        <p>추천수: {{ reviewList[1].likes }}</p>
                    </div>
                </div>
            </router-link>
            <router-link
                :to="{ name: 'ReviewBoardView', params: { articleno: reviewList[2].articleno } }"
                class="article-title link-dark"
                style="text-decoration: none"
            >
                <div class="card" style="height: 430px">
                    <img :src="reviewPics[2]" alt="Avatar" style="width: 100%" />
                    <div class="card-info">
                        <h4 class="d-inline-block text-truncate" style="max-width: 240px">
                            {{ reviewList[2].subject }}
                        </h4>
                        <p>추천수: {{ reviewList[2].likes }}</p>
                    </div>
                </div>
            </router-link>
            <router-link
                :to="{ name: 'ReviewBoardView', params: { articleno: reviewList[3].articleno } }"
                class="article-title link-dark"
                style="text-decoration: none"
            >
                <div class="card" style="height: 430px">
                    <img :src="reviewPics[3]" class="img-fluid" alt="Avatar" style="width: 100%" />
                    <div class="card-info">
                        <h4 class="d-inline-block text-truncate" style="max-width: 240px">
                            {{ reviewList[3].subject }}
                        </h4>
                        <p>추천수: {{ reviewList[3].likes }}</p>
                    </div>
                </div>
            </router-link>
        </div>
        <div class="divider-container">
            <div class="divider"></div>
            <h2>여행 뉴스</h2>
        </div>
        <div class="row">
            <template v-for="(data, index) in crawlings" :key="data.href">
                <template v-if="index != 0 && index < 7">
                    <div class="card col-4 pt-2" style="border: 0px">
                        <a :href="data.href" target="_blank" style="text-decoration: none; color: black">
                            <img class="card-img-top" :src="data.src" alt="Card image cap" />
                            <p class="card-title" style="text-align: center; text-decoration: none">{{ data.alt }}</p>
                        </a>
                    </div>
                </template>
            </template>
        </div>

        <div class="bottom_contact">
            <p></p>
            <p>Recrip과 함께 손쉽게 여행을 준비해 보세요</p>
            <p></p>
        </div>
    </div>
</template>

<style scoped></style>
