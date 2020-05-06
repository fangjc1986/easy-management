<template>
    <div class="account_log-selector">
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
                    <account_log
                        :is-selector="true"
                        height="300px"
                        :selector-instance="instance"
                        :select-type="selectType"
                        :expand-filter-window="false"
                        :default-filter-form="defaultFilterForm"
                        :default-filter-locked="defaultFilterLocked"
                    />
                </ezm-window-item>
            </ezm-window>
        </ezm-dialog>
    </div>
</template>

<script>
    import EzmEditDialog from "@/components/editor/container/ezm-edit-dialog";
    import SelectorMixin from "@/mixin/SelectorMixin";
    import EzmDialog from "@/components/display/ezm-dialog";
    import Account_log from "@/views/log/account_log/account_log";
    import EzmWindow from "@/components/window/ezm-window";
    import EzmWindowItem from "@/components/window/ezm-window-item";
    import {accountLogGetList} from "@/api/log/account_log";

    export default {
        name: 'account_log-selector',
        mixins: [SelectorMixin],
        components: {EzmDialog, EzmWindowItem, Account_log, EzmEditDialog},
        props: {
            fieldShow:{
                default: 'name',
            }
        },
        data() {
            return {
                listMethod: accountLogGetList,
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
    .account_log-selector {

    }
</style>