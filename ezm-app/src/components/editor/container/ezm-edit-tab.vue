<template>
    <transition name="fade-right">
        <div class="ezm-edit-tab pos-a flex-grow flex-box flex-column pa-sm scroll-y-thin bg-white"
             style="left: 0;right: 0;bottom: 0;top: 0;z-index: 100;box-sizing: border-box;"
             v-if="value"
        >
            <slot></slot>
        </div>
    </transition>

</template>

<script>
    import ReloadMixin from "@/mixin/ReloadMixin";

    export default {
        name: 'ezm-edit-tab',
        mixins: [ReloadMixin],
        components: {},
        props: {
            value: {
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
            unload() {
                this.$emit('input', false);
            }
        }
    };
</script>

<style scoped lang="less">
    .ezm-edit-tab {

    }
</style>