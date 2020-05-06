<template>
    <div class="ezm-filter-field flex-box flex-center mr-sm">
        <div class="pr-xs">
            {{ field.label }}
        </div>
        <div class="">
            <render-filter-input
                :width="field.filterWidth || ''"
                :disabled="disabled"
                :field="field"
                :value="value"
                @input="(v) => $emit('input',v)"
            ></render-filter-input>
        </div>
    </div>
</template>

<script>
    import RenderFilterInput from "@/components/render/render-filter-input";

    export default {
        name: 'ezm-filter-field',
        mixins: [],
        components: {RenderFilterInput},
        props: {
            field: [Object],
            value: {
                default: '',
            },
            disabled: {
                default: false,
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
        methods: {}
    };
</script>

<style scoped lang="less">
    .ezm-filter-field {

    }
</style>