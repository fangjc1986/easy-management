<template>
    <div class="account_log-filter filter-container">
        <ezm-window
            icon="iconfont icon-tasks"
            :title="`筛选 [ ${pageInstance.pageName} ]`"
            container-class="plr-sm ptb-xs"
            :expand="pageInstance.filterWindow"
        >
            <template slot="button-left">
                <el-button-group>
                    <el-button
                        icon="el-icon-search"
                        :loading="pageInstance.loading"
                        @click="pageInstance.refreshList()"
                    >查询
                    </el-button>
                </el-button-group>
            </template>
            <ezm-window-item title="一般">
                <div class="filter-field-container">
                    <ezm-filter-field
                        class="ptb-xxs"
                        v-for="field in fieldsComp"
                        :key="field.prop"
                        :field="field"
                        v-if="field.filterRender"
                        :disabled="getInputDisabled(field)"
                        v-model="pageInstance.filterForm[getFieldName(field)]"
                    ></ezm-filter-field>
                </div>
            </ezm-window-item>
        </ezm-window>
    </div>
</template>

<script>
    import EzmWindow from "@/components/window/ezm-window";
    import EzmWindowItem from "@/components/window/ezm-window-item";
    import EzmFilterField from "@/components/editor/ezm-filter-field";
    import FilterMixin from "@/mixin/FilterMixin";

    export default {
        name: 'account_log-filter',
        mixins: [FilterMixin],
        components: {EzmFilterField, EzmWindowItem, EzmWindow},
        props: {},
        data() {
            return {
                test1: '',
                filterForm: {}
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
    .account_log-filter {

    }
</style>