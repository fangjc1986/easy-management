import Enum from "@/utils/Enum";
import {isArray, isString, isObject, isFunction} from "xe-utils/dist/xe-utils";
import {getDictToOptionsByCode} from "@/api/base/dict_detail";
import {vxeTableAddRender} from "@/utils/vue.render.util";
import {getFilterFieldName} from "@/utils/element.util";
import {debThr} from "@/utils/common.util";

export default {
    props: {},
    data() {
        return {
            dictOptions: {},
            needSetOptionsRender: new Set(),
            needSetTableOptionsRender: new Set(),
            rules: null,
        }
    },
    watch: {},
    computed: {},
    created() {
        if (!this.fields) return;
        this.refreshRenders();
        this.refreshDictOptions();
        this.rules = this.getRules();
    },
    mounted() {
        // if (!this.fields) return;
        // this.refreshRenders();
        // this.refreshDictOptions();
    },
    methods: {
        getRules() {
            return this.fields.reduce((t, field) => {
                if (!field.rules || !field.rules.length) return t;
                t[field.prop] = field.rules;
                return t;
            }, {})
        },
        refreshRenders() {
            if (!this.fields) return;
            this.resetEditRenders();
            this.resetFilterRenders();
            this.resetCellRenders();
            this.resetContentRender();
        },
        refreshDictOptionsV1() {
            [...new Set([...this.needSetOptionsRender].map(x => x.dictCode))].forEach((code) => {
                getDictToOptionsByCode(code).then(resp => {
                    [...this.needSetOptionsRender].forEach(render => {
                        if (render.dictCode === code) {
                            render.options = resp;
                        }
                    })
                })
            })
        },
        refreshDictOptions() {
            this.fields.forEach(field => {
                if (!field.params || !field.params.options) return;
                let defaultFilterOptions = field.params.defaultFilterOptions || [];
                let code = field.params.options;
                if (isString(code)) {
                    getDictToOptionsByCode(code, false).then(resp => {
                        field.params.options = resp;
                        [...this.needSetOptionsRender].forEach(render => {
                            let ls = resp.map(x => Object.assign({}, x));
                            ls.forEach(x => {
                                if (render.isLabelValue) {
                                    let v = x.value;
                                    x.value = x.label;
                                    x.label = v + ' - ' + x.label;
                                } else {
                                    x.label = x.value + ' - ' + x.label;
                                }
                            });
                            if (render.fieldProp === field.prop) {
                                render.options = render.isFilter ? defaultFilterOptions.concat(ls) : ls;
                            }
                        });
                    });
                } else if (isArray(code)) {
                    [...this.needSetOptionsRender].forEach(render => {
                        if (render.fieldProp === field.prop) {
                            render.options = render.isFilter ? defaultFilterOptions.concat(code) : code;
                        }
                    });
                } else if (isFunction(code)) {
                    code().then(resp => {
                        [...this.needSetOptionsRender].forEach(render => {
                            if (render.fieldProp === field.prop) {
                                render.options = render.isFilter ? defaultFilterOptions.concat(resp) : resp;
                            }
                        });
                        field.params.options = resp;
                    })
                }
            });
        },

        setRenderToVxeTable(render) {
            if (isObject(render.name)) {
                vxeTableAddRender(render.name);
                render.name = render.name.name;
            }
        },
        resetEditRenders() {
            let prKeys = Object.keys(this.parentFieldRelation || {});
            this.fields.forEach(field => {
                if (!field.editGroup) field.editGroup = this.defaultEditGroupName || '@default';
                if (!field.editRender) return;
                this.setRenderToVxeTable(field.editRender);
                if (prKeys.includes(field.prop)) {
                    field.addDisabled = field.editDisabled = true;
                }
                if (field.editRender === true) {
                    field.editRender = new Enum.DefaultRender;
                } else if (field.editRender.options === true) {
                    field.editRender.dictCode = field.editRender.options;
                    field.editRender.fieldProp = field.prop;
                    field.editRender.options = [];
                    field.editRender.isEdit = true;
                    this.pushToNeedRenderOptions(field.editRender);
                }
                if (field.tableEdit && !isObject(field.tableEditRender)) {
                    field.tableEditRender = field.editRender;
                }
            });
        },
        resetFilterRenders() {
            this.fields.forEach(field => {
                field.filterFieldName = getFilterFieldName(field);
                if (!field.filterRender) return;
                this.setRenderToVxeTable(field.filterRender);
                if (field.filterRender === true) {
                    return field.filterRender = field.editRender || new Enum.DefaultRender;
                }
                if (field.filterRender.options === true) {
                    field.filterRender.dictCode = field.filterRender.options;
                    field.filterRender.fieldProp = field.prop;
                    field.filterRender.isFilter = true;
                    field.filterRender.options = [];
                    this.pushToNeedRenderOptions(field.filterRender);
                }
            })
        },
        resetCellRenders() {
            this.fields.forEach(field => {
                if (!field.cellRender) return;
                this.setRenderToVxeTable(field.cellRender);
                if (field.cellRender.options === true) {
                    field.cellRender.dictCode = field.cellRender.options;
                    field.cellRender.fieldProp = field.prop;
                    field.cellRender.isCell = true;
                    field.cellRender.options = [];
                    this.pushToNeedRenderOptions(field.cellRender);
                }
            })
        },
        resetContentRender() {
            this.fields.forEach(field => {
                if (!field.contentRender) return;
                this.setRenderToVxeTable(field.contentRender);
                if (field.contentRender.options === true) {
                    field.contentRender.dictCode = field.contentRender.options;
                    field.contentRender.fieldProp = field.prop;
                    field.contentRender.isCell = true;
                    field.contentRender.options = [];
                    this.pushToNeedRenderOptions(field.cellRender);
                }
            })
        },
        pushToNeedRenderOptions(render) {
            this.needSetOptionsRender.add(render);
        },
        pushToNeedSetTableOptionsRender(render) {
            this.needSetTableOptionsRender.add(render);
        }
    }
}