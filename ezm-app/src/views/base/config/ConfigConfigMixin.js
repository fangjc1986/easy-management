import {
    commonValidate
} from "@/utils/validateUtil";

export default {
    data() {
        return {
            editSpan: {
                span: 12
            },

            fields: [
                {
                    // COMMENT： 编码（作为查询key）
                    prop: 'code',
                    label: '编码(KEY)',
                    editRender: true,
                    rules: [commonValidate.required,],

                    filterRender: true,
                    filters:[{data:''}],
                    editDisabled: true,
                    editLabel:true,

                    params: {
                        filterSuffix: '_like',
                    }
                },

                {
                    // COMMENT： 参数值
                    prop: 'value',
                    label: '参数值',
                    editRender: {
                        name: 'ElInput',
                        type: 'visible',
                    },
                    rules: [commonValidate.required,],

                    tableEdit: true,
                },

                {
                    // COMMENT： 类型：1：文本框输入，2：波尔量开关
                    prop: 'type',
                    label: '类型',
                    editRender: {
                        name: 'ElSelect',
                        options: true,
                    },
                    rules: [commonValidate.required,],

                    show: false,
                    params:{
                        options:'SYS_AUTH',
                    }
                },

                {
                    // COMMENT： 备注说明
                    prop: 'remark',
                    label: '说明',
                    editRender: {
                        name: 'ElInput',
                        props: {
                            type: 'textarea'
                        },
                        attrs: {
                            rows: 5
                        }
                    },

                    width: 400,
                    editSpan: {
                        span: 24,
                    }
                },

            ]
        }
    }
}