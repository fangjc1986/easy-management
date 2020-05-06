import {
    commonValidate
} from "@/utils/validateUtil";

export default {
    data() {
        return {
            editSpan: {
                span: 24,
            },

            fields: [
                {
                    // COMMENT： 字典id
                    prop: 'dictId',
                    label: '字典id',
                    show: false,
                    rules: [commonValidate.required,],

                    editRank: 1,
                },

                {
                    // COMMENT： 分类编号
                    prop: 'dictCode',
                    label: '分类编号',
                    editRender: true,
                    rules: [commonValidate.required,],
                    show: false,

                    editRank: 2

                },

                {
                    // COMMENT： 编号
                    prop: 'code',
                    label: '编号',
                    editRender: true,
                    rules: [commonValidate.required,],
                    editRank: 3,

                    editSpan: {
                        span: 8,
                    },
                    filters: [{data:''}],
                    filterRender: true,
                    params:{
                        filterSuffix: '_like',
                    },
                    editDisabled: true,
                },

                {
                    // COMMENT： 名称
                    prop: 'name',
                    label: '名称',
                    tableEdit: true,
                    editRender: true,
                    rules: [commonValidate.required,],
                    editRank: 4,

                    width: 200,
                    editSpan: {
                        span: 12,
                    },
                    filters: [{data:''}],
                    filterRender: true,
                    params:{
                        filterSuffix: '_like',
                    },
                },

                {
                    // COMMENT： 颜色
                    prop: 'color',
                    label: '颜色',
                    editRender: {
                        type: 'visible',
                        name: 'EzmColorPicker'
                    },
                    filterRank: 5,
                    editRank: 5,
                    editSpan: {
                        span: 4,
                    },

                    width: 80,
                    tableEdit: true,
                },

                {
                    // COMMENT： 排序
                    prop: 'rank',
                    label: '排序',
                    editRender: true,
                    filterRank: 5,
                    editRank: 5,
                    editSpan: {
                        span: 4,
                    },
                    rules:[
                        commonValidate.required,
                        commonValidate.integer
                    ],

                    width: 80,
                    tableEdit: true,
                    editValue:1,
                },

                {
                    // COMMENT： 说明
                    prop: 'remark',
                    label: '说明',
                    editRender: {
                        name: 'ElInput',
                        props: {
                            type: 'textarea',

                        },
                        attrs: {
                            rows: 5,
                        }
                    },
                    editRank: 6,

                    width: 360,
                },
            ]
        }
    }
}