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
const messages = ref();

onMounted(() => {
    if (isLogin != false) {
        userInfo.value = getLoginInfo;
        getMessageList();
        if (userInfo.value != null) {
            modified_profile.value = userInfo.value.profile;
            modified_name.value = userInfo.value.username;
            modified_pwd.value = userInfo.value.userpwd;
        }
    }
});

const message_list = () => {
    getMessageList();
    modalOn('.modal-list');
};

const toid = ref('');
const subject = ref('');
const content = ref('');

const sendmessage = () => {
    var url = '/api/messageinsert';
    async function getTodo(url) {
        const response = await axios.post(url, {
            fromid: userInfo.value.userid,
            toid: toid.value,
            subject: subject.value,
            content: content.value,
        });
        if (response.data.resmsg == '등록 오류 발생') {
            alert('해당 아이디가 존재하지 않습니다.');
        }
        messages.value = response.data.resdata.list;
        content.value = '';
        toid.value = '';
        subject.value = '';
    }
    getTodo(url).catch((error) => {
        console.log(error);
    });
    modalOff('.modal-message');
};

const getMessageList = () => {
    var url = '/api/messagelist';
    const param = ref(userInfo.value.userid);

    async function getTodo(url) {
        const response = await axios.get(url, { params: { userid: param.value } });

        messages.value = response.data.resmsg;
    }
    getTodo(url).catch((error) => {
        console.log(error);
    });
};

async function deleteMessage(messageno) {
    var url = '/api/messagedelete';

    async function getTodo(url) {
        const response = await axios.delete(url, { params: { messageno: messageno } });
        messages.value = response.data.resmsg;
    }
    getTodo(url).catch((error) => {
        console.log(error);
    });
    modalOff('.modal-list');
    await message_list();
    alert('메시지가 삭제되었습니다');
}

const login_userid = ref('');
const login_userpwd = ref('');
const profile = ref(info.getLoginInfo.profile);

function login() {
    getUserInfo();
    getMessageList();
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
                console.log('login', response.data.resmsg);
                info.setLoginInfo(true, JSON.stringify(response.data.resmsg));
                modalOff('.modal-signin');
                profile.value = info.getLoginInfo.profile;

                router.go(0);
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
const signup_profile = ref('');

function signup() {
    const multipartFile = new FormData();
    console.log('mofile', file.value[0]);
    multipartFile.append('multipartFile', file.value[0]);
    multipartFile.append('userid', signup_id.value);
    multipartFile.append('username', signup_name.value);
    multipartFile.append('userpwd', signup_pwd.value);
    axios
        .post('/api/restmeminsert', multipartFile, { headers: { 'Content-Type': 'multipart/form-data' } })
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

const modified_profile = ref('');
const modified_name = ref('');
const modified_pwd = ref('');

function modify() {
    const multipartFile = new FormData();
    console.log('mofile', file.value[0]);
    multipartFile.append('multipartFile', file.value[0]);
    multipartFile.append('userid', this.userInfo.userid);
    multipartFile.append('username', modified_name.value);
    multipartFile.append('userpwd', modified_pwd.value);
    axios
        .put('/api/restmemupdate', multipartFile, { headers: { 'Content-Type': 'multipart/form-data' } })
        .then((response) => {
            if (response.data.resdata == 1) {
                modified_profile.value = response.data.profile;
                modalOff('.modal-signin');
                setLoginInfo(
                    true,
                    JSON.stringify({
                        profile: modified_profile.value,
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

function modalReload(className) {
    const targetModal = $(className);
}

const clickcancel = (target) => {
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

const file = ref();
const filechange = (e) => {
    file.value = e.target.files;
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
                                <router-link to="/mytrippage" class="flex text-decoration-none link-dark">
                                    <!-- <img class="nav-img" src="@/assets/images/nav/calendar.png" alt="calendar" /> -->
                                    <span class="nav" style="font-family: JeonjuCraftGoR">내 계획 관리</span>
                                </router-link>
                            </li>
                            <li class="gap">
                                <router-link to="/map" class="flex text-decoration-none link-dark">
                                    <!-- <img class="nav-img" src="@/assets/images/nav/map.png" alt="map" /> -->
                                    <span class="nav" style="font-family: JeonjuCraftGoR">지역별 여행지</span>
                                </router-link>
                            </li>
                            <li class="gap">
                                <router-link to="/reviewboardlist" class="flex text-decoration-none link-dark">
                                    <!-- <img class="nav-img" src="@/assets/images/nav/camera.png" alt="camera" /> -->
                                    <span class="nav" style="font-family: JeonjuCraftGoR">여행 게시판</span>
                                </router-link>
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
                    <div style="display: flex; align-items: center">
                        <div v-if="isLogin != false">
                            <span>
                                <img
                                    class="header-users-img"
                                    src="@/assets/images/nav/write_message.png"
                                    style="width: 35px"
                                    @click="modalOn('.modal-message')"
                                />
                                &nbsp;
                            </span>
                            <span style="margin-right: 1px">
                                <img
                                    class="header-users-img"
                                    src="@/assets/images/nav/list_message.png"
                                    style="width: 30px"
                                    @click="message_list()"
                                />
                            </span>
                            <span style="margin: 15px">
                                <img :src="profile" style="width: 50px; border-radius: 50%" />
                            </span>
                            <label style="margin-right: 100px">
                                <div class="but" style="font-family: MICEGothicBold">
                                    {{ info.getLoginInfo.username }}님 안녕하세요.
                                </div>
                            </label>
                        </div>
                        <img
                            class="header-users-img"
                            src="@/assets/images/nav/users.png"
                            alt="users"
                            @click="clickuser()"
                        />
                    </div>

                    <div v-if="isLogin == false">
                        <div class="header-dropdown mx-4" style="display: none">
                            <button @click="modalOn('.modal-signin')" style="overflow: auto; white-space: nowrap">
                                <span
                                    class="but"
                                    style="font-family: OAGothic-ExtraBold"
                                    data-toggle="modal"
                                    data-target=".modal-signin"
                                    >로그인</span
                                >
                            </button>
                            <button @click="modalOn('.modal-signup')" style="overflow: auto; white-space: nowrap">
                                <span class="but" style="font-family: OAGothic-ExtraBold">회원가입</span>
                            </button>
                        </div>
                    </div>
                    <div v-if="isLogin != false">
                        <div class="header-dropdown" style="display: none">
                            <button @click="modalOn('.modal-edit-userinfo')">
                                <span class="but" style="font-family: OAGothic-ExtraBold">내정보</span>
                            </button>
                            <button @click="logout">
                                <span class="but" style="font-family: OAGothic-ExtraBold">로그아웃</span>
                            </button>
                        </div>
                    </div>
                </nav>
            </div>
        </header>
    </div>
    <div class="modal-container">
        <div
            class="modal modal-wrap modal-signin"
            tabindex="-1"
            role="dialog"
            aria-labelledby="myLargeModalLabel"
            aria-hidden="true"
        >
            <div class="modal1 modal-dialogue">
                <header class="modal-header">
                    <h2>로그인</h2>
                    <button class="modal-close-btn" @click="modalOff('.modal-signin')">X</button>
                </header>
                <div class="modal-input-wrap">
                    <label class="modal-label" for="modal-id">아이디&nbsp;&nbsp;&nbsp;&nbsp;</label>
                    <input
                        margin-right="5px"
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
                        width="50px"
                        placeholder="비밀번호"
                        id="userpwd"
                        name="userpwd"
                        v-model="login_userpwd"
                    />
                </div>
                <div class="modal-input-wrap" style="justify-content: center">
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
                    <label class="modal-label" for="signin-name"
                        >이름&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label
                    >
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
                    <label class="modal-label" for="signin-id">아이디&nbsp;&nbsp;&nbsp;&nbsp;</label>
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
                    <label class="modal-label" for="edit-profile">프로필사진</label>
                    <input type="file" id="upload" name="upload" @change="filechange" />
                </div>
                <div class="modal-input-wrap">
                    <button @click="signup()" class="modal-submit signin">회원가입</button>
                    <button class="modal-cancel" @click="modalOff('.modal-signup')">취소</button>
                </div>
            </div>
        </div>

        <!-- 쪽지 -->
        <div
            class="modal modal-wrap modal-message"
            tabindex="-1"
            role="dialog"
            aria-labelledby="myLargeModalLabel"
            aria-hidden="true"
        >
            <div class="modal1 modal-dialogue">
                <header class="modal-header">
                    <h2>쪽지</h2>
                    <button class="modal-close-btn" @click="modalOff('.modal-message')">X</button>
                </header>
                <div class="modal-input-wrap">
                    <label class="modal-label" for="modal-id">받는사람</label>
                    <input
                        margin-right="5px"
                        class="modal-input"
                        type="text"
                        placeholder="아이디"
                        id="toid"
                        name="toid"
                        v-model="toid"
                    />
                </div>
                <div class="modal-input-wrap">
                    <label class="modal-label" for="modal-id"
                        >제목&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label
                    >
                    <input
                        margin-right="5px"
                        class="modal-input"
                        type="text"
                        placeholder="제목"
                        id="subject"
                        name="subject"
                        v-model="subject"
                    />
                </div>
                <div class="modal-input-wrap">
                    <label class="modal-label" for="modal-pw">내용</label>
                    <textarea
                        class="modal-input"
                        id="content"
                        name="content"
                        v-model="content"
                        style="width: 370px; height: 200px"
                    >
내용</textarea
                    >
                </div>
                <div class="modal-input-wrap" style="justify-content: center">
                    <button @click="sendmessage()" class="modal-submit login but">보내기</button>
                    <button class="modal-cancel but" @click="modalOff('.modal-message')">취소</button>
                </div>
            </div>
        </div>

        <div
            class="modal modal-wrap modal-list"
            tabindex="-1"
            role="dialog"
            aria-labelledby="myLargeModalLabel"
            aria-hidden="true"
        >
            <div class="modal1 modal-dialogue">
                <header class="modal-header">
                    <h2>쪽지 목록</h2>
                    <button
                        class="modal-close-btn"
                        style="margin-left: 300px; margin-bottom: 30px"
                        @click="modalOff('.modal-list')"
                    >
                        X
                    </button>
                    <MessageOutlined />
                </header>
                <div>
                    <table class="flex row">
                        <div class="column">
                            <tr class="flex mx-5">
                                <th>보낸 사람</th>
                                <th>제목</th>
                                <th>내용</th>
                                <th>보낸 시간</th>
                            </tr>
                            <hr />
                        </div>

                        <template v-for="message in messages" :key="message.messageno">
                            <div class="column">
                                <tr class="flex mx-5">
                                    <td class="col-md">{{ message.fromid }} &nbsp;&nbsp;</td>
                                    <td class="col-md">{{ message.subject }}</td>
                                    <td class="col-md">{{ message.content }}</td>
                                    <td class="col-md">{{ message.date }}</td>
                                </tr>
                            </div>
                            <button
                                v-if="message.messageno != null"
                                type="button"
                                class="btn btn-secondary"
                                @click="deleteMessage(message.messageno)"
                                style="width: 100px; margin-left: 190px"
                            >
                                확인
                            </button>
                        </template>
                    </table>
                </div>
                <div class="modal-input-wrap" style="justify-content: center">
                    <button class="modal-cancel but" @click="modalOff('.modal-list')">나가기</button>
                </div>
            </div>
        </div>

        <div class="modal-wrap modal-edit-userinfo modal">
            <div class="modal1">
                <header class="modal-header">
                    <h2>회원정보수정</h2>
                    <button class="modal-close-btn" @click="modalOff('.modal-edit-userinfo')">X</button>
                </header>
                <input type="hidden" name="action" value="modify" />
                <input type="hidden" name="userinfo" value="${userinfo }" />
                <div class="modal-input-wrap">
                    <label class="modal-label" for="edit-name"
                        >이름&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label
                    >
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
                    <label class="modal-label" for="edit-id">아이디&nbsp;&nbsp;&nbsp;&nbsp;</label>
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
                    <label class="modal-label" for="edit-profile">프로필사진</label>
                    <input type="file" id="upload" name="upload" @change="filechange" />
                </div>
                <div class="modal-input-wrap" style="justify-content: center">
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
