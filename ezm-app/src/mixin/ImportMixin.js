import PageTableFieldMixin from "@/mixin/page-mixin/PageTableFieldMixin";

export default {
    mixins: [PageTableFieldMixin],
    props: {
        defaultList: {
            default: () => [],
        }
    },
    data() {
        return {
            list: [],
            importTable: null,
        }
    },
    created() {

    },
    mounted() {

    },
    computed: {},
    watch: {
        defaultList(v) {
            this.list = v;
        }
    },
    methods: {
        getEditForm() {
            return this.fields.reduce((t, f) => {
                t[f.prop] = f.editValue || '';
                return t;
            }, {})
        }
    }
}