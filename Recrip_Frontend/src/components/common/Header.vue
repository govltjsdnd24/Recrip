<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import router from '@/router/index.js';
import { LoginInfo } from '../../store/login';
const info = LoginInfo();
const { isLogin, loginInfo, IsLogin, getLoginInfo, setLogOut, setLoginInfo } = info;

const $ = (query) => document.querySelector(query);

//const userInfo = ref(JSON.parse(sessionStorage.getItem('userInfo')));
const userInfo = ref(getLoginInfo);

onMounted(() => {
    console.log('hello');
    if (isLogin != false) {
        userInfo.value = getLoginInfo;
        if (userInfo.value != null) {
            console.log('LOADED:' + userInfo != null ? userInfo.value.userid : '', userInfo.value);
            modified_email.value = userInfo.value.email;
            modified_name.value = userInfo.value.username;
            modified_pwd.value = userInfo.value.userpwd;
        }
    }
});

const login_userid = ref('');
const login_userpwd = ref('');

function login() {
    getUserInfo();
}

const getUserInfo = () => {
    axios
        .post('/api/restmemlogin', {
            userid: login_userid.value,
            userpwd: login_userpwd.value,
        })
        .then((response) => {
            if (response.data.resdata == 1) {
                //sessionStorage.setItem('userInfo', JSON.stringify(response.data.resmsg));
                info.setLoginInfo(true, JSON.stringify(response.data.resmsg));
                modalOff('.modal-signin');

                router.go(router.currentRoute);
            } else {
                alert('로그인 실패');
            }
        })
        .catch((error) => {
            console.log(error);
        });
};

function logout() {
    // sessionStorage.removeItem('userInfo');
    // LoginInfo.setLogOut();
    setLogOut();
    router.go(router.currentRoute);
}

const signup_name = ref('');
const signup_id = ref('');
const signup_pwd = ref('');
const signup_email = ref('');

function signup() {
    axios
        .post('/api/restmeminsert', {
            email: signup_email.value,
            userid: signup_id.value,
            username: signup_name.value,
            userpwd: signup_pwd.value,
        })
        .then((response) => {
            if (response.data.resdata == 1) {
                modalOff('.modal-signin');
                router.go(router.currentRoute);
            }
        })
        .catch((error) => {
            console.log(error);
        });
}

const modified_email = ref('');
const modified_name = ref('');
const modified_pwd = ref('');

function modify() {
    axios
        .put('/api/restmemupdate', {
            email: modified_email.value,
            userid: this.userInfo.userid,
            isdeleted: false,
            username: modified_name.value,
            userpwd: modified_pwd.value,
        })
        .then((response) => {
            console.log(response);
            if (response.data.resdata == 1) {
                modalOff('.modal-signin');
                setLoginInfo(
                    true,
                    JSON.stringify({
                        email: modified_email.value,
                        userid: this.userInfo.userid,
                        isdeleted: false,
                        username: modified_name.value,
                        userpwd: modified_pwd.value,
                    })
                );
                router.go(router.currentRoute);
            }
        })
        .catch((error) => {
            console.log(error);
        });
}

function remove() {
    axios
        .delete('/api/restmemdelete', { params: { userid: this.userInfo.userid } })
        .then((response) => {
            console.log(response);
            if (response.data.resdata == 1) {
                modalOff('.modal-signin');
                //sessionStorage.removeItem('userInfo');
                setLogOut();
                router.go(router.currentRoute);
            }
        })
        .catch((error) => {
            console.log(error);
        });
}

// modal control
function modalOn(className) {
    const targetModal = $(className);
    targetModal.style.display = 'block';
}

function modalOff(className) {
    const targetModal = $(className);
    targetModal.style.display = 'none';
}

const clickcancel = (target) => {
    console.log(target);
    defaultPrevented();
    const targetModal = target.parentNode.parentNode.parentNode.parentNode;
    targetModal.style.display = 'none';
    console.log('modal is closed');
};

const clickuser = () => {
    const dropdown = $('.header-dropdown');
    if (dropdown.style.display == 'none') {
        dropdown.style.animation = 'reveal 0.5s cubic-bezier(0.77, 0, 0.175, 1) 0s';
        dropdown.style.display = 'block';
    } else if (dropdown.style.display == 'block') dropdown.style.display = 'none';
};
</script>

<template>
    <div>
        <header class="header">
            <div class="flex">
                <div class="flex">
                    <!-- service icon -->
                    <router-link to="/">
                        <img
                            class="service-logo img-fluid"
                            src="@/assets/images/logo.gif"
                            style="width: 90px"
                            alt="enjoy trip logo"
                        />
                    </router-link>

                    <!-- nav menus -->
                    <nav class="header-nav">
                        <ul class="flex">
                            <li class="gap">
                                <a href="" class="flex text-decoration-none link-dark">
                                    <!-- <img class="nav-img" src="@/assets/images/nav/calendar.png" alt="calendar" /> -->
                                    <span class="nav" style="font-family: JeonjuCraftGoR">내 계획 관리</span>
                                </a>
                            </li>
                            <li class="gap">
                                <router-link to="/map" class="flex text-decoration-none link-dark">
                                    <!-- <img class="nav-img" src="@/assets/images/nav/map.png" alt="map" /> -->
                                    <span class="nav" style="font-family: JeonjuCraftGoR">지역별 여행지</span>
                                </router-link>
                            </li>
                            <li class="gap">
                                <a href="" class="flex text-decoration-none link-dark">
                                    <!-- <img class="nav-img" src="@/assets/images/nav/camera.png" alt="camera" /> -->
                                    <span class="nav" style="font-family: JeonjuCraftGoR">여행 게시판</span>
                                </a>
                            </li>
                            <li class="gap">
                                <router-link to="/boardlist" class="flex text-decoration-none link-dark">
                                    <!-- <img class="nav-img" src="@/assets/images/nav/chatting.png" alt="chatting" /> -->
                                    <span class="nav" style="font-family: JeonjuCraftGoR">자유 게시판</span>
                                </router-link>
                            </li>
                        </ul>
                    </nav>
                </div>

                <!-- auth menus (dropdown) -->
                <nav>
                    <div v-if="isLogin != false">
                        <label style="margin-right: 100px">
                            {{ userInfo.userid }} ({{ info.getLoginInfo.username }})님 안녕하세요.
                        </label>
                    </div>
                    <img
                        class="header-users-img"
                        src="@/assets/images/nav/users.png"
                        alt="users"
                        @click="clickuser()"
                    />
                    <div v-if="isLogin == false">
                        <div class="header-dropdown mx-4" style="display: none">
                            <button @click="modalOn('.modal-signin')" style="overflow: auto; white-space: nowrap">
                                <span class="but" style="font-family: OAGothic-ExtraBold">로그인</span>
                            </button>
                            <button @click="modalOn('.modal-signup')" style="overflow: auto; white-space: nowrap">
                                <span class="but" style="font-family: OAGothic-ExtraBold">회원가입</span>
                            </button>
                        </div>
                    </div>
                    <div v-if="isLogin != false">
                        <div class="header-dropdown" style="display: none">
                            <button @click="modalOn('.modal-edit-userinfo')">내정보</button>
                            <button @click="logout">로그아웃</button>
                        </div>
                    </div>
                </nav>
            </div>
        </header>
    </div>
    <div class="modal-container">
        <div class="modal-wrap modal-signin" style="display: none">
            <div class="modal1">
                <header class="modal-header">
                    <h2>로그인</h2>
                    <button class="modal-close-btn" @click="modalOff('.modal-signin')">X</button>
                </header>
                <div class="modal-input-wrap">
                    <label class="modal-label" for="modal-id">아이디</label>
                    <input
                        class="modal-input"
                        type="text"
                        placeholder="아이디"
                        id="userid"
                        name="userid"
                        v-model="login_userid"
                    />
                </div>
                <div class="modal-input-wrap">
                    <label class="modal-label" for="modal-pw">비밀번호</label>
                    <input
                        class="modal-input"
                        type="password"
                        placeholder="비밀번호"
                        id="userpwd"
                        name="userpwd"
                        v-model="login_userpwd"
                    />
                </div>
                <div class="modal-input-wrap">
                    <button @click="login()" class="modal-submit login but">로그인</button>
                    <button class="modal-cancel but" @click="modalOff('.modal-signin')">취소</button>
                </div>
            </div>
        </div>

        <div class="modal-wrap modal-signup" style="display: none">
            <div class="modal1">
                <header class="modal-header">
                    <h2>회원가입</h2>
                    <button class="modal-close-btn" @click="modalOff('.modal-signup')">X</button>
                </header>
                <input type="hidden" name="action" value="register" />
                <div class="modal-input-wrap">
                    <label class="modal-label" for="signin-name">이름</label>
                    <input
                        class="modal-input"
                        type="text"
                        placeholder="이름"
                        id="signin-name"
                        name="username"
                        v-model="signup_name"
                    />
                </div>
                <div class="modal-input-wrap">
                    <label class="modal-label" for="signin-id">아이디</label>
                    <input
                        class="modal-input"
                        type="text"
                        placeholder="아이디"
                        id="signin-id"
                        name="userid"
                        v-model="signup_id"
                    />
                </div>
                <div class="modal-input-wrap">
                    <label class="modal-label" for="signin-pw">비밀번호</label>
                    <input
                        class="modal-input"
                        type="password"
                        placeholder="비밀번호"
                        id="signin-pw"
                        name="userpwd"
                        v-model="signup_pwd"
                    />
                </div>
                <div class="modal-input-wrap">
                    <label class="modal-label" for="signin-email">이메일</label>
                    <input
                        class="modal-input"
                        type="text"
                        placeholder="이메일"
                        id="signin-email"
                        name="email"
                        v-model="signup_email"
                    />
                </div>
                <div class="modal-input-wrap">
                    <button @click="signup()" class="modal-submit signin">회원가입</button>
                    <button class="modal-cancel" @click="modalOff('.modal-signup')">취소</button>
                </div>
            </div>
        </div>

        <div class="modal-wrap modal-edit-userinfo" style="display: none">
            <div class="modal1">
                <header class="modal-header">
                    <h2>회원정보수정</h2>
                    <button class="modal-close-btn" @click="modalOff('.modal-edit-userinfo')">X</button>
                </header>
                <input type="hidden" name="action" value="modify" />
                <input type="hidden" name="userinfo" value="${userinfo }" />
                <div class="modal-input-wrap">
                    <label class="modal-label" for="edit-name">이름</label>
                    <input
                        class="modal-input"
                        type="text"
                        placeholder="이름"
                        id="edit-name"
                        v-model="modified_name"
                        name="username"
                    />
                </div>
                <div class="modal-input-wrap">
                    <label class="modal-label" for="edit-id">아이디</label>
                    <input
                        class="modal-input"
                        type="text"
                        placeholder="아이디"
                        id="edit-id"
                        name="userid"
                        :value="userInfo != null ? userInfo.userid : ''"
                        readonly="readonly"
                    />
                </div>
                <div class="modal-input-wrap">
                    <label class="modal-label" for="edit-pw">비밀번호</label>
                    <input
                        class="modal-input"
                        type="password"
                        placeholder="비밀번호"
                        id="edit-pw"
                        name="userpwd"
                        v-model="modified_pwd"
                    />
                </div>
                <div class="modal-input-wrap">
                    <label class="modal-label" for="edit-email">이메일</label>
                    <input
                        class="modal-input"
                        type="text"
                        placeholder="이메일"
                        id="edit-email"
                        name="email"
                        v-model="modified_email"
                    />
                </div>
                <div class="modal-input-wrap">
                    <button type="button" @click="modify()" class="modal-submit modify">수정</button>
                    <button type="button" @click="remove()" class="modal-warn">탈퇴</button>
                    <button class="modal-cancel" @click="modalOff('.modal-edit-userinfo')">취소</button>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.nav {
    box-shadow: inset 0 0 0 0 gray;
    color: black;
    margin: 0 -0.25rem;
    padding: 0 0.25rem;
    transition: color 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
}
.nav:hover {
    box-shadow: inset 200px 0 0 0 gray;
    color: white;
}
</style>
