<script setup>
import { ref, onMounted, onBeforeMount, watch} from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import { LoginInfo } from '../store/login';

const info = LoginInfo();
const { getLoginInfo, IsLogin } = info;
const route = useRoute();
const router = useRouter();
const userinfo = ref({});
const { articleno } = route.params;

const article = ref({});
const comments = ref([{}]);
const files = ref([{}]);
const comment = ref('');

onBeforeMount(() => {
    userinfo.value = getLoginInfo;
    var url = `/api/reviewboardview?articleno=${articleno}`;

    async function getArticle(url) {
        const response = await axios.get(url);
        console.log(response.data.resdata);
        article.value = response.data.resdata;
    }
    getArticle(url).catch((error) => {
        console.log(error);
    });

    var comm = `/api/reviewcommentlist?articleno=${articleno}`;

    async function getComment(comm) {
        const response = await axios.get(comm);
        console.log(response.data.resdata);
        comments.value = response.data.resdata;
    }
    getComment(comm).catch((error) => {
        console.log(error);
    });

    var filelist = `/api/reviewboardfilelist?articleno=${articleno}`;

    async function getfiles(filelist) {
        const response = await axios.get(filelist);
        console.log(response.data.resdata);
        files.value = response.data.resdata;
    }
    getfiles(filelist).catch((error) => {
        console.log(error);
    });

    console.log(comments.value);
});

const DeleteBoard = () => {
    var url = `/api/reviewboarddelete?articleno=${articleno}`;

    async function getArticle(url) {
        const response = await axios.delete(url);
    }
    getArticle(url).catch((error) => {
        console.log(error);
    });

    setTimeout(golist, 100);
};

const golist = () => {
    router.push('/reviewlist');
};

const CommentWrite = () => {
    var url = '/api/reviewcommentwrite';

    async function articleUpdate(url) {
        const response = await axios.post(url, {
            articleno: articleno,
            userid: userinfo.value.userid,
            content: comment.value,
        });
    }
    articleUpdate(url).catch((error) => {
        console.log(error);
    });

    setTimeout(gozero, 100);
};

const gozero = () => {
    router.go(0);
};

const CommentDelete = (commentno) => {
    var url = `/api/reviewcommentdelete?commentno=${commentno}`;

    async function CommentDelete(url) {
        const response = await axios.delete(url);
    }
    CommentDelete(url).catch((error) => {
        console.log(error);
    });

    setTimeout(gozero, 100);
};


    const getImageSource =(filename) =>{
        console.log("FILENOM: "+filename);
        return 'http://192.168.0.3:8080/trip/download?fileName=' + filename;
    };

    watch(files,(files)=>{
        files.forEach(file => {
            getImageSource(file);
        });
    })


</script>

<template>
    <main>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-8 col-md-10 col-sm-12">
                    <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                        <span id="subject">{{ article.subject }}</span>
                    </h2>
                </div>
                <div class="col-lg-8 col-md-10 col-sm-12">
                    <div class="row">
                        <div class="col-md-8">
                            <div class="clearfix align-content-center">
                                <img
                                    class="avatar me-2 float-md-start bg-light p-2"
                                    src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
                                />
                                <p>
                                    <span class="fw-bold" id="user">{{ article.userid }}</span> <br />
                                    <span class="text-secondary fw-light" id="date"> {{ article.date }} </span>
                                </p>
                            </div>
                        </div>

                        <div class="text-secondary" id="content">
                            {{ article.content }}
                        </div>
                        <!-- <div class="divider mt-3 mb-3"></div> -->
                        <br />
                        <br />
                        <hr />
                        <br />
                        <br />
                        <br />
                        <div><h3>첨부 이미지</h3></div>
                        <br />
                        <div v-for="file in files" :key="file.filename">
                            <img :src="file.url" style="max-width: 500px;"/>
                        </div>
                        <div class="d-flex justify-content-end">
                            <router-link to="/reviewboardlist">
                                <button type="button" class="btn btn-dark mb-3">글목록</button>
                            </router-link>
                            <template v-if="userinfo.userid == article.userid">
                                <router-link :to="{ name: 'ReviewBoardModify', params: { articleno: article.articleno } }">
                                    <button type="button" class="btn btn-secondary mb-3 ms-1">글수정</button>
                                </router-link>
                                <button type="button" class="btn btn-danger mb-3 ms-1" @click="DeleteBoard">
                                    글삭제
                                </button>
                            </template>
                        </div>
                    </div>
                </div>

                <!-- 댓글 작성 -->
                <template v-if="IsLogin == true">
                    <form id="comment" class="col-lg-8 col-md-10 col-sm-12">
                        <div class="input-group mb-3">
                            <input type="hidden" id="articleno" :value="article.articleno" />
                            <input type="hidden" id="nowid" value="${userinfo.userid}" />
                            <span class="input-group-text">{{ userinfo.userid }}</span>
                            <div class="form-floating">
                                <textarea
                                    class="form-control"
                                    placeholder="Leave a comment here"
                                    id="floatingTextarea"
                                    style="height: 150px"
                                    v-model="comment"
                                ></textarea>
                                <label for="floatingInputGroup1">댓글</label>
                            </div>
                            <button type="button" class="btn btn-outline-dark" @click="CommentWrite">작성</button>
                        </div>
                    </form>
                </template>

                <!-- 댓글 목록 -->
                <div id="comment-area" class="col-lg-8 col-md-10 col-sm-12">
                    <ul class="list-group">
                        <li class="list-group-item" v-for="comment in comments" :key="comment.commentno">
                            <div class="media mt-3 mb-3">
                                <div class="media-body">
                                    <h3 class="mt-0">{{ comment.userid }}</h3>
                                    <h4>{{ comment.content }}</h4>
                                    <h5>{{ comment.registdate }}</h5>
                                </div>
                            </div>
                            <!-- <c:if test="${userinfo.userid eq comment.userid }"> -->
                            <template v-if="IsLogin == true">
                                <template v-if="userinfo.userid == comment.userid">
                                    <div class="d-flex justify-content-end">
                                        <button
                                            type="button"
                                            class="btn btn-outline-danger ms-1"
                                            id="btn-cm-delete"
                                            @click="CommentDelete(comment.commentno)"
                                        >
                                            댓글삭제
                                        </button>
                                    </div>
                                </template>
                            </template>
                            <!-- </c:if> -->
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </main>
</template>

<style scoped></style>
