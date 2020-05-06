import {
    commonValidate
} from "@/utils/validateUtil";

export default {
    data() {
        return {
            editSpan: {
                span: 12,
            },

            fields: [

                {
                    // COMMENT： 编号
                    prop: 'code',
                    label: '编号',
                    editRender: true,
                    rules: [commonValidate.required,],
                    filterRank: 0,
                    editRank: 0,
                    editLabel: true,

                    editDisabled: true,
                },


                {
                    // COMMENT： 组名
                    prop: 'name',
                    label: '组名',
                    editRender: true,
                    rules: [commonValidate.required,],
                    filterRank: 1,
                    editRank: 1,

                    tableEdit: true,
                    width: 200,
                },


                {
                    // COMMENT： 说明
                    prop: 'remark',
                    label: '说明',
                    editRender: {
                        name: "ElInput",
                        props: {
                            type: 'textarea',
                        },
                        attrs: {
                            rows: 5,
                        }
                    },
                    filterRank: 3,
                    editRank: 3,

                    width: 300,
                    editSpan: {span: 24},
                    tableEdit: true,
                    tableEditRender: {
                        name: 'EzmTextInput',
                    }
                },

            ]
        }
    }
}