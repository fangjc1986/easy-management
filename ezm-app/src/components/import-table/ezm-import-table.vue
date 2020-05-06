<template>
    <div class="ezm-import-table"
         :class="height? '' : 'flex-grow flex-box flex-column'"
    >
        <vxe-toolbar
            class="flex-shk-off mb-xs"
            style="min-height: auto;height:28px"
            :export="{icon: 'el-icon-download'}"
            :custom="{icon:'el-icon-s-operation', checkMethod:checkMethod}"
            :import="showImport ? {icon: 'el-icon-document-add'} : false"
            ref="toolbar"
            v-show="showToolBar"
        >
            <template slot="tools">
                <el-button icon="el-icon-sort" title="重置排序筛选" circle
                           @click="clickResetSort"
                           :loading="loading"
                />
            </template>
            <template slot="buttons">
                <slot name="buttons-left"></slot>
                <el-button-group v-if="!disableOperateButton"
                                 class="mr-xs"
                >
                    <el-button
                        icon="el-icon-plus"
                        :loading="loading"
                        v-if="!disableAddButton"
                        @click="clickAddButton"
                    >新增
                    </el-button>
                    <el-button
                        icon="el-icon-delete"
                        :loading="loading"
                        v-if="!disableDeleteButton"
                        :disabled="!selectedRows.length"
                        @click="clickDeleteButton"
                    >删除
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
                class=""
                ref="table"
                :loading="loading"
                :data="importInstance.list"
                :height="height || resize_tableHeight"
                highlight-hover-row
                highlight-current-row
                highlight-hover-column
                :sort-config="{remote:false, trigger: 'default'}"
                :show-overflow="showOverflow"
                :checkbox-config="{highlight: true, range: true}"
                :radio-config="{highlight:true}"
                :edit-rules="importInstance.rules"
                :edit-config="{mode:'row',trigger:'click', showStatus:true}"
                :filter-config="{remote:false}"
                :optimization="{cloak:true, animat: false}"
                :column-width="defaultColumnWidth || '140px'"
                :keep-source="true"

                @checkbox-change="checkboxChange"
                @checkbox-all="checkboxChange"
            >

                <vxe-table-column
                    type="checkbox" width="40" fixed="left"
                    align="center"
                    :show-overflow="false"
                />

                <vxe-table-column
                    type="seq" title="#" width="50" v-if="showSeq " fixed="left"
                />

                <vxe-table-column
                    title="" v-if="showRowOperation " fixed="left"
                    :width="rowOperationWidth"
                >
                    <template v-slot:header="{}">
                        <el-icon class="el-icon-more" style="font-size: 16px;"></el-icon>
                    </template>
                    <template v-slot="{row}">
                        <div class="flex-box flex-center overflow-h table-row-operation">
                            <slot name="row_operation_left" :row="row"></slot>
                            <slot name="row_operation_right" :row="row"></slot>
                        </div>
                    </template>
                </vxe-table-column>

                <slot/>

            </vxe-table>

        </div>
        <slot name="bottom"/>
    </div>
</template>

<script>
    import TableAutoResizeMixin from "@/components/table/table-mixin/TableAutoResizeMixin";
    import ReloadMixin from "@/mixin/ReloadMixin";
    import {DialogUtil} from "@/utils/common.util";

    export default {
        name: 'ezm-import-table',
        mixins: [ReloadMixin, TableAutoResizeMixin],
        components: {},
        props: {
            importInstance: Object,
            loading: {
                default: false,
            },
            // 溢出隐藏
            showOverflow: {
                default: true,
            },
            treeConfig: {
                default: false,
            },
            showRowOperation: {
                default: false,
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
            rules: {
                default: () => [],
            },
            value: {
                default: () => [],
            },
            showSeq: {
                default: true,
            },
            disableDeleteButton: {
                default: false,
            },
            disableAddButton: {
                default: false,
            },
            disableOperateButton: {
                default: false,
            },
            showImport: {
                default: false,
            }

        },
        data() {
            return {
                list: this.value,
                selectedRows: [],
            }
        },
        computed: {},
        watch: {
            list(v) {
            }
        },
        created() {
            this.importInstance.importTable = this;
        },
        mounted() {
            this.$refs.toolbar.$el.querySelectorAll(".vxe-tools--operate-btn").forEach(btn => {
                btn.className = "vxe-tool-btn el-button el-button--default el-button--mini is-circle";
            });
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
            checkboxChange({records}) {
                this.selectedRows = records;
                this.$emit('on-selected-row-change', this.selectedRows);
            },
            radioChange({row}) {
                this.selectedRows = [row];
                this.$emit('on-selected-row-change', this.selectedRows);
            },

            checkMethod({column}) {
                return !['checkbox'].includes(column.type);
            },
            async clickAddButton() {
                let records = this.importInstance.getEditForm();
                let {row: newRow} = await this.$refs.table.insertAt(records, null);
                await this.$refs.table.setActiveRow(newRow);
            },
            clickDeleteButton() {
                this.$refs.table.removeCheckboxRow();
            },
            clickResetSort() {
                this.$refs.table.clearFilter();
                this.$refs.table.clearSort();
            },
            getTableData() {
                return this.$refs.table.getTableData().fullData;
            },
            insetRecords(records) {
                this.$refs.table.insert(records);
            },
            validate() {
                return this.$refs.table.validate().then((res, c) => {
                    return Promise.resolve();
                }).catch(() => {
                    DialogUtil.toast("请检查表单填写是否正确！", "error");
                    return Promise.reject();
                });
            }

        }
    };
</script>

<style scoped lang="less">
    .ezm-import-table {
    }
</style>