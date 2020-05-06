<template>
    <div class="dict ezm-page-container">

        <ezm-table
            :page-instance="this"
            :height="null"
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
            <dict-edit
                :page-instance="this"
                :save-close="false"
                :show-back-button="true"
            />
            <dict_detail
                class="mt-sm"
                v-if="editForm.id"
                :parent-page-instance="this"
                :parent-field-relation="{'dictId' : 'id' , 'dictCode' : 'code'}"
                :height="null"
            />
        </ezm-edit-tab>

    </div>
</template>

<script>
    import EzmTable from "@/components/table/ezm-table";
    import PageMixin from "@/mixin/page-mixin/PageMixin";
    import {dictGetList, dictRemove, dictSave, dictUpdateList} from "@/api/base/dict";
    import DictFilter from "@/views/base/dict/dict-filter";
    import {DialogUtil} from "@/utils/common.util";
    import DictEdit from "@/views/base/dict/dict-edit";
    import DictConfigMixin from "@/views/base/dict/DictConfigMixin";
    import Dict_detail from "@/views/base/dict_detail/dict_detail";
    import EzmEditTab from "@/components/editor/container/ezm-edit-tab";

    export default {
        name: 'dict',
        mixins: [PageMixin, DictConfigMixin],
        components: {EzmEditTab, Dict_detail, DictEdit, DictFilter, EzmTable},
        props: {},
        data() {
            return {
                pageName: '页面名称',
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
                dictGetList({
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
                dictSave(row).then(resp => {
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
                dictUpdateList(rows).then(resp => {
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
                    dictRemove({
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
    .dict {

    }
</style>