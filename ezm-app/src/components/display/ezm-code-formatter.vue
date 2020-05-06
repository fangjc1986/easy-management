<template>
    <div class="ezm-code-formatter scroll-xy-thin"
         :style="containerStyle"
    >
        <pre v-html="code" style="font-size: 13px;line-height: 1.3;"></pre>
    </div>
</template>

<script>
    import {DomUtil} from "@/utils/common.util";

    const beautify = require('js-beautify');
    const hljs = require('highlight.js');

    export default {
        name: 'ezm-code-formatter',
        mixins: [],
        components: {},
        props: {
            value: {
                default: '',
            },
            maxHeight: {
                default: '500px',
            },
            codeType: {
                default: 'js',
            }
        },
        data() {
            return {}
        },
        computed: {
            code() {
                const highlightedCode = hljs.highlightAuto(beautify.js(this.value)).value;
                return highlightedCode;
            },
            containerStyle() {
                return {
                    'max-height': DomUtil.styleSize(this.maxHeight),
                }
            }
        },
        watch: {},
        created() {
        },
        mounted() {
        },
        destroyed() {
        },
        methods: {}
    };
</script>

<style scoped lang="less">
    .ezm-code-formatter {

    }
</style>