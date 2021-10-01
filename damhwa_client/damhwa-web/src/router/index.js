import { createRouter, createWebHistory } from 'vue-router'
import Calendar from '../views/Calendar.vue'
import FeelingDetail from '../views/FeelingDetail.vue'
import LetterDetail from '../views/LetterDetail.vue'

const routes = [
  {
    path: '/',
    name: 'Calendar',
    component: Calendar
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }, 
  {
    path: '/feelingdetail/:historyId',
    name: 'FeelingDetail',
    component: FeelingDetail
  },
  {
    path: '/letterdetail/:historyId',
    name: 'LetterDetail',
    component: LetterDetail
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
