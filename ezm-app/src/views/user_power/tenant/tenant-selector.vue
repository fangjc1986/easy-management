<template>
    <div class="tenant-selector">
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
                    <tenant
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
    import Tenant from "@/views/user_power/tenant/tenant";
    import EzmWindow from "@/components/window/ezm-window";
    import EzmWindowItem from "@/components/window/ezm-window-item";
    import {tenantGetList} from "@/api/user_power/tenant";

    export default {
        name: 'tenant-selector',
        mixins: [SelectorMixin],
        components: {EzmDialog, EzmWindowItem, Tenant, EzmEditDialog},
        props: {
            fieldShow: {
                default: 'name',
            }
        },
        data() {
            return {
                listMethod: tenantGetList,
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
    .tenant-selector {

    }
</style>