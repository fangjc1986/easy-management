import {
    commonValidate
} from "@/utils/validateUtil";
import EzmIconPicker from '@/components/selector/ezm-icon-picker';
import {treeTrace} from "@/utils/common.util";
import {isEmpty} from "xe-utils/dist/xe-utils";
import AuthSelector from "@/views/user_power/auth/auth-selector"

export default {
    data() {
        return {
            editSpan: {
                span: 8
            },
            fields: [
                {
                    // COMMENT： 名称
                    prop: 'name',
                    label: '名称',
                    editRender: true,
                    rules: [commonValidate.required,],
                    filterRank: 1,
                    editRank: 1,

                    treeNode: true,
                    editLabel: true,

                    width: 260,
                    tableEdit: true,
                },

                {
                    // COMMENT： 路径
                    prop: 'path',
                    label: '路径',
                    editRender: {
                        name: 'ElInput',
                    },
                    rules: [commonValidate.required,],
                    filterRank: 2,
                    editRank: 2,
                    editSpan: {span: 16},
                    width: 300,

                    filters: [{data: ''}],
                    filterRender: {
                        name: 'ElInput',
                    },

                    tableEdit: true,

                    params: {
                        filterSuffix: '_like',
                    }
                },

                {
                    // COMMENT： 类型：1：页面；2：API；
                    prop: 'type',
                    label: '类型',
                    editRender: {
                        name: 'ElSelect',
                        options: true,
                    },
                    rules: [commonValidate.required,],
                    filterRank: 3,
                    editRank: 3,
                    cellRender: {
                        name: "ezm-options-tag",
                        options: true,
                    },


                    // formatter: formatterByOptions,
                    params: {
                        defaultFilterOptions: [
                            {label: '选择全部', value: ''},
                        ],
                        options: [
                            {label: '菜单页面', value: 1, color: '#2D8CF0'},
                            {label: 'API接口', value: 2, color: '#19BE6B'},
                            {label: '局部', value: 3, color: '#00C2B1'},
                        ],
                    },

                },

                {
                    // COMMENT： 图标
                    prop: 'icon',
                    label: '图标',
                    editRender: {
                        name: EzmIconPicker,
                    },
                    rules: [
                        {
                            validator: (rule, value, cb) => {
                                if (this.editForm.type === 1 && isEmpty(value.trim())) {
                                    return cb(new Error("菜单页面下不能为空"));
                                }
                                return cb();
                            },
                        }
                    ],
                    filterRank: 4,
                    editRank: 4,
                    width: 200,

                    params: {},
                    editValue: 'icon-folder1',
                    tableEdit: true,
                },

                {
                    // COMMENT： 父id
                    prop: 'pid',
                    label: '所属权限',
                    editRender: {
                        name: AuthSelector
                    },
                    rules: [
                        commonValidate.required,
                        {
                            validator: (rule, value, cb) => {
                                let res = treeTrace(this.paginate.records, (x) => x.id === value, 'id');
                                res.pop();
                                if (value === this.editForm.id) {
                                    return cb(new Error("父权限不能是自己！"));
                                }
                                if (res.includes(this.editForm.id)) {
                                    return cb(new Error("父权限不能是自己的子权限！"));
                                }
                                cb();
                            }
                        }
                    ],
                    filterRank: 6,
                    editRank: 0,

                    editValue: '0',
                    show: false,
                    editSpan: {span: 24},
                    editWidth: '300px'
                },

                {
                    // COMMENT： 分类
                    prop: 'category',
                    label: '分类',
                    editRender: {
                        name: 'ElSelect',
                        options: true,
                    },
                    rules: [commonValidate.required,],
                    filterRank: 7,
                    editRank: 7,

                    params: {
                        options: 'SYS_AUTH_CAT'
                    },
                    tableEdit: true,
                },

                {
                    // COMMENT： 分类
                    prop: 'rank',
                    label: '排序',
                    editRender: true,
                    rules: [
                        commonValidate.required,
                        {
                            validator: (rule, value, cb) => {
                                if (/^[\-\+]?\d+$/.test(value + '')) {
                                    return cb();
                                }
                                return cb(new Error("必须为整数"));
                            }
                        },
                    ],
                    filterRank: 7,
                    editRank: 7,

                    editValue: 0,
                    tableEdit: true,
                },

                {
                    // COMMENT： 备注说明
                    prop: 'remark',
                    label: '备注说明',
                    editRender: {
                        name: 'ElInput',
                        attrs: {
                            type: 'textarea',
                            rows: 5,
                        }
                    },
                    filterRank: 8,
                    editRank: 8,

                    editSpan: {span: 24},
                    width: 300,
                },

            ]
        }
    },
    methods: {
        getFieldByProp(prop) {
            return this.fields.find(x => x.prop === prop);
        }
    }
}