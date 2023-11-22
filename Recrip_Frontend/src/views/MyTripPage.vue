<script setup>
import PageNavigation from '../components/common/PageNavigation.vue';
import { ref, onBeforeMount } from 'vue';
import axios from 'axios';
import { LoginInfo } from '../store/login';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();
const info = LoginInfo();
const { isLogin, loginInfo, IsLogin, getLoginInfo, setLogOut, setLoginInfo } = info;

const type = ref('');

const total = ref();
const current = ref(1);
const list = ref([]);
const isShown = ref(false);
const group = ref();
const index = [1, 2, 3, 4, 5];
const title = ref('');

onBeforeMount(() => {
    if (getLoginInfo.userid == null) {
        alert('로그인이 필요합니다.');
        router.push('/');
    }
});

const HistoryLoad = () => {
    isShown.value = true;
    title.value = '내가 본 여행지 목록';
    if (type.value != 'history') {
        current.value = 1;
    }
    type.value = 'history';
    var url = '/api/historylist';
    axios
        .get(url, {
            params: {
                userid: getLoginInfo.userid,
                current: current.value,
                size: 10,
                table: 'historylist',
            },
        })
        .then((response) => {
            console.log(response);
            list.value = response.data.resmsg;
            total.value = response.data.totalpage;
            console.log(list.value);
        });
};

const WishLoad = () => {
    isShown.value = true;
    title.value = '찜 목록';
    if (type.value != 'wish') {
        current.value = 1;
    }
    type.value = 'wish';
    var url = '/api/wishlist';
    axios
        .get(url, {
            params: {
                userid: getLoginInfo.userid,
                current: current.value,
                size: 10,
                table: 'wishlist',
            },
        })
        .then((response) => {
            list.value = response.data.resmsg;
            total.value = response.data.totalpage;
        });
};

const CourseLoad = () => {
    isShown.value = true;
    title.value = '코스 목록';
    if (type.value != 'course') {
        current.value = 1;
    }
    type.value = 'course';
    var url = '/api/courselist';
    axios
        .get(url, {
            params: {
                userid: getLoginInfo.userid,
                current: current.value,
                size: 5,
                table: 'courselist',
            },
        })
        .then((response) => {
            let l = [];
            let index = response.data.resgroup[0];
            list.value = [];
            for (let i = 0; i < response.data.resgroup.length; i++) {
                if (index == response.data.resgroup[i]) {
                    l.push(response.data.resmsg[i]);
                } else {
                    list.value.push({ data: l, groupno: index });
                    l = [];
                    l.push(response.data.resmsg[i]);
                    index = response.data.resgroup[i];
                }
            }

            list.value.push({ data:l, groupno:index });

            console.log('코스리스트', list.value);
            // list.value = response.data.resmsg;
            // group.value = response.data.resgroup;
            total.value = response.data.totalpage;
        });
};

const onPageChange = (val) => {
    current.value = val;
    switch (type.value) {
        case 'history':
            HistoryLoad();
            break;
        case 'wish':
            WishLoad();
            break;
        case 'course':
            CourseLoad();
            break;
    }
    var accordion = document.querySelectorAll('.accordion-collapse');
    accordion.forEach(element => {
        console.log(element);
        element.setAttribute('class', 'accordion-collapse collapse');
    });
    var button = document.querySelectorAll('.accordion-button');
    button.forEach(element => {
        console.log(element);
        element.setAttribute('class', 'accordion-button collapsed');
    });
};

const reviewwrite = (attr) => {
    console.log(attr);
    router.push({ name: 'ReviewBoardWrite', state: { attr: JSON.stringify(attr) } });
};

const deleteattr = (index) => {
    console.log(index);
};

const reviewcoursewrite = (groupno) => {
    console.log(groupno);
    router.push({ name: 'ReviewBoardWrite', state: { groupno: groupno } });
};
</script>

<template>
    <div class="row justify-content-center">
        <div style="margin-top: 0px; margin-bottom: 0px">
            페이지 전환
            <a-button type="dashed" style="margin: 20px" @click="HistoryLoad">내가 본 여행지 목록</a-button>
            <a-button type="dashed" style="margin: 20px" @click="WishLoad">찜 목록</a-button>
            <a-button type="dashed" style="margin: 20px" @click="CourseLoad">코스 목록</a-button>
        </div>
        <h5 v-if="isShown == false" style="text-align: center">보고 싶은 페이지를 선택해주세요.</h5>

        <template v-if="title != ''">
            <h1 style="text-align: center; margin-bottom: 0px">{{ title }}</h1>
        </template>

        <div class="row">
            <template v-if="type != 'course'">
                <template v-for="(attr, index) in list" :key="index">
                    <div class="col-2">
                        <a-card hoverable style="max-width: 300px; margin: 0px">
                            <template #cover>
                                <img
                                    :alt="attr.title"
                                    :src="
                                        attr.first_image != '' ? attr.first_image : '/src/assets/images/Recrip_wide.JPG'
                                    "
                                    style="max-width: 300px; max-height: 300px"
                                />
                            </template>
                            <template #actions>
                                <button @click="reviewwrite(attr)">review</button>
                                <button @click="deleteattr(index)">delete</button>
                            </template>
                            <a-card-meta :title="attr.title">
                                <template #description>{{ attr.addr1 }}</template>
                            </a-card-meta>
                        </a-card>
                    </div>
                </template>
            </template>
            <template v-else>
                <div class="accordion" id="accordionExample">
                    <div class="accordion-item" v-for="(datas, index) in list" :key="index" style="margin: 0px">
                        <h2 class="accordion-header" :id="'heading' + index">
                            <button
                                class="accordion-button collapsed"
                                type="button"
                                data-bs-toggle="collapse"
                                :data-bs-target="'#collapse' + index"
                                aria-expanded="true"
                                :aria-controls="'collapse' + index"
                            >
                                {{ (current - 1) * 5 + index + 1 }}번 코스
                            </button>
                        </h2>
                        <div 
                            :id="'collapse' + index"
                            class="accordion-collapse collapse"
                            :aria-labelledby="'heading' + item"
                            data-bs-parent="#accordionExample"
                        >
                            <div class="accordion-body row flex">
                                <template
                                    v-for="(attr, index) in datas.data"
                                    :key="attr.content_id"
                                    
                                >
                                    <div class="col-2 mx-0 px-0">
                                        <a-card hoverable style="max-width: 300px; margin: 0px">
                                            <template #cover>
                                                <img
                                                    :alt="attr.title"
                                                    :src="
                                                        attr.first_image != ''
                                                            ? attr.first_image
                                                            : '/src/assets/images/Recrip_wide.JPG'
                                                    "
                                                    style="max-width: 300px; max-height: 300px"
                                                />
                                            </template>

                                            <a-card-meta :title="attr.title"> </a-card-meta>
                                        </a-card>
                                    </div>

                                    <div
                                        v-if="index != datas.data.length - 1"
                                        class="col-2 px-0"
                                        style="vertical-align: middle; width: 50px"
                                    >
                                        <img src="@/assets/images/arrow_right.png" style="width: 50px; margin: 0px" />
                                    </div>
                                </template>
                                <div class="row flex justify-content-center">
                                    <button
                                        class="col-4 btn btn-outline-secondary mx-5"
                                        @click="reviewcoursewrite(datas.groupno)"
                                    >
                                        review
                                    </button>
                                    <button class="col-4 btn btn-outline-danger mx-5" @click="deleteattr(index)">
                                        delete
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </template>
        </div>

        <template v-if="title != ''">
            <div class="row">
                <table class="text-center">
                    <PageNavigation
                        :current-page="current"
                        :total-page="total"
                        @pageChange="onPageChange"
                    ></PageNavigation>
                </table>
            </div>
        </template>
    </div>
</template>

<style scoped>
div {
    margin: 20px;
}
</style>
