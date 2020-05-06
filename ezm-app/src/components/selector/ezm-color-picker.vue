<template>
    <div class="">
        <el-color-picker
            :value="value"
            @input="(v) => $emit('input',v)"
            :predefine="predefine"
        ></el-color-picker>
    </div>
</template>

<script>
    import {recommendedColor} from "@/utils/Enum";

    export default {
        name: 'ezm-color-picker',
        mixins: [],
        components: {},
        props: {
            value: {
                default: ''
            }
        },
        data() {
            return {
                predefine: recommendedColor
            }
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
    .ezm-color-picker {

    }
</style>