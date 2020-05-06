import {isString} from "xe-utils/dist/xe-utils";
import {arraySame, debThr, treeFilter} from "@/utils/common.util";

export default {
    props: {
        /**
         * 默认选中参数
         *{
         *     id: [11,22,33,44,55],
         *     username: [22,33,44,55],
         *}
         */
        defaultSelectedRows: {
            default() {
                return {};
            }
        },
        selectType: {
            default: 'checkbox'
        },
        showSelected: {
            default: true,
        },
        showSeq: {
            default: true,
        },
        dblEditDisabled: {
            default: true,
        },
        isSelector: {
            default: false,
        },
    },
    data() {
        return {
            selectedRows: [],
            lastSelectedRows: null,
        };
    },
    watch: {
        defaultSelectedRows(v) {
            setTimeout(() => this.setSelectedRows());
        },
        "pageInstance.paginate.records"(v, o) {
            setTimeout(() => this.setSelectedRows());
        },
        selectedRows(v) {
            // this.$emit("on-selected-rows-change", v);
        }
    },
    mounted() {
    },
    computed: {
        selectTypeComp() {
            if (this.pageInstance.isSelector) {
                return this.pageInstance.selectType || 'checkbox';
            }
            return this.selectType;
        },

    },
    methods: {
        checkboxChange({records}) {
            this.selectedRows = records;
            this.$emit("on-selected-rows-change", this.selectedRows);
        },
        radioChange({row}) {
            this.selectedRows = [row];
            this.$emit("on-selected-rows-change", this.selectedRows);
        },
        clearCheckBoxRow() {
            setTimeout(() => {
                this.$refs.table.clearRadioRow();
                this.$refs.table.clearCheckboxRow();
                this.selectedRows = [];
                this.$emit("on-selected-rows-change", this.selectedRows);
            });
        },
        setSelectedRows() {
            if (!this.$refs.table) return;
            if (this.pageInstance.paginate.size < 999) return;
            let keys = Object.keys(this.defaultSelectedRows);
            let rows = treeFilter(this.pageInstance.paginate.records, row => {
                for (let i = 0; i < keys.length; i++) {
                    let key = keys[i];
                    if (this.defaultSelectedRows[key].includes(row[key])) return true;
                }
                let selectorInstance = this.pageInstance.selectorInstance;
                if (selectorInstance && selectorInstance.value) {
                    let v = selectorInstance.value;
                    if (isString(v)) {
                        v = v.split(',');
                    }
                    if (v.includes(row[selectorInstance.fieldValue])) return true;
                }
                return false;
            });
            if (!rows.length) return;
            if (this.lastSelectedRows !== null && arraySame(this.lastSelectedRows, rows)) return;
            this.lastSelectedRows = rows;
            this.setAllCheckBoxRow(false);
            if (this.selectTypeComp === 'checkbox') {
                this.$refs.table.setCheckboxRow(rows, true);
                this.selectedRows = rows;
            } else {
                this.$refs.table.setRadioRow(rows[0]);
                this.selectedRows = [rows[0]];
            }
        },
        setAllCheckBoxRow(checked) {
            if (!this.$refs.table) return;
            this.$refs.table.setAllCheckboxRow(checked);
        }
        ,
        onDblClick({row}) {
            this.$emit('on-dblclick', row);
            if (this.dblEditDisabled) return;
            this.pageInstance.clickEdit(row);
        }
        ,

        clickSelectorConfirm() {
            this.$emit("on-click-selector-confirm", this.selectedRows);
            if (this.pageInstance.selectorInstance) this.pageInstance.selectorInstance.selectorConfirm(this.selectedRows);
            this.pageInstance.selectorConfirm(this.selectedRows);
        }
        ,
        clickSelectorCancel() {
            this.$emit("on-click-selector-confirm", []);
            if (this.pageInstance.selectorInstance) this.pageInstance.selectorInstance.selectorConfirm([]);
            this.pageInstance.selectorConfirm([]);
        }


    }
}