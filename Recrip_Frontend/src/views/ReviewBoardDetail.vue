<script setup>
import { ref, onMounted, onBeforeMount, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import { LoginInfo } from '../store/login';

const $ = (query) => document.querySelector(query);

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
const likecount = ref(0);

const attr = ref();
const starscore = ref();

const childcomments = ref([{}]);
const commentcount = ref(0);

function modalOn(className) {
    const targetModal = $(className);
    targetModal.style.display = 'block';
}

function modalOff(className) {
    const targetModal = $(className);
    childcontent.value = '';
    targetModal.style.display = 'none';
}

onBeforeMount(() => {
    userinfo.value = getLoginInfo;
    var url = `/api/reviewboardview?articleno=${articleno}`;

    async function getArticle(url) {
        const response = await axios.get(url);
        console.log('글정보', response.data);
        article.value = response.data.resdata;
        attr.value = response.data.attr;
        starscore.value = response.data.starscore;

        console.log(attr.value, starscore.value);
    }
    getArticle(url).catch((error) => {
        console.log(error);
    });

    var comm = `/api/reviewcommentlist?articleno=${articleno}`;

    async function getComment(comm) {
        const response = await axios.get(comm);
        console.log(response);
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

    var likeurl = `/api/reviewboardlikecount?articleno=${articleno}`;
    getLikes(likeurl);

    var counturl = `/api/reviewcommentcount?articleno=${articleno}`;
    commentCount(counturl);

    var childcomm = `/api/reviewcommentchildren?articleno=${articleno}`;

    async function getChildComment(childcomm) {
        const response = await axios.get(childcomm);
        childcomments.value = response.data.resdata;
        console.log('CHILDREN', childcomments.value);
    }
    getChildComment(childcomm).catch((error) => {
        console.log(error);
    });
});

async function commentCount(url) {
    const response = await axios.get(url);
    commentcount.value = response.data.resdata;
}

async function getLikes(url) {
    const response = await axios.get(url);
    likecount.value = response.data.resmsg;
}

const likeBoard = () => {
    var url = `/api/reviewboardlike?articleno=${articleno}&userid=${getLoginInfo.userid}`;
    var likeurl = `/api/reviewboardlikecount?articleno=${articleno}`;

    async function getArticle(url) {
        const response = await axios.get(url);
        if (getLoginInfo.userid == null) {
            alert('좋아요를 누르시려면 우선 로그인이 되어있어야 합니다.');
        } else if (response.data.resmsg == '입력성공') {
            if (alert('해당 게시물이 좋아요 처리 되었습니다.')) {
            } else {
                getLikes(likeurl);
                router.go(0);
            }
        } else if (response.data.resmsg == '중복확인') alert('이미 해당 게시물에 좋아요를 하셨습니다.');
        else alert('좋아요 실패');
    }
    getArticle(url).catch((error) => {
        alert('좋아요 실패');
        console.log(error);
    });
    getLikes(likeurl);
    router.push(0);
};

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

const childcontent = ref('');
const parentcomment = ref(0);
const parentdepth = ref(0);
const parentcontent = ref('');
const parentuserid = ref('');

const CommentChild = function (comment) {
    modalOn('.modal-comment');
    parentcomment.value = comment.commentno;
    parentdepth.value = comment.depth;
    parentcontent.value = comment.content;
    parentuserid.value = comment.userid;
};

const CommentChildWrite = () => {
    var url = '/api/reviewcommentchildwrite';
    let updated_depth = Number(parentdepth.value) + 1;
    async function articleWrite(url) {
        const response = await axios.post(url, {
            articleno: articleno,
            userid: userinfo.value.userid,
            content: childcontent.value,
            parentcomment: parentcomment.value,
            depth: updated_depth,
        });
    }
    articleWrite(url).catch((error) => {
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

const getImageSource = (filename) => {
    console.log('FILENOM: ' + filename);
    return 'http://192.168.0.3:8080/trip/download?fileName=' + filename;
};

watch(files, (files) => {
    files.forEach((file) => {
        getImageSource(file);
    });
});

const gotomap = (groupno , content_id) => {
    console.log(groupno, content_id);
    if (groupno != 0) {
        router.push({ name: 'map', state: { groupno: groupno } });
    } else {
        router.push({ name: 'map', state: { content_id: content_id } });
    }
    
}
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
                    <div class="row justify-content-center" style="margin-bottom: 20px">
                        <template v-for="(at, index) in attr" :key="index">
                            <a-card hoverable style="width: 300px">
                                <template #cover>
                                    <img :alt="at.title" :src="at.first_image" />
                                </template>
                                <p><a-rate :value="starscore[index].starscore / 10" allow-half disabled /></p>
                                <a-card-meta :title="at.title">
                                    <template #description>
                                        {{ at.addr1 }}
                                    </template>
                                </a-card-meta>
                            </a-card>
                        </template>
                    </div>
                    <div style="text-align:center; margin: 10px;">
                        <button class="btn btn-primary" type="button" @click="gotomap(article.groupno,attr[0].content_id)">맵에서 보기</button>
                    </div>
                    <div class="row">
                        <div class="col-md-8">
                            <div class="clearfix align-content-center">
                                <img
                                    class="avatar me-2 float-md-start bg-light p-2"
                                    src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
                                />
                                <p>조회: {{ article.hit }} &nbsp; 추천: {{ article.likes }}</p>
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
                            <img :src="file.url" style="max-width: 500px" />
                        </div>
                        <div class="d-flex justify-content-end mb-2 mr-2">
                            좋아요 &nbsp;
                            <img
                                src="@/assets/images/heart.png"
                                style="width: 30px; cursor: pointer"
                                @click="likeBoard"
                            />
                        </div>
                        <div class="d-flex justify-content-end">
                            <router-link to="/reviewboardlist">
                                <button type="button" class="btn btn-dark mb-3">글목록</button>
                            </router-link>
                            <template v-if="userinfo.userid == article.userid">
                                <router-link
                                    :to="{ name: 'ReviewBoardModify', params: { articleno: article.articleno } }"
                                >
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
                    <hr />
                    <h4>댓글 ({{ commentcount }})</h4>
                    <ul class="list-group">
                        <template v-if="commentcount > 0">
                            <li class="list-group-item pt-0 pb-0" v-for="comment in comments" :key="comment.commentno">
                                <div
                                    class="media mt-3 mb-3"
                                    :style="{
                                        marginLeft: (comment.depth < 5 ? comment.depth : 4) * 100 + 'px',
                                    }"
                                >
                                    <template v-if="comment.depth != 0">
                                        <img
                                            src="/src/assets/images/down_right.png"
                                            style="width: 30px; height: 20px; margin-left: -50px"
                                        />
                                    </template>
                                    <div class="media-body">
                                        <div class="row">
                                            <h5 class="mt-0 col">{{ comment.userid }}</h5>
                                            <div class="d-flex justify-content-end col">
                                                <template v-if="IsLogin == true">
                                                    <button
                                                        type="button"
                                                        class="btn btn-outline-dark ms-1"
                                                        id="btn-cm-delete"
                                                        @click="CommentChild(comment)"
                                                    >
                                                        댓글추가
                                                    </button>
                                                    <template v-if="userinfo.userid == comment.userid">
                                                        <button
                                                            type="button"
                                                            class="btn btn-outline-danger ms-1"
                                                            id="btn-cm-delete"
                                                            @click="CommentDelete(comment.commentno)"
                                                        >
                                                            댓글삭제
                                                        </button>
                                                    </template>
                                                </template>
                                            </div>
                                        </div>
                                        <p>{{ comment.content }}</p>
                                        <p>{{ comment.registdate }}</p>
                                    </div>
                                </div>
                                <div
                                    class="modal modal-wrap modal-comment"
                                    tabindex="-1"
                                    role="dialog"
                                    aria-labelledby="myLargeModalLabel"
                                    aria-hidden="true"
                                >
                                    <div class="modal1 modal-dialogue">
                                        <header class="modal-header">
                                            <h2>답글 작성</h2>
                                            <button class="modal-close-btn" @click="modalOff('.modal-comment')">
                                                X
                                            </button>
                                        </header>
                                        {{ parentuserid }}: {{ parentcontent }} 의 대한 답글
                                        <div class="modal-input-wrap">
                                            <label class="modal-label" for="modal-pw">내용</label>
                                            <textarea
                                                class="modal-input"
                                                id="content"
                                                name="content"
                                                v-model="childcontent"
                                                style="width: 370px; height: 100px"
                                            >
                                        내용</textarea
                                            >
                                        </div>
                                        <div class="modal-input-wrap" style="justify-content: center">
                                            <button @click="CommentChildWrite()" class="modal-submit">확인</button>
                                            <button class="modal-cancel but" @click="modalOff('.modal-comment')">
                                                취소
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </template>
                    </ul>
                </div>
            </div>
        </div>
    </main>
</template>

<style scoped></style>
