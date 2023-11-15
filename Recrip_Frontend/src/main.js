import './assets/css/main.css';
import './assets/css/bottom.css';
import './assets/css/button.css';
import './assets/css/hot_place.css';
import './assets/css/plan.css';
import './assets/css/slider.css';
import './assets/css/style.css';
import './assets/css/components/carousel.css';
import './assets/css/components/footer.css';
import './assets/css/components/header.css';
import './assets/css/components/map.css';
import './assets/css/components/modal.css';

import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

import axios from 'axios';
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';

const pinia = createPinia();
const app = createApp(App);

pinia.use(piniaPluginPersistedstate);

app.use(pinia);
app.use(router);

app.mount('#app');
