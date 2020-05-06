import {toLine} from "@/utils/common.util";

export default class Paginate {

    total = 0;
    size = 20;
    defaultSize = 20;
    current = 1;
    searchCount = true;
    pages = 0;
    toPage = 1;
    records = [];
    changeCallBack = new Function();
    asc = '';
    desc = '';
    pageInstance = {};

    /**
     * 构造函数
     * @param pageInstance  页面实例
     * @param changeCallBack    刷新页面回调函数
     * @param size  每页数量
     */
    constructor(pageInstance, changeCallBack = null, size = 20) {
        this.pageInstance = pageInstance;
        this.changeCallBack = changeCallBack;
        this.size = size || 20;
        this.defaultSize = size || 20;
    }

    /**
     * 将服务器返回信息写入本对象
     * @param resp  服务器返回
     * @returns {Paginate}
     */
    setResponse(resp) {
        this.total = resp.total;
        this.size = resp.size;
        this.current = resp.current;
        this.pages = resp.pages;
        this.records = resp.records;
        return this;
    }

    /**
     * 修改页面信息并刷新页面
     * 在分页组件中调用此方法跳转页面
     * @param opt   分页组件传递对象
     * @returns {Paginate}
     */
    changePage(opt) {
        this.toPage = opt.currentPage;
        this.size = opt.pageSize;
        this.changeCallBack && this.changeCallBack();
        return this;
    }

    /**
     * 获取分页请求象
     * @returns {{current: number, size: number}}
     */
    getRequestParams() {
        let json = {
            size: this.size,
            current: this.toPage,
        };
        if (this.asc) {
            json.asc = this.asc;
        }
        if (this.desc) {
            json.desc = this.desc;
        }
        return json;
    }


    /**
     * 重置页面信息
     * 回到第一页
     * @returns {Paginate}
     */
    resetPage() {
        this.current = 1;
        this.size = this.defaultSize;
        this.toPage = 1;
        this.total = 0;
        return this;
    }

    /**
     * 设置排序信息
     * 在 ezm-table 组件触发排序时调用
     * @param sort  排序对象
     * @returns {Paginate|void}
     */
    setSort(sort) {
        if (!sort.order) return this.resetSort();
        let prop = toLine(this.getSortField(sort.prop, sort.params || {}));
        if (sort.order === 'desc') {
            this.asc = '';
            this.desc = prop;
        } else {
            this.desc = '';
            this.asc = prop;
        }
        return this;
    }


    /**
     * 追加字段前缀
     * @param fieldName
     * @param params
     * @returns {*}
     */
    getSortField(fieldName, params) {
        let fp = params.filterPrefix || this.pageInstance.defaultFilterPrefix || "";
        return fp + fieldName;
    }

    /**
     * 重置排序信息
     */
    resetSort() {
        this.asc = '';
        this.desc = '';
    }

}