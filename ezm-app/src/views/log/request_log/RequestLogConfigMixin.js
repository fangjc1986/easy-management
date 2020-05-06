import {
    commonValidate
} from "@/utils/validateUtil";

export default {
    data() {
        return {
            editSpan: {},

            fields: [
                {
                    // COMMENT： 用户名
                    prop: 'userName',
                    label: '用户名',
                    editRender: true,
                    filterRank: 1,
                    editRank: 1,
                    filterRender: true,
                    filters:[{data:''}],
                    editSpan: {span: 8},
                },

                {
                    // COMMENT： 访问API
                    prop: 'api',
                    label: '访问API',
                    editRender: true,
                    rules: [commonValidate.required, ],
                    filterRank: 3,
                    editRank: 3,
                    filterRender: true,
                    filters:[{data:''}],
                    editSpan: {span: 16},
                },
                {
                    // COMMENT： 参数JSON
                    prop: 'params',
                    label: '请求参数',
                    editRender: {
                        name: require("@/components/display/ezm-code-formatter").default,
                    },
                    rules: [commonValidate.required, ],
                    filterRank: 4,
                    editRank: 4,
                    formatter:({cellValue})=> {
                        if( !cellValue ) return '';
                        return cellValue.slice(0,100);
                    },
                    width: 400,
                    editSpan: {span: 12},
                },

                {
                    // COMMENT： 返回参数
                    prop: 'response',
                    label: '返回参数',
                    editRender: {
                        name: require("@/components/display/ezm-code-formatter").default,
                    },
                    rules: [commonValidate.required, ],
                    filterRank: 5,
                    editRank: 5,
                    formatter:({cellValue})=> {
                        if( !cellValue ) return '';
                        return cellValue.slice(0,100)
                    },
                    width: 400,
                    editSpan: {span: 12},

                },

            ]
        }
    }
}