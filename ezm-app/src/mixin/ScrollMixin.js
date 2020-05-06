import ReloadMixin from "@/mixin/ReloadMixin";

export default {
    mixins: [ReloadMixin],
    components: {},
    props: {},
    data() {
        return {
            s_last_scrollTop: 0,
        }
    },
    computed: {},
    watch: {},
    created() {
    },
    mounted() {
    },
    destroyed() {
    },
    activated() {
        this.$el.scrollTo(0, this.s_last_scrollTop);
    },
    deactivated() {

    },
    methods: {
        closeWindowEvent() {
            this.s_last_scrollTop = 0;
            this.unload();
        },
        switchOutEvent() {
            this.s_last_scrollTop = this.$el.scrollTop || 0;
            this.tabOut();
        },
    }
}