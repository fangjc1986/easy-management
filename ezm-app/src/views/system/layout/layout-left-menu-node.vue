<template>
    <div>
        <template v-for="node in list">
            <el-submenu v-if="node && node.children && node.children.length "
                        :index="node.path"
            >
                <template slot="title">
                    <i :class="['iconfont', node.icon]" v-if="node.icon"></i>
                    <span slot="title">{{ node.meta.title }}</span>
                </template>
                <layout-left-menu-node :list="node.children"></layout-left-menu-node>
            </el-submenu>
            <el-menu-item :index="node.path" v-else>
                <i :class="['iconfont', node.icon]" v-if="node.icon"></i>
                <span slot="title">{{ node.meta.title }}</span>
            </el-menu-item>
        </template>
    </div>
</template>

<script>

    export default {
        name: 'layout-left-menu-node',
        mixins: [],
        components: {},
        props: {
            list: Array,
        },
        data() {
            return {}
        },
        computed: {},
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

</style>