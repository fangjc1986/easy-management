import {
    commonValidate
} from "@/utils/validateUtil";

export default {
    data() {
        return {
            editSpan: {},

            fields: [
                {
                    // COMMENT： 编号
                    prop: 'code',
                    label: '编号',
                    editLabel: true,
                    editRender: true,
                    rules: [commonValidate.required,],
                    editDisabled: true,

                    filters:[{data:''}],
                    filterRender: true,
                    params:{
                        filterSuffix: '_like',
                    },
                    width: 220,
                },

                {
                    // COMMENT： 名称
                    prop: 'name',
                    label: '名称',
                    editRender: true,
                    width: 220,
                    rules: [commonValidate.required,],

                    filters:[{data:''}],
                    filterRender: true,
                    params:{
                        filterSuffix: '_like',
                    },
                    tableEdit: true,
                },

                {
                    // COMMENT： 说明
                    prop: 'remark',
                    label: '说明',
                    width: 360,
                    editRender: {
                        name: 'EzmTextInput',
                    },
                    tableEdit: true,
                },

            ]
        }
    }
}