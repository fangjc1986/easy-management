<template>
    <div class="request_log ezm-page-container">

        <ezm-table
            :page-instance="this"
            :height="height"
            :dbl-edit-disabled="true"
            class=""
            :disable-operate-button="true"
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
            </vxe-table-column>

        </ezm-table>
        <ezm-edit-tab
            v-model="showEditWindow"
        >
            <request_log-edit
                :page-instance="this"
                :show-back-button="true"
            />
        </ezm-edit-tab>


    </div>
</template>

<script>
    import EzmTable from "@/components/table/ezm-table";
    import PageMixin from "@/mixin/page-mixin/PageMixin";
    import {requestLogGetList, requestLogRemove, requestLogSave, requestLogUpdateList} from "@/api/log/request_log";
    import Request_logFilter from "@/views/log/request_log/request_log-filter";
    import {DialogUtil} from "@/utils/common.util";
    import Request_logEdit from "@/views/log/request_log/request_log-edit";
    import RequestLogConfigMixin from "@/views/log/request_log/RequestLogConfigMixin";
    import EzmEditTab from "@/components/editor/container/ezm-edit-tab";

    export default {
        name: 'request_log',
        mixins: [PageMixin, RequestLogConfigMixin],
        components: {EzmEditTab, Request_logEdit, Request_logFilter, EzmTable},
        props: {},
        data() {
            return {
                pageName: "页面名称",
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
                requestLogGetList({
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
                requestLogSave(row).then(resp => {
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
                requestLogUpdateList(rows).then(resp => {
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
                    requestLogRemove({
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
    .request_log {

    }
</style>