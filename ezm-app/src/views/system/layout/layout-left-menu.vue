<template>
    <el-menu
        :default-active="$route.path"
        :collapse="$store.state.layout.leftShrink"
        router
        unique-opened
        class="layout-left-menu"
    >
        <layout-left-menu-node :list="menuTree"></layout-left-menu-node>
    </el-menu>
</template>

<script>
    import {mapState} from "vuex";
    import LayoutLeftMenuNode from "@/views/system/layout/layout-left-menu-node";

    export default {
        name: 'layout-left-menu',
        mixins: [],
        components: {LayoutLeftMenuNode},
        props: {
            node: {
                default: null
            },
            list: {
                default: null,
            }
        },
        data() {
            return {
                num: 0,
            }
        },
        computed: {
            ...mapState('route', ['menuTree']),
        },
        watch: {},
        created() {
        },
        mounted() {
        },
        destroyed() {
        },
        methods: {}
    };
</script>

<style scoped lang="less">
    .layout-left-menu {

    }
</style>