<template>
    <div class="request_log-selector">
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
                    <request_log
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
    import Request_log from "@/views/log/request_log/request_log";
    import EzmWindow from "@/components/window/ezm-window";
    import EzmWindowItem from "@/components/window/ezm-window-item";
    import {requestLogGetList} from "@/api/log/request_log";

    export default {
        name: 'request_log-selector',
        mixins: [SelectorMixin],
        components: {EzmDialog, EzmWindowItem, Request_log, EzmEditDialog},
        props: {
            fieldShow:{
                default: 'name',
            }
        },
        data() {
            return {
                listMethod: requestLogGetList,
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
    .request_log-selector {

    }
</style>