<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from 'axios';

const route = useRoute();
const { articleno } = route.params;
const article = ref({});

const router = useRouter();

onMounted(() => {
    var url = `/api/freeboardview?articleno=${articleno}`;

    async function getArticle(url) {
        const response = await axios.get(url);
        console.log(response.data.resdata);
        article.value = response.data.resdata;
    }
    getArticle(url).catch((error) => {
        console.log(error);
    });
});

const BoardUpdate = () => {
    var url = '/api/freeboardupdate';
    console.log(article.value);
    async function articleUpdate(url) {
        const response = await axios.put(url, {
            articleno: article.value.articleno,
            subject: article.value.subject,
            content: article.value.content
        });
        console.log(response);
    }
    articleUpdate(url).catch((error) => {
        console.log(error);
    });

	setTimeout(Cancel, 100);
}

const Cancel = () => {
    router.push({ name: 'BoardView', params: { articleno: article.value.articleno } });
}

</script>

<template>
    <main>
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-lg-8 col-md-10 col-sm-12">
					<h2 class="my-3 py-3 shadow-sm bg-light text-center">
						<mark class="sky">글수정</mark>
					</h2>
				</div>
				<div class="col-lg-8 col-md-10 col-sm-12">
					<form id="form-update" method="POST" action="">
						<div class="mb-3">
							<label for="subject" class="form-label">제목 : </label>
							<input
								type="text" class="form-control" id="subject" name="subject"
								placeholder="제목..." v-model="article.subject"/>
						</div>
						<div class="mb-3">
							<label for="content" class="form-label">내용 : </label>
							<textarea class="form-control" id="content" name="content"
								rows="7" v-model="article.content"></textarea>
						</div>
						<div class="col-auto text-center">
							<button type="button"
								class="btn btn-outline-primary mb-3" @click="BoardUpdate">수정</button>
							<button type="button" class="btn btn-outline-danger mb-3" @click="Cancel">취소</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</main>
</template>

<style scoped>

</style> 