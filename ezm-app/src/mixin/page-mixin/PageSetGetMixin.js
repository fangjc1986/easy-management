import {isArray, isFunction} from "xe-utils/dist/xe-utils";
import {DialogUtil, TypeUtil} from "@/utils/common.util";

export default {
    props: {
        // 默认筛选表单
        defaultFilterForm: {
            default: null
        },
        // 默认锁定筛选
        defaultFilterLocked: {
            default: false,
        },
        // 显示筛选窗口
        showFilterWindow: {
            default: true,
        },
        // 筛选窗口是否展开
        expandFilterWindow: {
            default: true,
        },
        // 父页实例
        parentPageInstance: {
            default: null
        },
        // 与父模型等价关系字段 如: { user_id : id } => 父模型中的id 对应 当前模型中的user_id
        parentFieldRelation: {
            default: null,
        },

    },
    data() {
        return {
            loading: false,
            ezmTable: null,
            filterWindow: this.expandFilterWindow,
            filterForm: {},
            editForm: {},
            childPageInstance: null,

            pageInstance: this,
            loadingToast: null
        }
    },
    computed: {
        defaultFilterFormComp() {
            if (TypeUtil.isFunction(this.defaultFilterForm)) {
                return this.defaultFilterForm();
            }
            return this.defaultFilterForm;
        }
    },
    watch: {
        'paginate.records'(v) {
            this.resetTableFilter();
        },
    }
    ,
    methods: {
        loadingStart(alertText) {
            if (alertText) this.loadingToast = DialogUtil.toast(alertText, "loading");
            this.loading = true;
        },
        loadingFinish() {
            if (this.loadingToast) this.loadingToast.close();
            this.loading = false;
        },
        setShowEditWindow(v) {
            this.showEditWindow = v;
        },
        setEzmTable(ezmTable) {
            this.ezmTable = ezmTable;
        },
        setChildPageInstance(instance) {
            this.childPageInstance = instance;
        },
        resetDefaultParams() {
            this.filterWindow = this.expandFilterWindow;
            this.resetEditForm();
            this.resetFilterForm();
            this.ezmTable.$refs.table.clearSort();
            this.ezmTable.$refs.table.clearFilter();
            if (this.parentPageInstance) {
                this.parentPageInstance.setChildPageInstance(this);
            }
        },
        resetFilterForm() {
            this.filterForm = Object.assign(this.getInitFilterForm(), this.defaultFilterFormComp || {});
            let query = this.r_is_page ? Object.assign({}, this.$route.query) : false;
            if (this.autoLoadUrlParams) this.filterForm = Object.assign(query, this.filterForm);
            this.resetTableFilter();
        },

        resetTableFilter2() {
            setTimeout(() => {
                Object.keys(this.filterForm || {}).forEach(f => {
                    let fieldName = f.split("_")[0];
                    let column = this.ezmTable.$refs.table.getColumnByField(fieldName);
                    if (!column || !column.filters || !column.filters.length) return;
                    let filter = column.filters[0];
                    filter.checked = !!this.filterForm[f];
                    filter.data = this.filterForm[f];
                });
            });
        },

        resetTableFilter() {
            setTimeout(() => {
                let columns = this.ezmTable.$refs.table.getColumns();
                Object.keys(this.filterForm).forEach(key => {
                    let column = columns.find(c => {
                        let params = c.params || {};
                        let realKey = key.replace(new RegExp(`${params.filterSuffix}$`, 'ig'), "");
                        return realKey === (params.filterField || c.property);
                    });
                    if (!column || !column.filters || !column.filters.length) return;
                    column.filters[0].data = this.filterForm[key];
                    column.filters[0].checked = !!this.filterForm[key];
                });
            });
        },

        resetEditForm() {
            this.editForm = this.getEditForm();
            if (this.parentPageInstance) {
                Object.keys(this.parentFieldRelation || {}).forEach(k => {
                    this.editForm[k] = this.parentPageInstance.editForm[this.parentFieldRelation[k]];
                });
            }
        },
        getInitFilterForm() {
            let pr = this.parentFieldRelation || {};
            let lockedField = Object.keys(pr);
            return this.fields.reduce((t, field) => {
                let locked = lockedField.includes(field.prop);
                if (!field.filterRender && !locked) return t;
                let fieldName = this.getFilterFieldName(field.prop, false, false);
                field.filterValue = field.filterValue || "";
                let value = (locked ? this.parentPageInstance.editForm[pr[field.prop]] : field.filterValue) || '';
                if (value) t[fieldName] = value;
                return t;
            }, {});
        },


        getEditForm() {
            return this.fields.reduce((t, field) => {
                field.editValue = field.editValue !== 0 ? (field.editValue || "") : 0;
                t[field.prop] = isFunction(field.editValue) ? field.editValue() : field.editValue;
                return t;
            }, {});
        },

        getFilterForm(prefix = "") {
            return Object.keys(this.filterForm).reduce((t, key) => {
                let kk = key;
                let field = this.fields.find(x => {
                    return x.prop === key || (x.params || {}).filterField === key;
                });
                let prefix = this.defaultFilterPrefix || '';
                let suffix = '';
                if (field) {
                    prefix = (field.params || {}).filterPrefix || prefix;
                    suffix = (field.params || {}).filterSuffix || suffix;
                }
                if (key.split('.').length > 1) prefix = '';
                if (new RegExp(`${suffix}$`).test(key)) suffix = '';
                let ffn = prefix + key + suffix;
                t[ffn] = isArray(this.filterForm[key]) ? this.filterForm[key].join(',') : this.filterForm[key];
                return t;
            }, {});
        },


        getFilterFieldName(prop, needSuffix = true, needPrefix = true) {
            let field = this.fields.find(x => x.prop === prop);
            if (!field) return prop;
            let params = field.params || {};
            prop = params.filterField || prop;
            let prefix = (prop.indexOf(".") < 0) ? (params.filterPrefix || field.defaultFilterPrefix || '') : "";
            let fieldName = params.filterField || prop;
            let suffix = params.filterSuffix || '';
            if (suffix && new RegExp(`${suffix}$`).test(prop)) suffix = '';
            return (needPrefix ? prefix : '') + fieldName + (needSuffix ? suffix : '');
        }

    }
}