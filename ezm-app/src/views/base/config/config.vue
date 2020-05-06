<template>
    <div class="config ezm-page-container">

        <ezm-table
            :page-instance="this"
            :height="null"
            :dbl-edit-disabled="true"
            class="flex-grow overflow-h pb-sm"
        >
            <vxe-table-column
                v-for="field in fields"
                :field="field.prop"
                :key="field.prop"
                :sortable="field.sortable !== false"
                :title="field.label"
                :width="field.width"
                v-if="field.show!==false"
                :edit-render="field.tableEditRender"
                :filters="field.filters"
                :filter-render="field.filterRender"
                :cell-render="field.cellRender"
                :formatter="field.formatter"
                :params="field.params || {}"
            >
                <template v-slot:edit="{row}" v-if="field.prop=== 'value'">
                    <el-input v-model="row.value" v-if="row.type==='INPUT'"></el-input>
                    <ezm-switch v-model="row.value" v-if="row.type==='SWITCH'"></ezm-switch>
                </template>
            </vxe-table-column>

        </ezm-table>
        <ezm-edit-window
            v-model="showEditWindow"
            width="400px"
        >
            <config-edit
                :page-instance="this"
            />
        </ezm-edit-window>


    </div>
</template>

<script>
    import EzmTable from "@/components/table/ezm-table";
    import PageMixin from "@/mixin/page-mixin/PageMixin";
    import {configGetList, configRemove, configSave, configUpdateList} from "@/api/base/config";
    import ConfigFilter from "@/views/base/config/config-filter";
    import {DialogUtil} from "@/utils/common.util";
    import ConfigEdit from "@/views/base/config/config-edit";
    import ConfigConfigMixin from "@/views/base/config/ConfigConfigMixin";
    import EzmEditTab from "@/components/editor/container/ezm-edit-tab";
    import EzmEditWindow from "@/components/editor/container/ezm-edit-window";
    import EzmSwitch from "@/components/editor/ezm-switch";

    export default {
        name: 'config',
        mixins: [PageMixin, ConfigConfigMixin],
        components: {EzmSwitch, EzmEditWindow, EzmEditTab, ConfigEdit, ConfigFilter, EzmTable},
        props: {},
        data() {
            return {
                pageName: '页面名称',
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
        methods: {
            reload() {
                this.beforeReload();
            },
            getList(cb = null) {
                this.loadingStart();
                configGetList({
                    ...this.getFilterForm(),
                    ...this.paginate.getRequestParams(),
                }).then(resp => {
                    this.paginate.setResponse(resp);
                    this.getListSuccess();
                    cb && cb();
                }).finally(() => {
                    this.loadingFinish();
                });
            },
            saveRow(row, cb = null) {
                this.loadingStart();
                configSave(row).then(resp => {
                    DialogUtil.toast(row.id ? "修改成功！" : "新增成功！");
                    this.editForm = resp;
                    this.getList();
                    this.noticeUpdate();
                    cb && cb();
                }).finally(() => {
                    this.loadingFinish();
                })
            },
            updateRows(rows, cb = null) {
                this.loadingStart();
                configUpdateList(rows).then(resp => {
                    DialogUtil.toast("更新成功!");
                    this.ezmTable.reloadData();
                    this.noticeUpdate();
                    cb && cb();
                }).finally(() => {
                    this.loadingFinish();
                })
            },
            removeRows(rows, cb = null) {
                DialogUtil.confirm("确定删除？").then(() => {
                    this.loadingStart();
                    configRemove({
                        ids: rows.map(x => x.id)
                    }).then(resp => {
                        DialogUtil.toast("删除成功！");
                        this.getList();
                        this.noticeUpdate();
                        cb && cb();
                    }).finally(()=> this.loadingFinish());
                })
            }
        }
    };
</script>

<style scoped lang="less">
    .config {

    }
</style>