<template>
    <div class="ezm-text-input">
        <el-input
            :value="value"
            :disabled="disabled"
            :placeholder="placeholder"
            @input="(v)=> $emit('input', v)"
        >
            <el-button
                slot="append"
                icon="el-icon-edit-outline"
                @click="showDialog = true"
                title="点击打开长文本编辑窗口"
            />
        </el-input>
        <vxe-modal
            v-model="showDialog"
            :title="title"
            :lock-scroll="false"
            width="500px"
            :mask-closable="true"
            icon-status="el-icon-edit-outline"
        >
            <el-input
                class="mtb-xs"
                rows="12"
                type="textarea"
                :value="value"
                :disabled="disabled"
                :placeholder="placeholder"
                @input="(v)=> $emit('input', v)"
            ></el-input>
        </vxe-modal>
    </div>
</template>

<script>
    export default {
        name: 'ezm-text-input',
        mixins: [],
        components: {},
        props: {
            value: {
                default: '',
            },
            disabled: {
                default: false,
            },
            placeholder: {
                default: "请输入文本"
            },
            title: {
                default: "长文本编辑"
            },
        },
        data() {
            return {
                showDialog: false,
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
    .ezm-text-input {

    }
</style>