<script>
    import RenderMixin from "@/mixin/RenderMixin";
    import Enum from "@/utils/Enum";

    export default {
        name: 'render-edit-input',
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
            width: {
                default: '',
            }
        },
        data() {
            return {}
        },
        computed: {
            renderComp() {
                return this.field.editRender || new Enum.DefaultRender();
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
        methods: {},
        render(createElement, context) {
            return createElement(this.nameComp, this.renderOptions, this.renderSelectedOptions);
        }
    };
</script>