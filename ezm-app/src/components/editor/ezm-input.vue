<template>
    <el-input
        :value="value"
        :disabled="disabled"
        :placeholder="placeholder"

        @input="(v)=>$emit('input',v)"
    ></el-input>
</template>

<script>
    export default {
        name: 'ezm-input',
        mixins: [],
        components: {},
        props: {
            value: {
                default: ''
            },
            disabled: {
                default: false,
            },
            placeholder: {
                default: '',
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
        methods: {}
    };
</script>

<style scoped lang="less">
    .ezm-input {

    }
</style>