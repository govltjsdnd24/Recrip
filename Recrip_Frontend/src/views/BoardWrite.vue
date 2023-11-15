<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import {useRoute,useRouter} from 'vue-router';
import { LoginInfo } from "../store/login";

const info = LoginInfo();
const { getLoginInfo } = info;

const userinfo = ref();
const subject = ref('');
const content = ref('');
const router = useRouter();

onMounted(() => {
	userinfo.value = getLoginInfo;
})
const BoardWrite = () => {
    if (subject.value == '' || content.value == '') {
        alert('작성 내용 확인');
        return false;
    }
    console.log(subject.value , content.value);
    var url = '/api/boardwrite';

    async function writeArticle(url) {
        const response = await axios.post(url, {
            userid: userinfo.value.userid,
            subject: subject.value,
            content: content.value
        });
        console.log(response);
    }
    writeArticle(url).catch((error) => {
        console.log(error);
    }); 

    router.push('/boardlist')
}
</script>

<template>
    <main>
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-lg-8 col-md-10 col-sm-12">
					<h2 class="my-3 py-3 shadow-sm bg-light text-center">
						<mark class="sky">글쓰기</mark>
					</h2>
				</div>
				<div class="col-lg-8 col-md-10 col-sm-12">
					<form id="form-register" method="POST" action="">
						<input type="hidden" id="nowid" value="${userinfo.userid}">
						<div class="mb-3">
							<label for="subject" class="form-label">제목 : </label> <input
								type="text" class="form-control" id="subject" name="subject"
								placeholder="제목..." v-model="subject"/>
						</div>
						<div class="mb-3">
							<label for="content" class="form-label">내용 : </label>
							<textarea class="form-control" id="content" name="content"
								rows="7" v-model="content"></textarea>
						</div>
						<div class="col-auto text-center">
							<button type="button" id="btn-register"
								class="btn btn-outline-primary mb-3" @click="BoardWrite()">글작성</button>
							<button type="reset" class="btn btn-outline-danger mb-3">초기화</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</main> 
</template>

<style scoped></style>
