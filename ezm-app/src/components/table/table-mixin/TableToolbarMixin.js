import {DialogUtil} from "@/utils/common.util";
import {isEmpty} from "xe-utils/dist/xe-utils";

export default {
    props: {
        disableOperateButton: {
            default: false,
        },
        disableAddButton: {
            default: false,
        },
        disableEditButton: {
            default: false,
        },
        disableDeleteButton: {
            default: false,
        },
        disableUpdateButton: {
            default: false,
        },
    },
    data() {
        return {
            loading: false,
            loadingTimer: null,
        }
    },
    watch: {
        'pageInstance.loading'(v) {
            clearTimeout(this.loadingTimer);
            if (!v) return this.loading = v;
            this.loadingTimer = setTimeout(() => {
                this.loading = v;
            }, 400);
        }
    },
    mounted() {
        this.$refs.toolbar.$el.querySelectorAll(".vxe-tools--operate-btn").forEach(btn => {
            btn.className = "vxe-tool-btn el-button el-button--default el-button--mini is-circle";
        });
    },
    methods: {
        clickAddButton() {
            this.pageInstance.clickAdd();
            this.$emit('on-click-add');
        },
        clickEditButton() {
            this.pageInstance.clickEdit(this.selectedRows[0]);
            this.$emit('on-click-edit', this.selectedRows[0]);
        },
        clickDeleteButton() {
            this.pageInstance.removeRows(this.selectedRows);
            this.$emit('on-click-delete', this.selectedRows);
        },
        filterChange({filters}) {
            let nowScroll = Object.assign({}, this.lastScrollPos);
            let p = this.pageInstance;
            let filterForm = p.filterForm;
            filters.forEach(c => {
                let v = c.datas.length === 1 ? c.datas[0] : c.datas;
                if (isEmpty(v)) v = '';
                let params = c.column.params || {};
                let s = params.filterField || c.field;
                ;
                if (!this.pageInstance.fields.find(x => x.prop === c.field)) {
                    s += params.filterSuffix || "";
                }
                if (p.defaultFilterLocked && Object.keys(p.defaultFilterForm || {}).includes(s) && p.defaultFilterForm[s]) {
                    return;
                }
                filterForm[s] = v;
            });
            p.refreshList(nowScroll);
        },

        sortChange(column) {
            let nowScroll = Object.assign({}, this.lastScrollPos);
            nowScroll.scrollTop = 0;
            this.pageInstance.paginate.setSort(column);
            this.pageInstance.refreshList(nowScroll);
        },
        clickResetSort() {
            this.$refs.table.clearSort();
            this.$refs.table.clearFilter();
            this.pageInstance.resetFilterForm();
            this.pageInstance.reloadList();
        },
        checkMethod({column}) {
            return !['checkbox'].includes(column.type);
        },
        clickUpdateChange() {
            let rows = this.$refs.table.getUpdateRecords();
            if (!rows.length) return DialogUtil.toast("没有找到变更内容!", 'warning');
            this.$refs.table.validate().then((res, c) => {
                this.pageInstance.updateRows(rows);
            }).catch(() => {
                DialogUtil.toast("请检查表单填写是否正确！", "error");
            })
        }
    }
}