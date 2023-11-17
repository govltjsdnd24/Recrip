<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import BoardListItem from '../components/common/BoardListItem.vue';
import PageNavigation from '../components/common/PageNavigation.vue';
import VSelect from '../components/common/VSelect.vue';
import { LoginInfo } from '../store/login';
const info = LoginInfo();
const { isLogin, loginInfo, IsLogin, getLoginInfo, setLogOut, setLoginInfo } = info;
const articles = ref();
const userinfo = ref();
const currentpage = ref(1);
const totalpage = ref();
onMounted(() => {
    getArticleList();
    userinfo.value = getLoginInfo;
});

const getArticleList = () => {
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

const onPageChange = (val) => {
    console.log(val + '번 페이지로 이동 준비 끝!!!');
    param.value.pgno = val;
    currentpage.value = val;
    getArticleList();
};

const selectOption = ref([
    { text: '검색조건', value: '' },
    { text: '글번호', value: 'articleno' },
    { text: '제목', value: 'subject' },
    { text: '작성자아이디', value: 'userid' },
]);

const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;

const param = ref({
    pgno: currentpage.value,
    spp: VITE_ARTICLE_LIST_SIZE,
    key: '',
    word: '',
    table: 'freeboard',
});

const changeKey = (val) => {
    console.log('BoarList에서 선택한 조건 : ' + val);
    param.value.key = val;
};
</script>

<template>
    <div>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-8 col-md-10 col-sm-12">
                    <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                        <span>글목록</span>
                    </h2>
                </div>
                <div class="col-lg-8 col-md-10 col-sm-12">
                    <div class="row align-self-center mb-2">
                        <template v-if="IsLogin == true">
                            <div class="col-md-2 text-start">
                                <router-link :to="{ name: 'BoardWrite' }">
                                    <button type="button" class="btn btn-dark btn-sm">글쓰기</button>
                                </router-link>
                            </div>
                        </template>
                        <div class="col-md-5 offset-5">
                            <form class="d-flex">
                                <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
                                <div class="input-group input-group-sm">
                                    <input
                                        type="text"
                                        class="form-control"
                                        v-model="param.word"
                                        placeholder="검색어..."
                                    />
                                    <button class="btn btn-dark" type="button" @click="getArticleList">검색</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <table class="table table-hover">
                        <thead>
                            <tr class="text-center">
                                <th scope="col">글번호</th>
                                <th scope="col">제목</th>
                                <th scope="col">작성자</th>
                                <th scope="col">조회수</th>
                                <th scope="col">작성일</th>
                            </tr>
                        </thead>
                        <tbody>
                            <BoardListItem
                                v-for="article in articles"
                                :key="article.articleno"
                                :article="article"
                            ></BoardListItem>
                        </tbody>
                    </table>
                </div>
                <table class="text-center">
                    <tr>
                        <PageNavigation
                            :current-page="currentpage"
                            :total-page="totalpage"
                            @pageChange="onPageChange"
                        ></PageNavigation>
                    </tr>
                </table>
                <form id="form-param" method="get" action="${root }/board">
                    <input type="hidden" id="p-pgno" name="pgno" value="" />
                </form>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
