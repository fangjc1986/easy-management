import store from "@/store";
import {debThr} from "@/utils/common.util";

export default {
    mixins: [],
    components: {},
    props: {},
    data() {
        return {
            r_focus_reload: false,
            r_is_page: false,
            r_route: '',
            r_mounted: false,
        }
    },
    computed: {},
    watch: {},
    created() {
    },
    mounted() {
        this.r_mounted = false;
        this.activatedEvent();
    },
    destroyed() {
    },
    activated() {
        if (this.r_mounted) {
            this.activatedEvent();
        } else {
            this.r_mounted = true;
        }
    },
    deactivated() {
        if (store.state.tabs.showTabs.find(x => x.path === this.r_route)) {
            this.switchOutEvent();
            return;
        }
        this.closeWindowEvent();
    },
    methods: {
        activatedEvent() {
            this.r_route = this.$route.path;
            if (this.r_focus_reload) {
                this.openWindowEvent && this.openWindowEvent();
            } else if (store.state.tabs.showTabs.find(x => x.path === this.r_route) && this.r_mounted) {
                this.switchInEvent && this.switchInEvent();
            } else {
                this.openWindowEvent && this.openWindowEvent();
            }
            if (this.r_is_page || 1) {
                debThr.debounce("reload_mixin_activateEvent", () => {
                    this.$store.dispatch("tabs/setActiveTabsName", this.r_route);
                    this.$store.dispatch("tabs/pushTo", this.$route);
                });
            }

        },
        reload() {
        },
        unload() {
        },
        tabLoad() {
        },
        tabOut() {

        },
        closeWindowEvent() {
            this.unload();
        },
        openWindowEvent() {
            this.reload();
        },
        switchInEvent() {
            this.tabLoad();
        },
        switchOutEvent() {
            this.tabOut();
        },
    }
}