<template>
    <div class="auth ezm-page-container">

        <ezm-table
            :page-instance="this"
            :height="height"
            :dbl-edit-disabled="true"
            class=""
            :tree-config="{expandAll: treeExpansion}"
            :show-row-operation="!isRoleSelected"
            :default-selected-rows="defaultSelectRowsComp"
            :show-tool-bar="!isRoleSelected"
            row-operation-width="80"

            @on-selected-rows-change="(v)=>$emit('on-selected-rows-change',v)"
        >
            <vxe-table-column
                v-for="field in fields"
                :fixed="field.prop === 'name' ? 'left' : ''"
                :field="field.prop"
                :key="field.prop"
                :sortable="field.sortable !== false"
                :title="field.label"
                :width="field.width"
                v-if="field.show!==false"
                :edit-render="!isRoleSelected ? field.tableEditRender : null"
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

            <template v-slot:row_operation_right="{row}">
                <i class="iconfont icon-plus-circle"
                   title="添加子权限"
                   @click="clickAddChild(row)"
                ></i>
                <i class="iconfont icon-telegram"
                   title="快速生成CRUD子接口"
                   @click="clickAddCRUD(row)"
                ></i>
            </template>

            <template slot="buttons-right">
                <el-button-group>
                    <el-button
                        icon="iconfont icon-stream"
                        @click="ezmTable.$refs.table.setAllTreeExpansion(treeExpansion=!treeExpansion)"
                    >切换折叠状态
                    </el-button>
                </el-button-group>
            </template>

        </ezm-table>
        <ezm-dialog
            v-model="showEditWindow"
            width="600px"
            v-if="!isSelector"
            :fixed="false"
        >
            <auth-edit
                :page-instance="this"
            />
        </ezm-dialog>
        <ezm-dialog
            v-model="showAddCRUDDialog"
            width="500"
        >
            <ezm-window
                title="添加CRUD接口"
                icon="iconfont icon-telegram"
            >
                <ezm-window-item class="pa-sm">
                    <el-form class="ezm-edit-form">
                        <el-row>
                            <el-col class="edit-form-col" :span="24">
                                <el-form-item label="API前缀地址">
                                    <el-input v-model="editAddCRUDForm.path"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col class="edit-form-col" :span="24">
                                <el-form-item label="接口名称前缀">
                                    <el-input v-model="editAddCRUDForm.name"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </ezm-window-item>

                <template slot="button-left">
                    <el-button-group>
                        <el-button
                            icon="iconfont icon-save"
                            :loading="loading"
                            @click="clickCreateCRUD"
                        >确认生成
                        </el-button>
                    </el-button-group>
                </template>
            </ezm-window>
        </ezm-dialog>
    </div>
</template>

<script>
    import EzmTable from "@/components/table/ezm-table";
    import PageMixin from "@/mixin/page-mixin/PageMixin";
    import {authAddCRUD, authGetList, authRemove, authSave, authUpdateList} from "@/api/user_power/auth";
    import AuthFilter from "@/views/user_power/auth/auth-filter";
    import {DialogUtil, list2Tree} from "@/utils/common.util";
    import AuthEdit from "@/views/user_power/auth/auth-edit";
    import AuthConfigMixin from "@/views/user_power/auth/AuthConfigMixin";
    import EzmEditTab from "@/components/editor/container/ezm-edit-tab";
    import EzmEditWindow from "@/components/editor/container/ezm-edit-window";
    import EzmEditDialog from "@/components/editor/container/ezm-edit-dialog";
    import Paginate from "@/utils/Paginate";
    import EzmDialog from "@/components/display/ezm-dialog";
    import EzmWindow from "@/components/window/ezm-window";
    import EzmWindowItem from "@/components/window/ezm-window-item";

    export default {
        name: 'auth',
        mixins: [PageMixin, AuthConfigMixin],
        components: {
            EzmWindowItem,
            EzmWindow,
            EzmDialog,
            EzmEditDialog, EzmEditWindow, EzmEditTab, AuthEdit, AuthFilter, EzmTable
        },
        props: {
            isRoleSelected: {
                default: false,
            },
            defaultSelectRows: {
                default: () => [],
            },
            showAuthType: {
                default: 'page',
            }
        },
        data() {
            return {
                pageName: '菜单权限',
                paginate: new Paginate(this, this.getList, 999),
                treeExpansion: false,
                showAddCRUDDialog: false,
                editAddCRUDForm: {},
            }
        },
        computed: {
            defaultSelectRowsComp() {
                let ids = [];
                if (this.showAuthType === 'page') {
                    ids = this.defaultSelectRows.filter(x => x.type === 1).map(x => x.id);
                } else {
                    ids = this.defaultSelectRows.filter(x => x.type !== 1).map(x => x.id);
                }
                return {
                    id: ids,
                }
            }
        },
        watch: {},
        created() {
        },
        mounted() {
        },
        destroyed() {
        },
        methods: {
            clickAddCRUD(row) {
                this.editAddCRUDForm = Object.assign({}, row);
                this.showAddCRUDDialog = true;
            },
            clickCreateCRUD() {
                this.loadingStart();
                authAddCRUD({
                    ids: [this.editAddCRUDForm.id],
                    p1: this.editAddCRUDForm.path,
                    p2: this.editAddCRUDForm.name,
                }).then(resp => {
                    DialogUtil.toast("生成CRUD接口成功！");
                    this.getListAndClearCheck();
                    this.showAddCRUDDialog = false;
                }).finally(() => this.loadingFinish());
            },
            reload() {
                this.beforeReload();
            },
            getList(cb = null) {
                this.loadingStart();
                authGetList({
                    ...this.getFilterForm(),
                    ...this.paginate.getRequestParams(),
                }).then(resp => {
                    if (this.isRoleSelected) {
                        resp.records = resp.records.filter(x => {
                            if (this.showAuthType === 'page') return x.type === 1;
                            return true;
                        });
                    }
                    resp.records = resp.records.sort((a, b) => (a.rank || 0) - (b.rank || 0));
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
            },
            clickAddChild(row) {
                this.resetEditForm();
                this.editForm.pid = row.id;
                this.editForm.path = row.path;
                this.showEditWindow = true;
            }
        }
    };
</script>

<style scoped lang="less">
    .auth {

    }
</style>