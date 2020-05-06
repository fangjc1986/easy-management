/**
 * el-form 表单快速验证方法
 * @param form
 * @returns {Promise<unknown>}
 */
import {DialogUtil, TypeUtil} from "@/utils/common.util";

export const formValidate = (form) => {
    if (!form) {
        console.error("表单不存在！！");
    }
    return new Promise((resolve, reject) => {
        form.validate(valid => {
            if (valid) resolve();
            else {
                DialogUtil.toast("请检查表单！", "error");
                reject();
            }
        });
    })
};

/**
 * 获取字段顾虑名称
 * @param field
 * @returns {string|boolean}
 */
export const getFilterFieldName = (field) => {
    let suffix = "";
    let params = field.params || {};
    if (params.filterSuffix) suffix = field.params.filterSuffix;
    return (params.filterField || field.prop) + suffix;
};


/**
 * 从 options 中获取参数值
 * @param cellValue
 * @param row
 * @param column
 * @returns {*}
 */
export const formatterByOptions = ({cellValue, row, column}) => {
    if (!column.params || !column.params.options) return cellValue;
    let ops = column.params.options;
    if( TypeUtil.isFunction(ops) ) return cellValue;
    if (!TypeUtil.isArray(ops)) return ops;
    return (ops.find(x => x.value === cellValue) || {}).label || cellValue;
};



