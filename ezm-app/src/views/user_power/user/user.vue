<template>
    <div class="user ezm-page-container">
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
            </vxe-table-column>

        </ezm-table>
        <ezm-edit-tab
            v-model="showEditWindow"
        >
            <user-edit
                :page-instance="this"
                :show-back-button="true"
                :save-close="false"
            />

            <ezm-window
                title="绑定租户与角色"
                :flex-show="true"
                class="mt-sm"
                :show-tabs="true"
                v-if="editForm.id"
                v-model="windowTabsShow"
                icon="el-icon-s-tools"
            >
                <ezm-window-item
                    title="租户隔离"
                    class="pa-xs"
                >
                    <tenant
                        :is-user-selector="true"
                        :parent-page-instance="pageInstance"

                        @on-selected-rows-change="onTenantSelectedChange"
                    ></tenant>
                </ezm-window-item>
                <ezm-window-item
                    title="角色绑定"
                    class="pa-xs"
                    v-if="nowSelectedTenantRole"
                >
                    <role
                        :is-user-selector="true"
                        :parent-page-instance="pageInstance"

                        @on-selected-rows-change="onRoleSelectedChange"
                    ></role>
                </ezm-window-item>

                <template slot="button-left">
                    <el-select
                        class="ml-sm"
                        placeholder="选择租户"
                        v-model="nowSelectedTenantRole "
                        v-if="nowSelectedTenantRole && windowTabsShow === '角色绑定' && selectedTenant.length > 1"
                    >
                        <el-option v-for="item in selectedTenant"
                                   :key="item.id"
                                   :value="item.id"
                                   :label="item.name"
                        ></el-option>
                    </el-select>

                    <el-button-group>
                        <el-button
                            v-if="selectedTenant.length > 1 && nowSelectedTenantRole && windowTabsShow === '角色绑定'"
                            icon="el-icon-document-copy"
                            title="将当前配置同步到其他隔离组（租户）"
                            @click="clickSyncConfig"
                        >同步配置
                        </el-button>
                    </el-button-group>


                    <el-button-group>
                        <el-button
                            type="primary"
                            icon="el-icon-circle-check"
                            :loading="loading"
                            @click="updateURT"
                        >更新权限
                        </el-button>
                    </el-button-group>
                </template>
            </ezm-window>

        </ezm-edit-tab>
    </div>
</template>

<script>
    import EzmTable from "@/components/table/ezm-table";
    import PageMixin from "@/mixin/page-mixin/PageMixin";
    import {userGet, userGetList, userRemove, userSave, userUpdateList, userUpdateURT} from "@/api/user_power/user";
    import UserFilter from "@/views/user_power/user/user-filter";
    import {arraySame, debThr, DialogUtil} from "@/utils/common.util";
    import UserEdit from "@/views/user_power/user/user-edit";
    import UserConfigMixin from "@/views/user_power/user/UserConfigMixin";
    import EzmEditTab from "@/components/editor/container/ezm-edit-tab";
    import EzmWindow from "@/components/window/ezm-window";
    import EzmWindowItem from "@/components/window/ezm-window-item";
    import Tenant from "@/views/user_power/tenant/tenant";
    import Role from "@/views/user_power/role/role";

    export default {
        name: 'user',
        mixins: [PageMixin, UserConfigMixin],
        components: {Role, Tenant, EzmWindowItem, EzmWindow, EzmEditTab, UserEdit, UserFilter, EzmTable},
        props: {},
        data() {
            return {
                pageName: '用户',
                userURT: {},
                selectedTenant: [],
                selectedRole: [],
                URT: {},
                nowSelectedTenantRole: '',
                windowTabsShow: '',
            }
        },
        computed: {
            tenantIds() {
                if (!this.userURT.tenantRolesStr) return [];
                return Object.keys(this.userURT.tenantRolesStr);
            },
            selectedRolesIds() {
                return this.URT[this.nowSelectedTenantRole] || [];
            }
        },
        watch: {
            selectedTenant(v) {
                if (!v.length) this.nowSelectedTenantRole = null;
                else this.nowSelectedTenantRole = v[0].id;
                let u = {};
                v.forEach(x => {
                    u[x.id] = this.URT[x.id] || [];
                });
                this.URT = u;
            },
            showEditWindow(v) {
                this.refreshTenantTab(v);

            },
            'editForm.id'(v) {
                this.refreshTenantTab(v);
            }
        },
        created() {
        },
        mounted() {
        },
        destroyed() {
        },
        methods: {
            refreshTenantTab(v) {
                if (!this.editForm.id) return;
                debThr.debounce(this._uid + 'refreshTenantTab', () => {
                    if (v) {
                        this.windowTabsShow = '租户隔离';
                        this.refreshUserURT();
                    }
                    if (!v) {
                        this.userURT = {};
                        this.selectedTenant = [];
                        this.selectedRole = [];
                        this.URT = {};
                    }
                });
            },
            clickSyncConfig() {
                let list = (this.URT[this.nowSelectedTenantRole] || []).concat();
                this.URT = {};
                this.selectedTenant.forEach(x => {
                    this.URT[x.id] = list;
                });
                DialogUtil.toast("配置已同步！");
            },
            updateURT() {
                this.loadingStart();
                userUpdateURT({
                    ...this.editForm,
                    tenantRoles: this.URT,
                    tenantRolesStr: this.URT,
                }).then(resp => {
                    DialogUtil.toast("用于角色与租户更新成功！");
                }).finally(() => this.loadingFinish());
            },
            refreshUserURT() {
                this.loadingStart();
                userGet(this.editForm).then(resp => {
                    this.userURT = resp;
                    this.URT = this.userURT.tenantRolesStr;
                    if (Object.keys(this.URT).length) {
                        console.log(Object.keys(this.URT)[0]);
                        this.nowSelectedTenantRole = Object.keys(this.URT)[0];
                    }
                }).finally(() => this.loadingFinish());
            },
            onTenantSelectedChange(v) {
                this.selectedTenant = v || [];
            },
            onRoleSelectedChange(v) {
                this.URT[this.nowSelectedTenantRole] = v.map(x => x.id);
            },
            reload() {
                this.beforeReload();
            },
            getList(cb = null) {
                this.loadingStart();
                userGetList({
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
                userSave(row).then(resp => {
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
                userUpdateList(rows).then(resp => {
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
                    userRemove({
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
    .user {

    }
</style>