import Enum from "@/utils/Enum";
import XEUtils from 'xe-utils'
import numeral from 'numeral';
import Mock from 'mockjs';
import dayjs from "dayjs";
import low from 'lowdb'
import LocalStorage from 'lowdb/adapters/LocalStorage'
import setting from "@/setting";
import {MessageBox} from "element-ui";
import {v4 as uuidv4} from 'uuid';


dayjs.extend(require('dayjs/plugin/advancedFormat'));
dayjs.extend(require('dayjs/plugin/weekOfYear'));
dayjs.extend(require('dayjs/plugin/isSameOrBefore'));
dayjs.extend(require('dayjs/plugin/isSameOrAfter'));

export const dayjsUtil = dayjs;
export const XeUtil = XEUtils;
export const numeralUtil = numeral;
export const lowDb = low(new LocalStorage('db'));

/**
 * router 载入
 * 在生产环境下使用懒加载
 * @param r
 * @returns {function(): *}
 */
export const routerImport = (r) => {
    if (process.env.NODE_ENV === 'production') {
        return () => import('@/views/' + r );
    } else {
        return require('@/views/' + r).default;
    }
};


export const cookiesUtil = {
    set(key, value, expire = null) {
        Enum.Vue.$cookies.set(key, 'ezm-' + JSON.stringify(value), expire || '30d');
    },
    get(key) {
        try {
            let r = Enum.Vue.$cookies.get(key);
            if (r) {
                return JSON.parse(r.slice(4));
            }
        } catch (e) {
        }
        return null;
    }
};

/**
 * 判断元素是否为空
 * @param v
 * @param zeroIsEmpty  0 是否为空
 * @returns {boolean}
 */
export const isEmpty = (v, zeroIsEmpty = false) => {
    if (typeof v === 'number') return v ? false : zeroIsEmpty;
    if (!v) return true;
    if (typeof v === 'object') return !Object.keys(v).length;
    return false;
};


/**
 * 类型工具
 */
export const TypeUtil = {
    isString(val) {
        return typeof val === 'string' || val instanceof String;
    },
    isArray(val) {
        return Object.prototype.toString.call(val) === '[object Array]';
    },
    isObject(val) {
        return Object.prototype.toString.call(val) === '[object Object]';
    },
    isNumber(v) {
        return typeof v === 'number';
    },
    isFunction(v) {
        return typeof v === 'function';
    },
    isEmpty(v, zeroIsEmpty = false) {
        if (this.isNumber(v)) return v ? false : zeroIsEmpty;
        if (!v) return true;
        if (this.isObject(v)) return !Object.keys(v).length;
        if (this.isArray(v)) return !v.length;
        return false;
    }
};

/**
 * DOM 工具
 */
export const DomUtil = {
    // 样式参数转换为带单位的参数
    styleSize(v) {
        v += '';
        if (/^\d+$/.test(v)) return v + 'px';
        return v;
    },
    /**
     * 获取元素在 屏幕中的位置
     * {
     *     top , right ,bottom, left
     * }
     * @param ele
     * @returns {DOMRect}
     */
    getElementPosition(ele) {
        return ele.getBoundingClientRect();
    }
};

export const FileUtil = {
    sizeString2Byte(str) {
        if (TypeUtil.isNumber(str)) return str;
        str = str.toLowerCase();
        let [units, n1024] = [
            ['b', 'k', 'm', 'g', 't', 'p'],
            [0, 1, 2, 3, 4, 5],
        ];
        while (units.length) {
            let u = units.pop();
            let n = n1024.pop();
            if (str.indexOf(u) === -1) continue;
            str = str.replace(u, '');
            return (str * 1) * Math.pow(1024, n);
        }
        return str * 1;
    }
};

/**
 * 检查是否为数字类型
 * @param v
 * @param needInteger
 * @returns {boolean}
 */
export const isNumber = (v, needInteger = false) => {
    try {
        v = v * 1;
    } catch (e) {
        return false;
    }
    if (needInteger) {
        return (v + "").indexOf(".") < 0;
    }
    return true;
};


/**
 * mock 模拟数据
 * @param m
 */
export const mock = (m) => {
    return Mock.mock(m);
};

/**
 * mock 模拟数据
 * 直接传递对象以及数量即可
 * @param obj
 * @param num
 */
export const mockObj = (obj, num) => {
    let m = {};
    m[`list|${num}`] = [obj];
    return mock(m).list;
};

/**
 * 生成约定的 返回结果
 * @param obj
 * @param num
 * @param code
 * @returns {{code: number, data: *, message: string}}
 */
export const mockResponse = (obj, num, code = 200) => {
    return {
        code: code,
        message: 'success',
        data: mockObj(obj, num),
    }
};

/**
 * 配置URL拦截
 * @param url
 * @param obj
 * @param num
 * @param code
 * @param activated
 * @param method
 */
export const mockResponseIntercept = (url, obj, num, activated = true, code = 200, method = 'all') => {
    if (!activated) return;
    method = method === 'all' ? 'get|post' : method;
    let regex = new RegExp(method, 'i');
    Mock.mock(setting.serverBaseUrl + url, regex, mockResponse(obj, num, code));
};

/**
 * url编码
 * 将 JSON 格式转换成 key=value&key=value格式
 * 并将 value 做 url 编码
 * @param param
 * @param key
 * @returns {string}
 */
export const urlEncode = (param, key) => {
    let paramStr = "";
    if (typeof param === "string" || typeof param === 'number' || typeof param === 'boolean') {
        paramStr += "&" + key + "=" + encodeURIComponent(param);
    } else {
        for (let i in param) {
            if (!param.hasOwnProperty(i)) continue;
            let item = param[i];
            // let k = key == null ? i : key + (true || param instanceof Array ? "[" + i + "]" : "." + i);
            let k = key == null ? i : key + ("[" + i + "]");
            paramStr += '&' + urlEncode(item, k);
        }
    }
    return paramStr.substr(1);
};

/**
 * 提示信息
 */
export const DialogUtil = {
    toast(content, status = 'success') {
        return Enum.vue.$XModal.message({
            message: content,
            status: status,
        })
    },
    confirm(content, title = "确认信息", type = 'warning') {
        return MessageBox.confirm(content, {
            title: title,
            dangerouslyUseHTMLString: true,
            type: "warning",
            customClass: "ezm-confirm",
            cancelButtonClass: "ezm-cancel-button el-button--mini ",
            confirmButtonClass: "ezm-confirm-button el-button--mini",
        });
    }
};

/**
 * 常规时间格式化
 * @param t
 * @returns {string}
 */
export const dateTimeFormat = (t) => {
    return dayjs(t).format("YYYY-MM-DD HH:mm:ss");
};


/**
 * 列表 转换为 树状结构
 * @param list
 * @param pid
 * @param childKey
 * @param pidKey
 * @param idKey
 * @returns {[]}
 */
export const list2Tree = (list, pid = '0', {childKey = 'children', pidKey = 'pid', idKey = 'id'} = {}) => {
    let res = [];
    list.forEach(l => {
        if (l[pidKey] === pid) {
            l[childKey] = list2Tree(list, l[idKey], {
                childKey: childKey,
                pidKey: pidKey,
                idKey: idKey,
            });
            if (!l[childKey].length) {
                delete l[childKey];
            }
            res.push(l);
        }
    });
    return res;
};

/**
 * 从树状结构中查找内容
 * @param tree
 * @param cb
 * @param onlyLeaf
 * @param children
 * @returns {[]}
 */
export const treeFilter = (tree, cb, onlyLeaf = false, children = 'children') => {
    let res = [];
    tree.forEach(x => {
        if (cb(x)) {
            if (!onlyLeaf) {
                res.push(x);
            } else if (!x.children || !x.children.length) {
                res.push(x);
            }
        }
        if (x.children) {
            res = res.concat(treeFilter(x.children, cb, onlyLeaf, children));
        }
    });
    return res;
};
/**
 * 将两个list 合并为一个树状结构
 * @param list1
 * @param list2
 * @param idKey
 * @param pidKey
 * @param childKey
 * @returns {*}
 */
export const listMerge2Tree = (list1, list2, idKey = 'id', pidKey = 'pid', childKey = 'children') => {
    list1.forEach(row => {
        row[childKey] = list2.filter(r2 => r2[pidKey] === row[idKey]) || [];
    });
    return list1;
};


/**
 * 查找树状结构轨迹
 * @param tree
 * @param cb
 * @param keyName
 * @param children
 * @returns {[]}
 */
export const treeTrace = (tree = [], cb, keyName = 'id', children = 'children') => {
    let res = [];
    for (let i in tree) {
        let D = tree[i];
        if (cb(D)) {
            res.push(D[keyName]);
            break;
        }
        if (D[children] && D[children].length) {
            let childRes = treeTrace(D[children], cb, keyName, children);
            if (childRes.length) {
                res.push(D[keyName]);
                res = res.concat(childRes);
                break;
            }
        }
    }
    return res;
};

/**
 * 根据集合key进行去重
 * @param collection
 * @param fieldMethod
 * @returns {[]}
 */
export const collectionRemoveDuplication = (collection, fieldMethod) => {
    let fieldSet = new Set();
    let res = [];
    collection.forEach(x => {
        if (fieldSet.has(fieldMethod(x))) return;
        res.push(x);
        fieldSet.add(fieldMethod(x));
    });
    return res;
};

/**
 * 从 label - value 中获取 label
 * @param list
 * @param value
 * @returns {*}
 */
export const getLabelFromSelector = (list, value) => {
    let item = list.find(x => x.value === value);
    return item ? item.label : "";
};

/**
 * 将过滤条件加上表格前缀
 * 后端默认以 t 作为主表名称
 * @param filterForm
 * @param prefix
 * @returns {{}}
 */
export const convertFilterForm = (filterForm, prefix = 't.') => {
    let res = {};
    for (let i in filterForm) {
        if (!filterForm.hasOwnProperty(i)) continue;
        let D = filterForm[i];
        if (!i.includes('.')) {
            res[prefix + i] = D;
        } else {
            res[i] = D;
        }
    }
    return res;
};

/**
 * 从树状结构中找到元素
 * @param tree
 * @param cb
 * @param childKey
 * @returns {null|*}
 */
export const findInTree = (tree, cb, {childKey = "children"} = {}) => {
    for (let i = 0; i < tree.length; i++) {
        let D = tree[i];
        if (cb(D)) return D;
        if (D.children && D.children.length) {
            return findInTree(tree, cb, {childKey: childKey});
        }
    }
    return null;
};

// 下划线转换驼峰
export const toHump = (name) => {
    return name.replace(/\_(\w)/g, function (all, letter) {
        return letter.toUpperCase();
    });
};

// 驼峰转换下划线
export const toLine = (name) => {
    return name.replace(/([A-Z])/g, "_$1").toLowerCase();
};

/**
 * 数组是否一样
 * @param arr1
 * @param arr2
 * @returns {boolean}
 */
export const arraySame = (arr1, arr2) => {
    if (arr1.length !== arr2.length) return false;
    for (let i = 0; i < arr1.length; i++) {
        if (!arr2.find(x => x === arr1[i])) {
            return false;
        }
    }
    return true;
};

/**
 * 修改 web 标题
 * @param title
 */
export const changeWebTitle = (title) => {
    document.querySelector("title").innerHTML = title;
};

/**
 * 创建随机 uuid
 * v4: 随机
 * v1: 时间戳
 * @returns {*}
 */
export const createUUID = () => {
    return uuidv4();
};
/**
 * 限流和节流
 */
export const debThr = {
    /**
     * 默认节流和防抖动延迟时间
     */
    _defDelay: 100,
    /**
     * 节流阀缓存
     */
    _thrKey: {},
    /**
     * 防抖动缓存
     */
    _debKey: {},
    /**
     * 仅执行一次
     */
    _onceKey: {},

    /**
     * 一次性执行完成不再执行
     * @param key
     * @param callback
     */
    once(key, callback) {
        if (this._onceKey[key]) return;
        this._onceKey[key] = true;
        callback && callback();
    },
    /**
     * 节流阀
     * @param key
     * @param callback
     * @param delay
     */
    throttle(key, callback, delay = this._defDelay) {
        if (this._thrKey[key]) return;
        this._thrKey[key] = true;
        callback && callback();
        setTimeout(() => {
            this._thrKey[key] = false;
        }, delay);
    },

    /**
     * 防抖动
     * @param key
     * @param callback
     * @param delay
     */
    debounce(key, callback, delay = this._defDelay + 1) {
        if (this._debKey[key]) clearTimeout(this._debKey[key]);
        this._debKey[key] = setTimeout(() => {
            callback && callback();
        }, delay);
    },

    /**
     * 节流阀与防抖动一起使用
     * @param key
     * @param func
     * @param delay
     */
    thrAndDeb(key, func, delay) {
        delay = delay === null ? 100 : delay;
        this.throttle(key, func, delay);
        this.debounce(key, func, delay);
    },

    /**
     * 节流阀 函数表达式返回
     * @param func  函数
     * @param delay 节流延迟
     * @returns {Function}
     */
    throttleFunction(func, delay = this._defDelay) {
        let isValid = true;
        return (...params) => {
            if (!isValid) return;
            isValid = false;
            func && func(...params);
            setTimeout(() => {
                isValid = true;
            }, delay);
        };
    },
    /**
     * 防抖动 函数表达式返回
     * @param func  函数
     * @param delay 防抖延迟
     * @returns {Function}
     */
    debounceFunction(func, delay = this._defDelay + 1) {
        let timer;
        return (...params) => {
            clearTimeout(timer);
            timer = setTimeout(() => {
                func && func(...params);
            }, delay);
        }
    },
    /**
     * 节流阀和防抖动函数表达式返回
     * @param func      函数
     * @param delayThrottle 节流延迟
     * @param delayDebounce 防抖延迟
     * @returns {Function}
     */
    thrAndDebFunction(func, delayThrottle = null, delayDebounce = null) {
        let throttleFunc = this.throttle(func, delayThrottle);
        let debounceFunc = this.debounce(func, delayDebounce);
        return (...params) => {
            throttleFunc(...params);
            debounceFunc(...params);
        }
    },
};