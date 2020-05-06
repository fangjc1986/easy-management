<template>
    <div class="auth-selector">
        <el-input
            :value="showTextComp"
            :disabled="disabled"
            :placeholder="placeholder"

            @click="clickInput"
            @input="onInput"
        >
            <el-button
                slot="append"
                icon="el-icon-coin"
                @click="clickButton"
                :disabled="disabled"
            ></el-button>
        </el-input>

        <ezm-dialog
            v-model="show"
            :in-body="false"
            :width="width"
        >
            <ezm-window :max-height="500"
                        :title="titleComp"
                        icon="el-icon-coin"
            >
                <ezm-window-item class="pa-xs">
                    <auth-selector-list
                        :is-selector="true"
                        height="300px"
                        :selector-instance="instance"
                        :select-type="selectType"
                    ></auth-selector-list>
                </ezm-window-item>
            </ezm-window>
        </ezm-dialog>
    </div>
</template>

<script>
    import EzmEditDialog from "@/components/editor/container/ezm-edit-dialog";
    import SelectorMixin from "@/mixin/SelectorMixin";
    import EzmDialog from "@/components/display/ezm-dialog";
    import AuthSelectorList from "@/views/user_power/auth/auth-selector-list";
    import EzmWindow from "@/components/window/ezm-window";
    import EzmWindowItem from "@/components/window/ezm-window-item";
    import {authGetList} from "@/api/user_power/auth";

    export default {
        name: 'auth-selector',
        mixins: [SelectorMixin],
        components: {EzmDialog, EzmWindowItem, AuthSelectorList, EzmEditDialog},
        props: {
            fieldShow: {
                default: 'name',
            },
            defaultShow:{
                default: '顶级菜单或权限',
            },
            defaultValue:{
                default: '0'
            }
        },
        data() {
            return {
                listMethod: authGetList,
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
    .auth-selector {

    }
</style>