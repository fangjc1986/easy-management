<template>
    <div class="index">
        <ezm-markdown>
            {{ md.content }}
        </ezm-markdown>
    </div>
</template>

<script>
    import ScrollMixin from "@/mixin/ScrollMixin";
    import EzmWindow from "@/components/window/ezm-window";
    import EzmWindowItem from "@/components/window/ezm-window-item";
    import EzmMarkdown from "@/components/display/ezm-markdown";

    const beautify = require('js-beautify');

    export default {
        name: 'index',
        mixins: [ScrollMixin],
        components: {EzmMarkdown, EzmWindowItem, EzmWindow},
        props: {},
        data() {
            return {
                r_is_page: true,
                loading: false,
                md: process.env.INDEX_MD
            }
        },
        computed: {
        },
        watch: {

        },
        created() {
        },
        mounted() {
            console.log(this.md);
        },
        destroyed() {
        },
        methods: {

        }
    };
</script>

<style scoped lang="less">
    .index {

    }

    .index-card {
        padding-left: .5vw;
        height: 6vw;
        box-sizing: border-box;

        .icon > i {
            font-size: 4vw;
        }

        .title {
            padding-top: 1.3vw;
            padding-left: .2vw;
            font-size: .7vw;
        }

        .content {
            font-size: 1.5vw;
            padding-bottom: .8vw;
            padding-right: 1vw;
        }
    }
</style>