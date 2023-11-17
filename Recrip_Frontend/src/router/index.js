import { createRouter, createWebHistory } from 'vue-router';
import MainView from '../views/MainView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: MainView
    },
    {
      path: '/map',
      name: 'map',
      component: () => import('../views/MapView.vue')
    },
    {
      path: '/boardlist',
      name: 'BoardList',
      component: () => import('../views/BoardList.vue')
    },
    {
      path: '/boardview/:articleno',
      name: 'BoardView',
      component: () => import('../views/BoardDetail.vue')
    },
    {
      path: '/boardwrite',
      name: 'BoardWrite',
      component: () => import('../views/BoardWrite.vue')
    },
    {
      path: '/boardmodify/:articleno',
      name: 'BoardModify',
      component: () => import('../views/BoardModify.vue')
    },
    {
      path: '/reviewboardlist',
      name: 'ReviewBoardList',
      component: () => import('../views/ReviewBoardList.vue')
    },
    {
      path: '/crawlingtest',
      name: 'CrawlingTest',
      component: () => import('../components/common/CrawlingTest.vue')
    },
    {
      path: '/mytrippage',
      name: 'MyTripPage',
      component: () => import('../views/MyTripPage.vue')
    },
  ]
})

export default router;
