import {
    commonValidate
} from "@/utils/validateUtil";
import Enum from "@/utils/Enum";

export default {
    data() {
        return {
            editSpan: {},
            defaultEditGroupName: '',
            fields: [
                {
                    // COMMENT： 用户名（登录用）
                    prop: 'userName',
                    label: '用户名',
                    editRender: true,
                    rules: [
                        commonValidate.required,
                        commonValidate.userName,
                        {min: 4, max: 20, message: '长度在 4 - 20 之间'}
                    ],
                    filterRank: 1,
                    editRank: 1,

                    editGroup: '基本信息',
                    editDisabled: true,
                    editLabel: true,
                },

                {
                    // COMMENT： 真实姓名
                    prop: 'realName',
                    label: '真实姓名',
                    editRender: true,
                    filterRank: 2,
                    editRank: 2,

                    editGroup: '补充信息',
                    tableEdit: true,
                },

                {
                    // COMMENT： 昵称
                    prop: 'nickName',
                    label: '昵称',
                    editRender: true,
                    filterRank: 3,
                    editRank: 3,

                    editGroup: '补充信息',
                    tableEdit: true,
                },

                {
                    // COMMENT： 用户类型：1：普通用户；2：后台用户；10：root账号
                    prop: 'type',
                    label: '用户类型',
                    editRender: {
                        name: "ElSelect",
                        options: true,
                    },
                    rules: [commonValidate.required,],
                    filterRank: 2,
                    editRank: 2,

                    editGroup: '基本信息',
                    params: {
                        options: [
                            {label: '普通用户', value: 1, color: '#2D8CF0'},
                            {label: '后台用户', value: 2, color: '#19BE6B'}
                        ]
                    },

                    editSpan:{
                        md: 6,
                        lg: 4,
                        xl: 4,
                    },

                    tableEdit: true,
                },

                {
                    // COMMENT： 是否激活
                    prop: 'activated',
                    label: '是否激活',
                    editRender: {
                        type: 'visible',
                        name: "EzmSwitch"
                    },
                    rules: [commonValidate.required,],
                    filterRank: 18,
                    editRank: 18,

                    editSpan:{
                        md: 3,
                        lg: 2,
                        xl: 2,
                    },

                    editValue: 1,
                    editGroup: '基本信息',
                    tableEdit: true,
                },

                {
                    // COMMENT： 密码（加密后的）
                    prop: 'password',
                    label: '密码',
                    editRender: {
                        name: "ElInput",
                        props: {
                            type: 'password'
                        }
                    },
                    rules: [
                        {
                            validator: (rule, value, cb) => {
                                if (this.editForm.id) {
                                    return cb();
                                }
                                if (!/^\S{6,20}$/.test(value)) {
                                    return cb(new Error("长为6-20位的非空字符"));
                                }
                                return cb();
                            }
                        }
                    ],
                    filterRank: 4,
                    editRank: 4,

                    show: false,

                    editGroup: '基本信息',
                    editRemarkText: '新增时密码必须填写',
                },

                {
                    // COMMENT： 密码（加密后的）
                    prop: 'rePassword',
                    label: '重复密码',
                    editRender: {
                        name: "ElInput",
                        props: {
                            type: 'password'
                        }
                    },
                    rules: [
                        {
                            validator: (rule, value, cb) => {
                                if ((this.editForm.password || "") !== (value || "")) {
                                    return cb(new Error("两次输入密码不一致"));
                                }
                                return cb();
                            }
                        }
                    ],
                    filterRank: 4.5,
                    editRank: 4.5,

                    show: false,

                    editGroup: '基本信息',
                },


                {
                    // COMMENT： 年龄
                    prop: 'age',
                    label: '年龄',
                    editRender: true,
                    filterRank: 6,
                    editRank: 6,
                    rules: [
                        {
                            validator: (rule, value, cb) => {
                                if (!value || /^\d{1,3}$/.test(value)) return cb();
                                return cb(new Error("少于3位整数"))
                            }
                        }
                    ],

                    editGroup: '补充信息',
                    tableEdit: true,
                    editValue: 0,
                },

                {
                    // COMMENT： 性别：1：女，2：男；
                    prop: 'gender',
                    label: '性别',
                    editRender: {
                        name: "ElSelect",
                        options: true,
                    },
                    filterRank: 7,
                    editRank: 7,

                    editGroup: '补充信息',
                    params: {
                        options: [
                            {label: '女', value: 1, color: "#ED4014"},
                            {label: '男', value: 2, color: "#2D8CF0"},
                        ]
                    },
                    tableEdit: true,
                },


                {
                    // COMMENT： 身份证号
                    prop: 'idCard',
                    label: '身份证号',
                    editRender: true,
                    filterRank: 9,
                    editRank: 9,
                    rules: [
                        commonValidate.idCard,
                    ],
                    editGroup: '补充信息',
                    tableEdit: true,
                    width: 240,
                },

                {
                    // COMMENT： 座机电话
                    prop: 'phone',
                    label: '座机电话',
                    editRender: true,
                    filterRank: 10,
                    editRank: 10,
                    rules: [
                        commonValidate.integer,
                    ],
                    editGroup: '补充信息',
                    tableEdit: true,
                },

                {
                    // COMMENT： 移动电话
                    prop: 'mobile',
                    label: '移动电话',
                    editRender: true,
                    filterRank: 11,
                    editRank: 11,
                    rules: [
                        commonValidate.mobile,
                    ],
                    editGroup: '补充信息',
                    tableEdit: true,

                },

                {
                    // COMMENT： 生日
                    prop: 'birthday',
                    label: '生日',
                    editRender: Enum.renders.dateRender(),
                    filterRank: 13,
                    editRank: 11.5,

                    editGroup: '补充信息',
                    tableEdit: true,
                },

                {
                    // COMMENT： 联系地址
                    prop: 'address',
                    label: '联系地址',
                    editRender: {
                        name: "EzmTextInput"
                    },
                    filterRank: 12,
                    editRank: 12,

                    editGroup: '补充信息',
                    width: 300,
                    tableEdit: true,
                },


                {
                    // COMMENT： 备注说明
                    prop: 'remark',
                    label: '备注说明',
                    editRender: {
                        name: "EzmTextInput"
                    },
                    filterRank: 15,
                    editRank: 15,

                    editGroup: '补充信息',
                    tableEdit: true,
                    width: 300,
                },


            ]
        }
    }
}