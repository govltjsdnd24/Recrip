<script setup>
import Footer from './components/common/Footer.vue';
import Header from './components/common/Header.vue';
</script>

<template>
    <div>
        <Header></Header>
        <router-view></router-view>
        <Footer></Footer>
    </div>
</template>

<style scoped></style>
