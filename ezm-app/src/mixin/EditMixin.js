import {formValidate} from "@/utils/element.util";
import {isEmpty} from "xe-utils/dist/xe-utils";
import tabs from "@/store/modules/tabs";
import {TypeUtil} from "@/utils/common.util";

export default {
    props: {
        pageInstance: Object,
        saveClose: {
            default: true
        },
        showAddButtonAlways: {
            default: false,
        },
        showCloseButton: {
            default: false,
        },
        showBackButton: {
            default: false,
        },
        showClearButton: {
            default: true,
        },
        showSaveButton:{
            default: true,
        }
    },
    data() {
        return {
            span: {
                xs: 24,
                sm: 12,
                md: 8,
                lg: 6,
                xl: 4,
            },
            addButtonText: '新增',
            clearButtonText: '清空',
            saveButtonText: '保存',
            backButtonText: '返回列表',
        }
    },
    watch: {
        'pageInstance.editForm'() {
            setTimeout(() => {
                this.$refs.form.clearValidate();
            });
        }
    },
    created() {
        this.span = isEmpty(this.pageInstance.editSpan) ? this.span : this.pageInstance.editSpan;
    },
    computed: {
        titleComp() {
            let field = this.editPropComp || {prop: 'id', label: 'id'};
            let p = this.pageInstance;
            if (p.editForm.id) {
                return `编辑 [ ${p.pageName} ] [ ${field.label} : ${p.editForm[field.prop]} ]`;
            }
            return `新增 [ ${p.pageName} ]`;
        },
        fieldsComp() {
            return this.pageInstance.fields.concat().sort((a, b) => {
                return (a.editRank || 0) - (b.editRank || 0);
            });
        },
        iconComp() {
            return this.pageInstance.editForm.id ? 'el-icon-edit' : 'el-icon-plus';
        },
        editPropComp() {
            return this.pageInstance.fields.find(f => f.editLabel === true);
        },
        showAddButton() {
            return this.showAddButtonAlways || !this.pageInstance.editForm.id;
        },
        showTabsComp() {
            return this.tabsComp.length > 1;
        },
        tabsComp() {
            return [...new Set(this.fieldsComp.map(x => x.editGroup))];
        }
    },
    methods: {
        getCustomEditSpan(field, size) {
            let es = field.editSpan || {};
            return es[size] || es['span'] || this.span[size] || this.span['span'] || 24;
        },
        getInputDisabled(field) {
            if (this.pageInstance.editForm.id) {
                return field.editDisabled;
            }
            return field.addDisabled;
        },
        clickAdd() {
            this.validate().then(() => {
                this.pageInstance.editForm.id = '';
                this.clickSave();
            });
        },
        clickSave() {
            this.validate().then(() => {
                this.pageInstance.saveRow(this.pageInstance.editForm, () => {
                    this.pageInstance.setShowEditWindow(!this.saveClose);
                });
            });
        },
        validate() {
            return formValidate(this.$refs.form);
        },
        onClickClose() {
            this.pageInstance.setShowEditWindow(false);
        },
        showField(field) {
            let addShow = !(field.addShow === false);
            let editShow = !(field.editShow === false);
            if (TypeUtil.isFunction(field.addShow)) {
                addShow = field.addShow(this.pageInstance.editForm);
            }
            if (TypeUtil.isFunction(field.editShow)) {
                editShow = field.editShow(this.pageInstance.editForm);
            }
            if (this.pageInstance.editForm.id) {
                return editShow;
            } else return addShow;
        }
    },
}