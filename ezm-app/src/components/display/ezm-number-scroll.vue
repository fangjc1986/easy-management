<template>
    <span class="ezm-number-scroll">
        {{ number }}
    </span>
</template>

<script>
    export default {
        name: 'ezm-number-scroll',
        mixins: [],
        components: {},
        props: {
            value: {
                default: 0,
            },
            interval:{
                default: 50,
            },

        },
        data() {
            return {
                number: this.value,
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
    .ezm-number-scroll {

    }
</style>