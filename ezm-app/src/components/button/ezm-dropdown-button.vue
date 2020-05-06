<template>
    <el-dropdown
        split-button
        type="primary"
        @click="clickButton"
        trigger="click"
    >
        <i class="el-icon-loading" v-if="loading"></i>
        <i :class="icon" v-if="icon && !loading"></i>
        <slot>{{title}}</slot>
        <template slot="dropdown">
            <slot name="dropdown"></slot>
        </template>
    </el-dropdown>
</template>

<script>
    export default {
        name: 'ezm-dropdown-button',
        mixins: [],
        components: {},
        props: {
            icon: {
                default: '',
            },
            title: {
                default: '按钮名称'
            },
            loading: {
                default: false,
            }
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
        methods: {
            clickButton() {
                if (this.loading) return;
                this.$emit('click');
            }
        }
    };
</script>

<style scoped lang="less">

</style>