<template>
    <div class="auth-selector-list ezm-page-container">

        <ezm-table
            :page-instance="this"
            :height="height"
            :dbl-edit-disabled="true"
            class=""
            :tree-config="{expandAll: true}"
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
                :content-render="field.contentRender"
                :formatter="field.formatter"
                :params="field.params || {}"
                :tree-node="field.treeNode"
            >
                <template v-slot="{row}" v-if="field.prop === 'icon'">
                   <i class="iconfont mr-xs" :class="row.icon" v-if="row.icon"></i> {{ row.icon }}
                </template>
            </vxe-table-column>

        </ezm-table>
    </div>
</template>

<script>
    import EzmTable from "@/components/table/ezm-table";
    import PageMixin from "@/mixin/page-mixin/PageMixin";
    import {authGetList, authRemove, authSave, authUpdateList} from "@/api/user_power/auth";
    import {DialogUtil, list2Tree} from "@/utils/common.util";
    import AuthConfigMixin from "@/views/user_power/auth/AuthConfigMixin";
    import EzmEditTab from "@/components/editor/container/ezm-edit-tab";

    export default {
        name: 'auth-selector-list',
        mixins: [PageMixin, AuthConfigMixin],
        components: {
               EzmEditTab, EzmTable
        },
        props: {

        },
        data() {
            return {
                pageName: '菜单权限',
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
                authGetList({
                    ...this.getFilterForm(),
                    ...this.paginate.getRequestParams(),
                }).then(resp => {
                    resp.records = list2Tree(resp.records);
                    this.paginate.setResponse(resp);
                    cb && cb();
                }).finally(() => {
                    this.loadingFinish();
                });
            },
            saveRow(row, cb = null) {
                this.loadingStart();
                authSave(row).then(resp => {
                    DialogUtil.toast(row.id ? "修改成功！" : "新增成功！");
                    this.editForm = resp;
                    this.getList();
                    this.noticeUpdate();
                    this.$store.commit("route/refreshRoute");
                    cb && cb();
                }).finally(() => {
                    this.loadingFinish();
                })
            },
            updateRows(rows, cb = null) {
                this.loadingStart();
                authUpdateList(rows).then(resp => {
                    DialogUtil.toast("更新成功!");
                    this.ezmTable.reloadData();
                    this.noticeUpdate();
                    this.$store.commit("route/refreshRoute");
                    cb && cb();
                }).finally(() => {
                    this.loadingFinish();
                })
            },
            removeRows(rows, cb = null) {
                DialogUtil.confirm("确定删除？").then(() => {
                    this.loadingStart();
                    authRemove({
                        ids: rows.map(x => x.id)
                    }).then(resp => {
                        DialogUtil.toast("删除成功！");
                        this.getList();
                        this.noticeUpdate();
                        cb && cb();
                        this.loadingFinish();
                        this.$store.commit("route/refreshRoute");
                    });
                })
            }
        }
    };
</script>

<style scoped lang="less">
    .auth-selector-list {

    }
</style>