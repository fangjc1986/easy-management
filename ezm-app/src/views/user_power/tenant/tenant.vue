<template>
    <div class="tenant ezm-page-container">

        <ezm-table
            :page-instance="this"
            :height="height"
            :dbl-edit-disabled="true"
            class=""
            :show-row-operation="!isUserSelector"
            :show-tool-bar="!isUserSelector"
            :default-selected-rows="isUserSelector ? {id: parentPageInstance.tenantIds} : {}"

            @on-selected-rows-change="(v)=>$emit('on-selected-rows-change',v)"
        >
            <vxe-table-column
                v-for="field in fields"
                :field="field.prop"
                :key="field.prop"
                :sortable="field.sortable !== false"
                :title="field.label"
                :width="field.width"
                v-if="field.show!==false"
                :edit-render="isUserSelector? null :field.tableEditRender"
                :filters="field.filters"
                :filter-render="field.filterRender"
                :cell-render="field.cellRender"
                :formatter="field.formatter"
                :params="field.params || {}"
            >
            </vxe-table-column>

        </ezm-table>
        <ezm-dialog
            v-model="showEditWindow"
            width="500px"
            :fixed="false"
        >
            <tenant-edit
                :page-instance="this"
            />
        </ezm-dialog>


    </div>
</template>

<script>
    import EzmTable from "@/components/table/ezm-table";
    import PageMixin from "@/mixin/page-mixin/PageMixin";
    import {tenantGetList, tenantRemove, tenantSave, tenantUpdateList} from "@/api/user_power/tenant";
    import TenantFilter from "@/views/user_power/tenant/tenant-filter";
    import {DialogUtil} from "@/utils/common.util";
    import TenantEdit from "@/views/user_power/tenant/tenant-edit";
    import TenantConfigMixin from "@/views/user_power/tenant/TenantConfigMixin";
    import EzmEditTab from "@/components/editor/container/ezm-edit-tab";
    import EzmEditWindow from "@/components/editor/container/ezm-edit-window";
    import Paginate from "@/utils/Paginate";
    import EzmDialog from "@/components/display/ezm-dialog";

    export default {
        name: 'tenant',
        mixins: [PageMixin, TenantConfigMixin],
        components: {EzmDialog, EzmEditWindow, EzmEditTab, TenantEdit, TenantFilter, EzmTable},
        props: {
            isUserSelector:{
                default:false,
            },
        },
        data() {
            return {
                pageName: '隔离组',
                paginate: new Paginate(this, this.getList, 999),
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
                tenantGetList({
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
                tenantSave(row).then(resp => {
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
                tenantUpdateList(rows).then(resp => {
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
                    tenantRemove({
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
    .tenant {

    }
</style>