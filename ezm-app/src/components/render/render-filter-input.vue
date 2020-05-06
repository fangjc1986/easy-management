<script>
    import Enum from "@/utils/Enum";
    import RenderMixin from "@/mixin/RenderMixin";

    export default {
        name: 'render-filter-input',
        mixins: [RenderMixin],
        components: {},
        props: {
            field: [Object],
            value: {
                default: ""
            },
            disabled: {
                default: false,
            },
            width:{
                default: '',
            }
        },
        data() {
            return {
                isFilter: true,
            }
        },
        computed: {
            renderComp() {
                return this.field.filterRender || new Enum.DefaultRender;
            },
        },
        watch: {
            value(v) {
            },
        },
        created() {
        },
        mounted() {
        },
        destroyed() {
        },
        methods: {
        },
        render(createElement, context) {
            return createElement(this.renderComp.name, this.renderOptions, this.renderSelectedOptions);
        }
    };
</script>

<style scoped lang="less">
    .render-filter-input {

    }
</style>