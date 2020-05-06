<template>
    <div class="ezm-edit-dialog">
        <vxe-modal
            :value="value"
            @input="(v)=>$emit('input', v)"
            :width="width"
            :mask="false"
            :destroy-on-close="destroyOnClose"
            :lock-scroll="true"
            :mask-closable="maskClosable"
            :height="height"
            :min-height="minHeight"
        >
            <template slot="header">
                <div class="close-button"
                     @click="$emit('input',false)"
                >
                    <i class="iconfont icon-times"></i>
                </div>
            </template>
            <div class="scroll-y-thin flex-box flex-column wh100p  pos-r pa-xs" style="box-sizing: border-box"
                :style="contentStyle"
            >
                <slot></slot>
            </div>
        </vxe-modal>
    </div>
</template>

<script>
    export default {
        name: 'ezm-edit-dialog',
        mixins: [],
        components: {},
        props: {
            value: {
                default: false,
            },
            width: {
                default: '80%',
            },
            height: {
                default: 'auto',
            },
            maskClosable: {
                default: false,
            },
            minHeight:{
                default: 'auto'
            },
            destroyOnClose:{
                default: true,
            }
        },
        data() {
            return {}
        },
        computed: {
            contentStyle(){
                return {
                }
            },
            dialogStyle(){
                return {
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
    .ezm-edit-dialog {
        .close-button {
            position: absolute;
            @size: 20px;
            @thmColor: #ff2165;
            height: 20px;
            width: 20px;
            border: 1px solid @thmColor;
            color: #ffffff;
            background-color: @thmColor;
            cursor: pointer;
            line-height: 20px;
            text-align: center;
            border-radius: 50%;
            right: -@size/2;
            top: -@size/2;
            transition: ease all .4s;
            box-shadow: 0 0 5px @thmColor;
            z-index: 101;

            &:hover {
                transform: scale(1.2);
            }
        }
    }
</style>