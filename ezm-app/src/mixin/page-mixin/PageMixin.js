import ScrollMixin from "@/mixin/ScrollMixin";
import PageSetGetMixin from "@/mixin/page-mixin/PageSetGetMixin";
import Paginate from "@/utils/Paginate";
import PageTableFieldMixin from "@/mixin/page-mixin/PageTableFieldMixin";

export default {
    mixins: [ScrollMixin, PageSetGetMixin, PageTableFieldMixin],
    components: {},
    props: {
        isSelector: {
            default: false,
        },
        selectType: {
            default: 'radio',
        },
        selectorInstance: {
            default: null,
        },
        height: {
            default: null,
        },
        autoLoadUrlParams: {
            default: true,
        },
        isNotPage:{
            default: false,
        }
    },
    data() {
        return {
            r_is_page: !this.isSelector && !this.isNotPage,
            paginate: new Paginate(this, this.getList),
            pageName: "名称",
            config: Object,
            defaultCellWidth: 140,
            showEditWindow: false,
            lastFullPath: '',
            listLoaded: false,
        }
    },
    computed: {},
    watch: {},
    created() {
        if (this.selectorInstance) {
            this.selectorInstance.selectorPageInstance = this;
        }
    },
    mounted() {

    },
    destroyed() {
    },
    activated() {
    },
    deactivated() {
    },
    methods: {
        beforeReload() {
            this.lastFullPath = this.$route.fullPath;
            this.filterForm = this.$route.query;
            this.resetDefaultParams();
            this.getList();
        },
        unload(){
            this.ezmTable.setAllCheckBoxRow(false);
        },
        afterReload() {

        },

        checkPathParams() {
            if (this.lastFullPath !== this.$route.fullPath && this.autoLoadUrlParams) {
                this.reload();
            }
            this.lastFullPath = this.$route.fullPath;
        },

        tabLoad() {
            this.beforeTabLoad();
        },

        beforeTabLoad() {
            this.checkPathParams();
        },

        getList() {
        },

        getListAndClearCheck() {
            this.getList(() => {
                this.ezmTable.clearCheckBoxRow();
            })
        },

        refreshList({scrollLeft = 0, scrollTop = 0} = {}) {
            this.paginate.resetPage();
            this.getList(() => setTimeout(() => {
                if (this.ezmTable.$refs.table) this.ezmTable.$refs.table.scrollTo(scrollLeft, scrollTop);
                this.ezmTable.clearCheckBoxRow();
                this.resetEditForm();
            }));
        },

        reloadList() {
            this.paginate.resetSort();
            this.refreshList();
        },

        clickEdit(row) {
            this.showEditWindow = true;
            this.editForm = Object.assign({}, row);
        },
        clickAdd() {
            this.showEditWindow = true;
            this.resetEditForm();
        },
        parentNoticeUpdate() {
        },
        childNoticeUpdate() {
        },
        noticeUpdate(params = {}) {
            this.$emit('on-notice-update');
            if (this.parentPageInstance && this.parentPageInstance.childNoticeUpdate) {
                this.parentPageInstance.childNoticeUpdate(params);
            }
            if (this.childPageInstance && this.childPageInstance.parentNoticeUpdate) {
                this.childPageInstance.parentNoticeUpdate(params);
            }
        },
        getListSuccess() {
            setTimeout(()=>this.listLoaded = true);
        },
        selectorConfirm(list){
            this.$emit('on-selected-confirm', list);
        }
    }
}