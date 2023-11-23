<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
import { LoginInfo } from '../store/login';

const route = useRoute();

const attr = ref([]);
if (history.state.attr != null) {
    attr.value.push(JSON.parse(history.state.attr));
}
const groupno = ref(0);
if (history.state.groupno != null) {
    groupno.value = history.state.groupno;
}

const info = LoginInfo();
const { getLoginInfo } = info;

const userinfo = ref();
const subject = ref('');
const content = ref('');
const router = useRouter();
const starscore = ref(new Array(1));

onMounted(() => {
    userinfo.value = getLoginInfo;
    if (groupno.value != 0) {
        console.log('코스 리뷰 작성 정보', groupno.value);
        courseload(groupno.value);
    } else {
        console.log('히스 위시 리뷰 정보', attr.value);
    }
});

const courseload = (groupno) => {
    let url = `/api/coursereview?userid=${getLoginInfo.userid}&groupno=${groupno}`;
    axios.get(url).then((response) => {
        attr.value = response.data.resmsg;
        starscore.value = new Array(attr.value.length);
    });
};

const BoardWrite = () => {
    if (subject.value == '' || content.value == '') {
        alert('작성 내용 확인');
        return false;
    }

    let contentid = [];
    attr.value.forEach((data) => {
        contentid.push(data.content_id);
    });

    console.log(starscore.value);
    console.log(subject.value, content.value, userinfo.value.userid, contentid);

    const multipartFile = new FormData();

    for (let index = 0; index < files.value.length; index++) {
        multipartFile.append('multipartFile', files.value[index]);
    }

    multipartFile.append('userid', userinfo.value.userid);
    multipartFile.append('subject', subject.value);
    multipartFile.append('content', content.value);
    multipartFile.append('starscore', starscore.value);
    multipartFile.append('contentid', contentid);
    multipartFile.append('groupno', groupno.value);

    var url = '/api/reviewboardwrite';

    for (let key of multipartFile.entries()) {
        console.log(`${key}`);
    }

    async function writeArticle(url) {
        console.log('MULTI ' + multipartFile);
        const response = await axios.post(url, multipartFile, { headers: { 'Content-Type': 'multipart/form-data' } });
        console.log(response);
    }

    writeArticle(url).catch((error) => {
        console.log(error);
    });

    setTimeout(golist, 1000);
};

const golist = () => {
    router.push('/reviewboardlist');
};

const files = ref([]);

const fileschange = (e) => {
    files.value = e.target.files;
    console.log(files.value);
};
</script>

<template>
    <main>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-8 col-md-10 col-sm-12">
                    <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                        <mark class="sky">리뷰 쓰기</mark>
                    </h2>
                </div>
                <div class="row justify-content-center">
                    <template v-for="(at, index) in attr" :key="index">
                        <a-card hoverable style="width: 300px; padding: 10px; margin-left: 5px; margin-right: 5px">
                            <template #cover>
                                <img
                                    :alt="at.title"
                                    :src="at.first_image != '' ? at.first_image : '/src/assets/images/Recrip_wide.JPG'"
                                />
                            </template>
                            <p><a-rate v-model:value="starscore[index]" allow-half /></p>
                            <a-card-meta :title="at.title">
                                <template #description>
                                    {{ at.addr1 }}
                                </template>
                            </a-card-meta>
                        </a-card>
                    </template>
                </div>
                <div class="col-lg-8 col-md-10 col-sm-12">
                    <form id="form-register" method="POST" action="">
                        <input type="hidden" id="nowid" value="${userinfo.userid}" />
                        <div class="mb-3">
                            <label for="subject" class="form-label">제목 : </label>
                            <input
                                type="text"
                                class="form-control"
                                id="subject"
                                name="subject"
                                placeholder="제목..."
                                v-model="subject"
                            />
                        </div>
                        <div class="mb-3">
                            <label for="content" class="form-label">내용 : </label>
                            <textarea
                                class="form-control"
                                id="content"
                                name="content"
                                rows="7"
                                v-model="content"
                            ></textarea>
                        </div>
                        <div>
                            <label for="upload" class="form-label">이미지 업로드 : </label>
                            <input
                                type="file"
                                id="upload"
                                name="upload"
                                multiple
                                @change="fileschange"
                                accept="image/*"
                            />
                        </div>
                        <div class="col-auto text-center">
                            <button
                                type="button"
                                id="btn-register"
                                class="btn btn-outline-primary mb-3"
                                @click="BoardWrite()"
                            >
                                글작성
                            </button>
                            <button type="reset" class="btn btn-outline-danger mb-3">초기화</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </main>
</template>

<style scoped></style>
