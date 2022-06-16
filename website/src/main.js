import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import '@/assets/css/fontawesome/css/all.css'
import '@/assets/css/custom/global.css'

createApp(App).use(router).mount('#app')
