
<script setup>
import {ref, onBeforeMount} from 'vue';
import axios from 'axios';
const props= defineProps({ article: Object });
console.log("PROPS:",props.article)
const subject=ref(props.article.subject);
const files = ref([{}]);

onBeforeMount(() => {
    var filelist = `/api/reviewboardfilelist?articleno=${props.article.articleno}`;

    async function getfiles(filelist) {
        const response = await axios.get(filelist);
        console.log("RES :"+response.data.resdata);
        files.value = response.data.resdata;
    }
    getfiles(filelist).catch((error) => {
        console.log(error);
    });
});
</script>

<template>
  <div class="col">
    <router-link
        :to="{ name: 'ReviewBoardView', params: { articleno: article.articleno } }"
        class="article-title link-dark"
      >
       
        <a-card hoverable style="width: 300px; min-height:300px">
            <template #cover >
                <img v-if="files[0] !=null" :src="files[0].url" style="max-height: 200px;">
                <img v-else alt="example" src="https://source.unsplash.com/random" style="object-fit:fit; max-height: 200px;"/>
            </template>
            <p><a-rate :value="article.starscore/10" allow-half disabled/></p>
            <a-card-meta :title=subject>
                <template #description>{{article.registdate}}</template>
            </a-card-meta>
        </a-card>
    </router-link>
  </div>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
