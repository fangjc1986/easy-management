import {
    commonValidate
} from "@/utils/validateUtil";

export default {
    data() {
        return {
            editSpan: {},

            fields: [
                {
                    // COMMENT： 用户id
                    prop: 'userId',
                    label: '用户id',
                    editRender: true,
                    rules: [commonValidate.required,],
                    filterRank: 1,
                    editRank: 1,
                    show: false,
                },

                {
                    // COMMENT： 用户名
                    prop: 'userName',
                    label: '用户名',
                    editRender: true,
                    rules: [commonValidate.required,],
                    filterRank: 2,
                    editRank: 2,
                    filters: [{data: ''}],
                    filterRender: true,
                },

                {
                    // COMMENT： 类型：1：登录；2：登出
                    prop: 'type',
                    label: '类型',
                    rules: [commonValidate.required,],
                    filterRank: 3,
                    editRank: 3,
                    cellRender: {
                        name: 'EzmOptionsTag',
                        options: true,
                    },
                    params: {
                        options: [
                            {label: '登录系统', value: 1, color: '#2D8CF0'},
                            {label: '退出系统', value: 2, color: '#F06292'},
                        ]
                    }
                },

                {
                    // COMMENT： 是否成功
                    prop: 'success',
                    label: '是否成功',
                    editRender: true,
                    rules: [commonValidate.required,],
                    filterRank: 4,
                    editRank: 4,
                    show: false,
                },

                {
                    // COMMENT： 登录ip
                    prop: 'ip',
                    label: '登录ip',
                    editRender: true,
                    rules: [commonValidate.required,],
                    filterRank: 5,
                    editRank: 5,

                    filterRender: true,
                    filters: [{data: ''}],
                },

            ]
        }
    }
}