<template>
    <div class="role ezm-page-container">

        <ezm-table
            :page-instance="this"
            :height="height"
            :dbl-edit-disabled="true"
            class=""
            :show-tool-bar="!isUserSelector"
            :show-row-operation="!isUserSelector"
            :default-selected-rows="isUserSelector? {id: parentPageInstance.selectedRolesIds} : {}"

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
                :edit-render="isUserSelector?null :field.tableEditRender"
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
            v-if="!isUserSelector"
        >
            <role-edit
                :page-instance="this"
                :show-back-button="true"
                :save-close="false"
            />
            <ezm-window
                :flex-show="true"
                class="mt-sm"
                :show-tabs="true"
                title="权限配置"
                icon="el-icon-s-tools"
                v-if="editForm.id"
            >
                <ezm-window-item title="菜单页面" class="pa-xs">
                    <auth
                        v-if="editForm.id"
                        :is-role-selected="true"
                        :parent-page-instance="pageInstance"
                        show-auth-type="page"
                        :defaultSelectRows="authList"
                        @on-selected-rows-change="pageSelectedChange"
                    ></auth>
                </ezm-window-item>
                <ezm-window-item title="权限按钮" class="pa-xs">
                    <auth
                        v-if="editForm.id"
                        :is-role-selected="true"
                        show-auth-type="auth"
                        :parent-page-instance="pageInstance"
                        :defaultSelectRows="authList"
                        @on-selected-rows-change="authSelectedChange"
                    ></auth>
                </ezm-window-item>

                <template slot="button-left">
                    <el-button
                        icon="el-icon-circle-check"
                        type="primary"
                        :loading="loading"
                        @click="clickUpdateRoleAuth"
                    >更新权限
                    </el-button>
                </template>
            </ezm-window>

        </ezm-edit-tab>


    </div>
</template>

<script>
    import EzmTable from "@/components/table/ezm-table";
    import PageMixin from "@/mixin/page-mixin/PageMixin";
    import {
        roleAuthList,
        roleBindAuths,
        roleGetList,
        roleRemove,
        roleSave,
        roleUpdateList
    } from "@/api/user_power/role";
    import RoleFilter from "@/views/user_power/role/role-filter";
    import {debThr, DialogUtil} from "@/utils/common.util";
    import RoleEdit from "@/views/user_power/role/role-edit";
    import RoleConfigMixin from "@/views/user_power/role/RoleConfigMixin";
    import EzmEditTab from "@/components/editor/container/ezm-edit-tab";
    import Auth from "@/views/user_power/auth/auth";
    import Paginate from "@/utils/Paginate";
    import EzmWindow from "@/components/window/ezm-window";
    import EzmWindowItem from "@/components/window/ezm-window-item";

    export default {
        name: 'role',
        mixins: [PageMixin, RoleConfigMixin],
        components: {EzmWindowItem, EzmWindow, Auth, EzmEditTab, RoleEdit, RoleFilter, EzmTable},
        props: {
            isUserSelector: {
                default: false,
            }
        },
        data() {
            return {
                pageName: '角色',
                authList: [],
                selectedAuth: [],
                selectedPage: [],
                paginate: new Paginate(this, this.getList, 999),
            }
        },
        computed: {},
        watch: {
            showEditWindow(v) {
                this.refreshTabWindow(v);
            },
            'editForm.id'(v) {
                this.refreshTabWindow(v);
            }
        },
        created() {
        },
        mounted() {
        },
        destroyed() {
        },
        methods: {
            refreshTabWindow(v) {
                debThr.debounce(this._uid + 'refreshTabWindow', () => {
                    if (v && this.editForm.id) this.refreshAuthList();
                    if (!v) {
                        this.authList = [];
                        this.selectedAuth = [];
                        this.selectedPage = [];
                    }
                });
            },
            pageSelectedChange(pages) {
                this.selectedPage = pages;
            },
            authSelectedChange(auths) {
                this.selectedAuth = auths.filter(x => x.type !== 1);
            },
            clickUpdateRoleAuth() {
                this.loadingStart();
                roleBindAuths({
                    ...this.editForm,
                    authIds: [...new Set(this.selectedAuth.map(x => x.id).concat(this.selectedPage.map(x => x.id)))]
                }).then(resp => {
                    DialogUtil.toast("权限更新成功");
                    this.refreshAuthList();
                    this.getList();
                }).finally(() => this.loadingFinish());
            },
            refreshAuthList() {
                this.loadingStart();
                roleAuthList(this.editForm).then(resp => {
                    this.authList = resp;
                    this.selectedAuth = this.authList.filter(x => x.type === 2);
                    this.selectedPage = this.authList.filter(x => x.type === 1);
                }).finally(() => this.loadingFinish());
            },
            reload() {
                this.beforeReload();
            },
            getList(cb = null) {
                this.loadingStart();
                roleGetList({
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
                roleSave(row).then(resp => {
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
                roleUpdateList(rows).then(resp => {
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
                    roleRemove({
                        ids: rows.map(x => x.id)
                    }).then(resp => {
                        DialogUtil.toast("删除成功！");
                        this.getList();
                        this.noticeUpdate();
                        cb && cb();
                    }).finally(() => this.loadingFinish());
                })
            }
        }
    };
</script>

<style scoped lang="less">
    .role {

    }
</style>