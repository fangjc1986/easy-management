<template>
    <div class="config-selector">
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
                    <config
                        :is-selector="true"
                        height="300px"
                        :selector-instance="instance"
                        :select-type="selectType"
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
    import Config from "@/views/base/config/config";
    import EzmWindow from "@/components/window/ezm-window";
    import EzmWindowItem from "@/components/window/ezm-window-item";
    import {configGetList} from "@/api/base/config";

    export default {
        name: 'config-selector',
        mixins: [SelectorMixin],
        components: {EzmDialog, EzmWindowItem, Config, EzmEditDialog},
        props: {
            fieldShow:{
                default: 'name',
            }
        },
        data() {
            return {
                listMethod: configGetList,
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
    .config-selector {

    }
</style>