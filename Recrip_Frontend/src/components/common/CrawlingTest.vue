<script setup>
import axios from "axios";
import { onMounted,ref } from "vue";
import cheerio from "cheerio";

const crawlings = ref([{}]);

onMounted(() => {
    axios.get('/test').then(response => {
        //console.log(response.request.response)
        const data = response.request.response;
        const $ = cheerio.load(data);
        //console.log(data);
        const result = $(".card_group > li > figure").toArray();
        //console.log(result);
        for (let index = 5; index < result.length; index++) {
            let row = {
                href: result[index].children[1].attribs.href,
                alt: result[index].children[1].children[1].attribs.alt,
                src: result[index].children[1].children[1].attribs.src
            }
            crawlings.value.push(row);
        }
    })
})

</script>

<template>
    <div>
        <a-card v-for="data in crawlings" :key="data.href" hoverable style="width: 300px">    
            <template #cover>
                <a :href="data.href"><img alt="" :src="data.src" style="width: 300px; height: 300px;" /></a>
                </template>
                <a :href="data.href"><a-card-meta :title="data.alt">
                </a-card-meta></a>
        </a-card>
    </div>
</template>

<style scoped>

</style>
