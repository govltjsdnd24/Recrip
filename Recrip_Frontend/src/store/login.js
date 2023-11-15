import { ref, computed } from 'vue';
import { defineStore } from 'pinia';

export const LoginInfo = defineStore(
    'login',
    () => {
        //store
        const isLogin = ref(false);
        const loginInfo = ref({});

        //getter
        const IsLogin = computed(() => {
            return isLogin.value;
        });

        const getLoginInfo = computed(() => {
            return loginInfo.value;
        });

        //action
        const setLoginInfo = (islogin, info) => {
            isLogin.value = islogin;
            loginInfo.value = JSON.parse(info);
        };

        const setLogOut = () => {
            isLogin.value = false;
            loginInfo.value = {};
        };

        return { isLogin, loginInfo, IsLogin, getLoginInfo, setLogOut, setLoginInfo };
    },
    { persist: { storage: sessionStorage } }
);
