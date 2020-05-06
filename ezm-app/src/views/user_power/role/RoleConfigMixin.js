import {
    commonValidate
} from "@/utils/validateUtil";

export default {
    data() {
        return {
            editSpan: {},

            fields: [
                {
                    // COMMENT： 名称
                    prop: 'name',
                    label: '名称',
                    editRender: true,
                    rules: [commonValidate.required,],
                    filterRank: 1,
                    editRank: 1,

                    width: 200,
                    tableEdit: true,
                    editLabel: true,
                },

                {
                    // COMMENT： 说明
                    prop: 'remark',
                    label: '说明',
                    editRender: {
                        name: 'EzmTextInput',
                    },
                    filterRank: 3,
                    editRank: 3,

                    width: 300,
                    tableEdit: true,
                },

                {
                    // COMMENT： 说明
                    prop: 'authCount',
                    label: '权限数量',
                    rules: [commonValidate.required,],
                    filterRank: 3,
                    editRank: 3,
                },

            ]
        }
    }
}