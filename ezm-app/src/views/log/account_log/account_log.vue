<template>
    <div class="account_log ezm-page-container">

        <ezm-table
            :page-instance="this"
            :height="height"
            :dbl-edit-disabled="true"
            class=""
            :show-row-operation="false"
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
            <account_log-edit
                :page-instance="this"
                :show-back-button="true"
            />
        </ezm-edit-tab>


    </div>
</template>

<script>
    import EzmTable from "@/components/table/ezm-table";
    import PageMixin from "@/mixin/page-mixin/PageMixin";
    import {accountLogGetList, accountLogRemove, accountLogSave, accountLogUpdateList} from "@/api/log/account_log";
    import Account_logFilter from "@/views/log/account_log/account_log-filter";
    import {DialogUtil} from "@/utils/common.util";
    import Account_logEdit from "@/views/log/account_log/account_log-edit";
    import AccountLogConfigMixin from "@/views/log/account_log/AccountLogConfigMixin";
    import EzmEditTab from "@/components/editor/container/ezm-edit-tab";

    export default {
        name: 'account_log',
        mixins: [PageMixin, AccountLogConfigMixin],
        components: {EzmEditTab, Account_logEdit, Account_logFilter, EzmTable},
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
                accountLogGetList({
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
                accountLogSave(row).then(resp => {
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
                accountLogUpdateList(rows).then(resp => {
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
                    accountLogRemove({
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
    .account_log {

    }
</style>