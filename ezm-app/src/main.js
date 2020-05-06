import 'babel-polyfill';

import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from "./store"
import './plugins/index.js'
import './assets/css/index.less'
import Enum from "@/utils/Enum";

Vue.config.productionTip = false;
Vue.prototype.$enum = Enum;

new Vue({
    router,
    store,
    render: h => h(App),
    created() {
        Enum.vue = this;
        this.$store.dispatch("layout/reload");
    },
}).$mount('#app');
