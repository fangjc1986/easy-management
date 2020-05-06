import Vue from 'vue'
import VueRouter from 'vue-router'
import route from "@/router/route";
import {changeWebTitle} from "@/utils/common.util";

Vue.use(VueRouter);

const router = new VueRouter(route);
router.beforeEach((t, f, n) => {
    changeWebTitle(t.meta.title + ' - ' + process.env.VUE_APP_TITLE);
    n();
});

export default router;
