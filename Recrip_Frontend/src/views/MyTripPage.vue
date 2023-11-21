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
const list = ref();
const isShown = ref(false);
const group = ref();

onBeforeMount(() => {
    if (getLoginInfo.userid == null) {
        alert('로그인이 필요합니다.');
        router.push('/');
    }
});

const HistoryLoad = () => {
    isShown.value = true;
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
                size: 10,
                table: 'courselist',
            },
        })
        .then((response) => {
            list.value = response.data.resmsg;
            group.value = response.data.resgroup;
            total.value = response.data.totalpage;
            console.log(group.value[group.value.length-1]);
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
};

const reviewwrite = (attr) => {
    console.log(attr);
    router.push({ name: 'ReviewBoardWrite', state: { attr: JSON.stringify(attr) } });
};

const deleteattr = (index) => {
    console.log(index);
};
</script>

<template>
    <div class="row justify-content-center">
        <div style="margin-top: 0px; margin-bottom: 0px">
            페이지 전환
            <a-button type="dashed" style="margin: 20px" @click="HistoryLoad">Histroy</a-button>
            <a-button type="dashed" style="margin: 20px" @click="WishLoad">Wish</a-button>
            <a-button type="dashed" style="margin: 20px" @click="CourseLoad">Course</a-button>
        </div>
        <h5 v-if="isShown == false">보고 싶은 페이지를 선택해주세요.</h5>

        <div class="row" style="width: 1900px; height: 1000px">
            <template v-for="(attr, index) in list" :key="index">
                <div class="col-2">
                    <a-card hoverable style="max-width: 300px; margin: 0px">
                        <template #cover>
                            <img
                                :alt="attr.title"
                                :src="attr.first_image"
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
        </div>

        <div class="row">
            <table class="text-center">
                <PageNavigation :current-page="current" :total-page="total" @pageChange="onPageChange"></PageNavigation>
            </table>
        </div>
    </div>
</template>

<style scoped>
div {
    margin: 20px;
}
</style>
