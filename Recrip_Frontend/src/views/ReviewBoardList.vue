<script setup>
import PageNavigation from "../components/common/PageNavigation.vue";
import { ref } from 'vue';

const total = ref(50);
const current = ref(1);
</script>

<template>
    <div class="row justify-content-center">
	<div class="row" style="width: 1900px; height: 1000px;">
        <div class="col-2">
            <a-card hoverable style="width: 300px">
                <template #cover>
                <img alt="example" src="https://source.unsplash.com/random" style="width: 300px; height: 300px;"/>
                </template>
                <a-card-meta title="Europe Street beat">
                <template #description>www.instagram.com</template>
                </a-card-meta>
            </a-card>
        </div>
        <div class="col-2">
            <a-card hoverable style="width: 300px">
                <template #cover>
                <img alt="example" src="https://source.unsplash.com/random" style="width: 300px; height: 300px;"/>
                </template>
                <a-card-meta title="Europe Street beat">
                <template #description>www.instagram.com</template>
                </a-card-meta>
            </a-card>
        </div>
        <div class="col-2">
            <a-card hoverable style="width: 300px">
                <template #cover>
                <img alt="example" src="https://source.unsplash.com/random" style="width: 300px; height: 300px;"/>
                </template>
                <a-card-meta title="Europe Street beat">
                <template #description>www.instagram.com</template>
                </a-card-meta>
            </a-card>
        </div>
        <div class="col-2">
            <a-card hoverable style="width: 300px">
                <template #cover>
                <img alt="example" src="https://source.unsplash.com/random" style="width: 300px; height: 300px;"/>
                </template>
                <a-card-meta title="Europe Street beat">
                <template #description>www.instagram.com</template>
                </a-card-meta>
            </a-card>
        </div>
        <div class="col-2">
            <a-card hoverable style="width: 300px">
                <template #cover>
                <img alt="example" src="https://source.unsplash.com/random" style="width: 300px; height: 300px;"/>
                </template>
                <a-card-meta title="Europe Street beat">
                <template #description>www.instagram.com</template>
                </a-card-meta>
            </a-card>
        </div>
        <div class="col-2">
            <a-card hoverable style="width: 300px">
                <template #cover>
                <img alt="example" src="https://source.unsplash.com/random" style="width: 300px; height: 300px;"/>
                </template>
                <a-card-meta title="Europe Street beat">
                <template #description>www.instagram.com</template>
                </a-card-meta>
            </a-card>
        </div>
        <div class="col-2">
            <a-card hoverable style="width: 300px">
                <template #cover>
                <img alt="example" src="https://source.unsplash.com/random" style="width: 300px; height: 300px;"/>
                </template>
                <a-card-meta title="Europe Street beat">
                <template #description>www.instagram.com</template>
                </a-card-meta>
            </a-card>
        </div>
        <div class="col-2">
            <a-card hoverable style="width: 300px">
                <template #cover>
                <img alt="example" src="https://source.unsplash.com/random" style="width: 300px; height: 300px;"/>
                </template>
                <a-card-meta title="Europe Street beat">
                <template #description>www.instagram.com</template>
                </a-card-meta>
            </a-card>
        </div>
        <div class="col-2">
            <a-card hoverable style="width: 300px" >
                <template #cover>
                <img alt="example" src="https://source.unsplash.com/random" style="width: 300px; height: 300px;"/>
                </template>
                <a-card-meta title="Europe Street beat">
                <template #description>www.instagram.com</template>
                </a-card-meta>
            </a-card>
        </div>
        <div class="col-2">
            <a-card hoverable style="width: 300px">
                <template #cover>
                <img alt="example" src="https://source.unsplash.com/random" style="width: 300px; height: 300px;"/>
                </template>
                <a-card-meta title="Europe Street beat">
                <template #description>www.instagram.com</template>
                </a-card-meta>
            </a-card>
        </div>
    </div>
    
    <div class="row">
        <table class="text-center">
            <PageNavigation
              :current-page="1"
              :total-page="30"
              @pageChange="onPageChange"
              ></PageNavigation>
        </table>
    </div>
</div>
</template>

<style scoped>
div {
    margin:  20px;
}
</style>
