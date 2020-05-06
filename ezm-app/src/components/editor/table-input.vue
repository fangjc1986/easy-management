<template>
    <el-input
        :value="value"
        @input="(v)=> $emit('input',v)"
        @keyup.enter.native="onKeyEnter"
    ></el-input>
</template>

<script>
    export default {
        name: 'table-input',
        mixins: [],
        components: {},
        props: {
            value: {
                default: ' ',
            },
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
            onKeyEnter() {
            },
        }
    };
</script>

<style scoped lang="less">
    .table-input {

    }
</style>