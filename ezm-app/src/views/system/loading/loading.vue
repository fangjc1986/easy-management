<template>
    <div class="loading">

    </div>
</template>

<script>
    import {DialogUtil} from "@/utils/common.util";

    export default {
        name: 'loading',
        mixins: [],
        components: {},
        props: {},
        data() {
            return {
                loading: null,
                loadingTimeout: null,
            }
        },
        computed: {},
        watch: {},
        created() {
        },
        mounted() {
            this.loadingTimeout = setTimeout(() => {
                DialogUtil.toast("加载超时，请重新登录!", 'error');
                this.$router.push({path: '/'});
            }, 10000);
            this.loading = this.$loading();
            this.$store.commit("user/refreshMyInfo");
            this.$store.commit("route/refreshRoute");
        },
        destroyed() {
            this.loading && this.loading.close();
            clearTimeout(this.loadingTimeout);
        },
        methods: {}
    };
</script>

<style scoped lang="less">
    .loading {

    }
</style>