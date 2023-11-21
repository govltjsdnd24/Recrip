<script setup>
import PageNavigation from '../components/common/PageNavigation.vue';
import ReviewBoardListItem from '../components/common/ReviewBoardListItem.vue';
import { ref, onMounted } from 'vue';
import { LoginInfo } from '../store/login';
import VSelect from '../components/common/VSelect.vue';
import axios from 'axios';
const info = LoginInfo();
const { isLogin, loginInfo, IsLogin, getLoginInfo } = info;

const articles = ref();
const userinfo = ref();
const currentpage = ref(1);
const totalpage = ref();

onMounted(() => {
    getReviews();
    userinfo.value = getLoginInfo;
});

const onPageChange = (val) => {
    console.log(val + '번 페이지로 이동 준비 끝!!!');
    param.value.pgno = val;
    currentpage.value = val;
    console.log('CURRENT: ', currentpage.value);
    getReviews();
};

const selectOption = ref([
    { text: '검색조건', value: '' },
    { text: '제목', value: 'subject' },
    { text: '작성자아이디', value: 'userid' },
]);

const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;

const getReviews = function () {
    var url = '/api/boardlist';

    async function getTodo(url) {
        const response = await axios.get(url, { params: param.value });
        console.log(response);
        articles.value = response.data.resdata.list;
        totalpage.value = response.data.resdata.totalpage;
    }
    getTodo(url).catch((error) => {
        console.log(error);
    });
};

const param = ref({
    pgno: currentpage.value,
    spp: 9,
    key: '',
    word: '',
    table: 'reviewboard',
});

const changeKey = (val) => {
    console.log('BoarList에서 선택한 조건 : ' + val);
    param.value.key = val;
};
</script>

<template>
    <div class="row mt-0">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-8 col-md-10 col-sm-12" style="margin-bottom: 0px; margin-top: 0px">
                    <h2 class="my-3 py-3 shadow-sm bg-light text-center" style="margin-bottom: 0px; margin-top: 0px">
                        <span>리뷰</span>
                    </h2>
                </div>
                <div class="col-lg-8 col-md-10 col-sm-12">
                    <div class="row align-self-center mb-2">
                        <div
                            class="col-md-5 offset"
                            style="margin-left: 60%; margin-top: 0px; padding-top: 0px; margin-bottom: 0px"
                        >
                            <form class="d-flex col">
                                <VSelect
                                    :selectOption="selectOption"
                                    @onKeySelect="changeKey"
                                    style="height: 30px; margin-top: 20px"
                                />
                                <div class="input-group input-group-sm">
                                    <input
                                        type="text"
                                        class="form-control"
                                        v-model="param.word"
                                        placeholder="검색어..."
                                    />
                                    <button class="btn btn-dark" type="button" @click="getReviews">검색</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="row">
                        <ReviewBoardListItem v-for="article in articles" :key="article.articleno" :article="article">
                        </ReviewBoardListItem>
                    </div>

                    <div class="row">
                        <table class="text-center">
                            <PageNavigation
                                :current-page="currentpage"
                                :total-page="totalpage"
                                @pageChange="onPageChange"
                            ></PageNavigation>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
div {
    margin: 20px;
}
</style>
