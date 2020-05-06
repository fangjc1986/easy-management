<script>
    import ReloadMixin from "@/mixin/ReloadMixin";

    export default {
        name: 'render-item',
        mixins: [ReloadMixin],
        components: {},
        props: {
            index: Number,
        },
        data() {
            return {}
        },
        computed: {},
        watch: {
        },
        created() {
        },
        mounted() {
        },
        destroyed() {
        },
        methods: {},
        render(createElement, context) {
            return this.$parent.tabs[this.index].renderElement(createElement);
        }
    };
</script>