import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import bridge from './bridge'
import {SetupCalendar, Calendar, DatePicker} from 'v-calendar';

window.bridge = bridge

createApp(App)
.use(router)
.use(SetupCalendar, {})
.use(bridge)
.component('Calendar', Calendar)
.component('DatePicker', DatePicker)
.mount('#app')

