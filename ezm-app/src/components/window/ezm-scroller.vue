<template>
    <div class="ezm-scroller"
         :style="scrollerStyle"
    >
        <slot></slot>
    </div>
</template>

<script>
    import ScrollMixin from "@/mixin/ScrollMixin";

    export default {
        name: 'ezm-scroller',
        mixins: [ScrollMixin],
        components: {},
        props: {
            height: {
                default: 'auto'
            },
            maxHeight: {
                default: 'auto'
            }
        },
        data() {
            return {}
        },
        computed: {
            scrollerStyle() {
                return {
                    'height': this.height,
                    'max-height': this.maxHeight,
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
    .ezm-scroller {
        overflow-x: hidden;
        overflow-y: auto;
    }
</style>