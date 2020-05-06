<template>
    <div class="ezm-table"
         :class="height? '' : 'flex-grow flex-box flex-column'"
    >
        <vxe-toolbar
            class="flex-shk-off mb-xs"
            style="min-height: auto;height:28px"
            :export="{icon: 'el-icon-download'}"
            :custom="{icon:'el-icon-s-operation', checkMethod:checkMethod}"
            ref="toolbar"
            v-show="showToolBar"
        >
            <template slot="tools">
                <el-button icon="el-icon-refresh-left" title="取消变更" circle
                           @click="$refs.table.revertData()"
                           :loading="pageInstance.loading"
                           v-if="!disableUpdateButton"
                />
                <el-button icon="el-icon-refresh" title="刷新" circle
                           @click="pageInstance.getList()"
                           :loading="pageInstance.loading"
                />
                <el-button icon="el-icon-sort" title="重置排序筛选" circle
                           @click="clickResetSort"
                           :loading="pageInstance.loading"
                />
            </template>
            <template slot="buttons">
                <slot name="buttons-left"></slot>
                <el-button-group v-if="!disableOperateButton && !pageInstance.isSelector"
                                 class="mr-xs"
                >
                    <el-button
                        icon="el-icon-plus"
                        :loading="pageInstance.loading"
                        v-if="!disableAddButton"
                        @click="clickAddButton"
                    >新增
                    </el-button>
                    <el-button
                        icon="el-icon-edit"
                        :loading="pageInstance.loading"
                        v-if="!disableEditButton"
                        :disabled="selectedRows.length !== 1"
                        @click="clickEditButton"
                    >修改
                    </el-button>
                    <el-button
                        icon="el-icon-delete"
                        :loading="pageInstance.loading"
                        v-if="!disableDeleteButton"
                        :disabled="!selectedRows.length"
                        @click="clickDeleteButton"
                    >删除
                    </el-button>
                    <el-button
                        icon="el-icon-finished"
                        :loading="pageInstance.loading"
                        v-if="!disableUpdateButton"
                        @click="clickUpdateChange"
                    >{{ updateBatchButtonText }}
                    </el-button>
                </el-button-group>
                <el-button-group>
                    <el-button
                        icon="el-icon-circle-check"
                        type="primary"
                        v-if="pageInstance.isSelector"
                        @click="clickSelectorConfirm"
                    >确定选择
                    </el-button>
                    <el-button
                        icon="el-icon-circle-close"
                        v-if="pageInstance.isSelector"
                        @click="clickSelectorCancel"
                    >取消选择
                    </el-button>
                </el-button-group>
                <slot name="buttons-right"></slot>
            </template>
        </vxe-toolbar>
        <div class=" box-shadow" ref="tableContainer"
             :class=" height? 'flex-shk-off' : 'flex-grow overflow-h'"
        >
            <vxe-table
                border
                resizable
                row-id="id"
                class=""
                ref="table"
                :loading="loading"
                :data="pageInstance.paginate.records"
                :height="height || resize_tableHeight"
                highlight-hover-row
                highlight-current-row
                highlight-hover-column
                :sort-config="{remote:true, trigger: 'default'}"
                :show-overflow="showOverflow"
                :checkbox-config="{highlight: true, range: true}"
                :radio-config="{highlight:true}"
                :edit-rules="pageInstance.rules"
                :edit-config="pageInstance.isSelector ? null : {mode:'row',trigger:'click', showStatus:true}"
                :tree-config="treeConfig"
                :keep-source="true"
                :filter-config="{remote:true}"
                :optimization="{cloak:true, animat: false}"
                :current-change="currentChange"
                :column-width="defaultColumnWidth || '140px'"

                @checkbox-change="checkboxChange"
                @checkbox-all="checkboxChange"
                @radio-change="radioChange"
                @filter-change="filterChange"
                @sort-change="sortChange"
                @scroll="tableScroll"
                @cell-dblclick="onDblClick"
            >

                <vxe-table-column
                    type="checkbox" width="40" fixed="left"
                    v-if="selectTypeComp === 'checkbox' && showSelected"
                    align="center"
                    :show-overflow="false"
                />
                <vxe-table-column
                    type="radio" width="40" fixed="left"
                    v-if="selectTypeComp === 'radio' && showSelected"
                />
                <vxe-table-column
                    type="seq" title="#" width="50" v-if="showSeq " fixed="left"
                />

                <vxe-table-column
                    title="" v-if="showRowOperation && !pageInstance.isSelector" fixed="left"
                    :width="rowOperationWidth"
                >
                    <template v-slot:header="{}">
                        <el-icon class="el-icon-more" style="font-size: 16px;"></el-icon>
                    </template>
                    <template v-slot="{row}">
                        <div class="flex-box flex-center overflow-h table-row-operation">
                            <slot name="row_operation_left" :row="row"></slot>
                            <i class="iconfont icon-edit"
                               title="编辑"
                               v-if="showRowOperateAddIcon"
                               @click="pageInstance.clickEdit(row)"
                            ></i>
                            <slot name="row_operation_right" :row="row"></slot>
                        </div>
                    </template>
                </vxe-table-column>

                <slot/>

                <vxe-table-column
                    title="创建人" width="140" field="createUser" sortable
                />
                <vxe-table-column
                    title="创建时间" width="140" field="createTime"
                    :filters="[{data:[]}]"
                    :filter-render="$enum.renders.dateRender('daterange')"
                    :params="{filterSuffix:'_between_istime'}" sortable
                />
                <vxe-table-column
                    title="修改人" width="140" field="updateUser" sortable
                />
                <vxe-table-column
                    title="修改时间" width="140" field="updateTime"
                    :filters="[{data:[]}]"
                    :filter-render="$enum.renders.dateRender('daterange')"
                    :params="{filterSuffix:'_between_istime'}" sortable
                />
            </vxe-table>

        </div>
        <div class="flex-shk-off mt-xs">
            <vxe-pager
                v-if="pageInstance.paginate.size < 999"
                size="mini"
                :current-page="pageInstance.paginate.current"
                :page-size="pageInstance.paginate.size"
                :total="pageInstance.paginate.total"
                :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
                @page-change="(opt) => pageInstance.paginate.changePage(opt)"
            />
        </div>
        <slot name="bottom"/>
    </div>
</template>

<script>
    import TableAutoResizeMixin from "@/components/table/table-mixin/TableAutoResizeMixin";
    import ReloadMixin from "@/mixin/ReloadMixin";
    import TableToolbarMixin from "@/components/table/table-mixin/TableToolbarMixin";
    import EzmTableToolbarFilterList from "@/components/table/ezm-table-toolbar-filter-list";
    import TableCheckRadioMixin from "@/components/table/table-mixin/TableCheckRadioMixin";
    import TableEditMixin from "@/components/table/table-mixin/TableEditMixin";

    export default {
        name: 'ezm-table',
        mixins: [ReloadMixin, TableAutoResizeMixin, TableToolbarMixin, TableCheckRadioMixin, TableEditMixin],
        components: {EzmTableToolbarFilterList},
        props: {
            // 页面 vue 实例
            pageInstance: Object,
            // 溢出隐藏
            showOverflow: {
                default: true,
            },
            treeConfig: {
                default: false,
            },
            showRowOperation: {
                default: true,
            },
            rowOperationWidth: {
                default: 45
            },
            defaultColumnWidth: {
                default: '140px',
            },
            showToolBar: {
                default: true,
            },
            showRowOperateAddIcon: {
                default: true,
            },
            updateBatchButtonText: {
                default: "保存变更",
            }
        },
        data() {
            return {}
        },
        computed: {},
        watch: {},
        created() {
            this.pageInstance.setEzmTable(this);
        },
        mounted() {
        },
        destroyed() {
        },
        beforeDestroy() {

        },
        deactivated() {
        },
        methods: {
            reload() {
                setTimeout(() => {
                    this.$refs.table.clearScroll();
                });
            },

        }
    };
</script>

<style scoped lang="less">
    .table-container {
    }
</style>