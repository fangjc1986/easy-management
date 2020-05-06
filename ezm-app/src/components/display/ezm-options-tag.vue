<template>
    <div class="ezm-options-tag">
        <el-tag
            :type="null"
            :style="{'border-color' : color }"
            :color="color"
            :effect="effect"
            v-if="!onlyText"
        >
            {{ showText }}
        </el-tag>
        <template v-else>{{ showText }}</template>
    </div>
</template>

<script>
    export default {
        name: 'ezm-options-tag',
        mixins: [],
        components: {},
        props: {
            value: {
                default: '',
            },
            options: {
                default: () => [],
            },
            effect: {
                default: 'dark',
            },
            onlyText: {
                default: false,
            }
        },
        data() {
            return {}
        },
        computed: {
            showText() {
                return (this.options.find(x => x.value === this.value) || {}).label || this.value;
            },
            color() {
                return (this.options.find(x => x.value === this.value) || {}).color || "";
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
    .ezm-options-tag {
        display: inline-block;
    }
</style>