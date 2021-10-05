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
    path: '/feelingdetail/:historyId',
    name: 'FeelingDetail',
    component: FeelingDetail,
    props: true
  },
  {
    path: '/letterdetail/:historyId',
    name: 'LetterDetail',
    component: LetterDetail,
    props: true
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
