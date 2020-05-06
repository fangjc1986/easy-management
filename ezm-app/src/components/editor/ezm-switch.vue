<template>
    <el-switch
        :value="valueComp"
        @input="(v)=>$emit('input', v ? 1: 0)"
    ></el-switch>
</template>

<script>
    export default {
        name: 'ezm-switch',
        mixins: [],
        components: {},
        props: {
            value: {
                default: false,
            },
        },
        data() {
            return {}
        },
        computed: {
            valueComp() {
                try {
                    return !!parseInt(this.value);
                } catch (e) {
                    return !!this.value;
                }
            }
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
    .ezm-switch {

    }
</style>