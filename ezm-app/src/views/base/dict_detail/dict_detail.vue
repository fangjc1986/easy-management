<template>
    <div class="dict_detail ezm-page-container ">

        <dict_detail-filter
            class="mb-sm"
            :pageInstance="this"
            v-if="false"
        />
        <ezm-table
            :page-instance="this"
            :height="height"
            :dbl-edit-disabled="true"
            class=""
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
                <template v-slot:edit="{row}" v-if="field.prop === 'color'">
                    <ezm-color-picker class="pt-xxs" v-model="row.color"></ezm-color-picker>
                </template>
            </vxe-table-column>

        </ezm-table>
        <ezm-dialog
            v-model="showEditWindow"
            width="400px"
        >
            <dict_detail-edit
                :page-instance="this"
            />
        </ezm-dialog>
    </div>
</template>

<script>
    import EzmTable from "@/components/table/ezm-table";
    import PageMixin from "@/mixin/page-mixin/PageMixin";
    import {dictDetailGetList, dictDetailRemove, dictDetailSave, dictDetailUpdateList} from "@/api/base/dict_detail";
    import Dict_detailFilter from "@/views/base/dict_detail/dict_detail-filter";
    import {DialogUtil} from "@/utils/common.util";
    import Dict_detailEdit from "@/views/base/dict_detail/dict_detail-edit";
    import DictDetailConfigMixin from "@/views/base/dict_detail/DictDetailConfigMixin";
    import EzmEditDialog from "@/components/editor/container/ezm-edit-dialog";
    import EzmWindowItem from "@/components/window/ezm-window-item";
    import EzmWindow from "@/components/window/ezm-window";
    import EzmEditTab from "@/components/editor/container/ezm-edit-tab";
    import EzmEditWindow from "@/components/editor/container/ezm-edit-window";
    import EzmDialog from "@/components/display/ezm-dialog";

    export default {
        name: 'dict_detail',
        mixins: [PageMixin, DictDetailConfigMixin],
        components: {
            EzmDialog,
            EzmEditWindow,
            EzmEditTab, EzmWindow, EzmWindowItem, EzmEditDialog, Dict_detailEdit, Dict_detailFilter, EzmTable
        },
        props: {},
        data() {
            return {
                pageName: '字典明细',
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
                dictDetailGetList({
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
                dictDetailSave(row).then(resp => {
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
                dictDetailUpdateList(rows).then(resp => {
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
                    dictDetailRemove({
                        ids: rows.map(x => x.id)
                    }).then(resp => {
                        DialogUtil.toast("删除成功！");
                        this.getList();
                        this.noticeUpdate();
                        cb && cb();
                    }).finally(() => this.loadingFinish());
                })
            },
            parentNoticeUpdate() {
                this.refreshList();
            }
        }
    };
</script>

<style scoped lang="less">
    .dict_detail {

    }
</style>