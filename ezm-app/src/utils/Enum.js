import Vue from 'vue';
import {warehouseGetList} from "@/api/wms/warehouse";
import {reservoirGetList} from "@/api/wms/reservoir";
import {shelfGetList} from "@/api/wms/shelf";

export default {
    vue: null,
    Vue: Vue,

    fieldsName: {
        'province': '省',
        'city': '市',
        'county': '区/县',
    },
    DefaultRender: class {
        name = 'ElInput';
    },

    renders: {
        dateRender(type = null) {
            return {
                name: "ElDatePicker",
                props: {
                    type: type || 'date',
                    format: 'yyyy-MM-dd',
                    'value-format': 'yyyy-MM-dd HH:mm:ss',
                },
            }
        },
        dateTimeRender(type = null) {
            return {
                name: "ElDatePicker",
                props: {
                    type: type || 'datetime',
                    format: 'yyyy-MM-dd HH:mm:ss',
                    'value-format': 'yyyy-MM-dd HH:mm:ss',
                },
            }
        },
        elInputPlaceHolder(placeholder = null) {
            if (placeholder === null) {
                return {
                    name: "ElInput",
                }
            }
            return {
                name: "ElInput",
                attrs: {
                    placeholder,
                }
            }
        },
        elInputMultiple(options = true, collapseTag = true, opt = {}) {
            return {
                name: "ElSelect",
                options: options,
                props: {
                    multiple: true,
                    'collapse-tags': collapseTag,
                },
                ... opt,
            }
        },
        textareaInput(rows = 5) {
            return {
                name: "ElInput",
                attrs: {
                    type: 'textarea',
                    rows: rows
                }
            };
        },
        ezmTextInput() {
            return {
                name: "EzmTextInput",
            };
        },
        whsResShfSelector(level = 2, {
            inputFormatter = (v) => v, outputFormatter = (v) => v, multiple = false,
            onChange = null, emitPath = true, showDefault = false, checkStrictly = false,
        } = {}) {
            let listMethods = [warehouseGetList, reservoirGetList, shelfGetList].slice(0, level);
            let fieldValue = ["code", "code", "code"].slice(0, level);
            let fieldShow = ["name", "name", "name"].slice(0, level);
            let fieldPid = ["whsCode", "resCode"].slice(0, level - 1);
            return {
                name: "EzmTreeSelector",
                props: {
                    focusMultiPromise: true,
                    listMethods,
                    fieldValue,
                    fieldShow,
                    fieldPid,
                    multiple,
                    emitPath,
                    showDefault,
                    checkStrictly,
                },
                inputFormatter: (v) => inputFormatter && inputFormatter(v),
                outputFormatter: (v) => outputFormatter && outputFormatter(v),
                on: {
                    'on-change': (v) => onChange && onChange(v),
                }
            }
        },
    },
    hasAuth(path = "") {
        return !!this.vue.$store.state.route.routeAuthList.find(x => x.path === path);
    }
}


export const DefaultOptions = {
    defaultFilterOptions: (v = "") => [
        {label: '全部', value: v},
    ],
};

export const recommendedColor = [
    '#2d8cf0',
    '#19be6b',
    '#ff9900',
    '#ed4014',
    '#00b5ff',
    '#19c919',
    '#f9e31c',
    '#ea1a1a',
    '#9b1dea',
    '#00c2b1',
    '#ac7a33',
    '#1d35ea',
    '#8bc34a',
    '#f16b62',
    '#ea4ca3',
    '#0d94aa',
    '#febd79',
    '#5d4037',
    '#00bcd4',
    '#f06292',
    '#cddc39',
    '#607d8b',
    '#000000',
    '#ffffff',
];