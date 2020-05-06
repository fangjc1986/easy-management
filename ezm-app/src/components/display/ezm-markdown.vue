<template>
    <div class="ezm-markdown markdown-body">
        <div class="w100p" v-html="html"
        ></div>
    </div>
</template>

<script>
    import VNodeUtil from "@/utils/VNodeUtil";

    let beautify = require('js-beautify').js;
    let beautify_css = require('js-beautify').css;
    let beautify_html = require('js-beautify').html;
    let beautifyList = {
        'javascript': beautify,
        'css': beautify_css,
        'html': beautify_html
    };
    let hljs = require('highlight.js'); // https://highlightjs.org/
    let beautifyOpt = {
        "indent_size": 4,
        "indent_char": " ",
        "indent_with_tabs": false,
        "editorconfig": false,
        "eol": "\n",
        "end_with_newline": false,
        "indent_level": 0,
        "preserve_newlines": true,
        "max_preserve_newlines": 10,
        "space_in_paren": false,
        "space_in_empty_paren": false,
        "jslint_happy": false,
        "space_after_anon_function": false,
        "space_after_named_function": false,
        "brace_style": "collapse",
        "unindent_chained_methods": false,
        "break_chained_methods": false,
        "keep_array_indentation": false,
        "unescape_strings": false,
        "wrap_line_length": 0,
        "e4x": false,
        "comma_first": false,
        "operator_position": "before-newline"
    };
    // Actual default values
    let md = require('markdown-it')({
        highlight: function (str, lang) {
            // 添加这两行才能正确显示 <>
            str = str.replace(/(&lt;)|(\[<\])/ig, "<");
            str = str.replace(/(&gt;)|(\[>\])/ig, ">");
            str = str.replace('[script]', "<script>");
            str = str.replace('[\/script]', "<\/script>");
            if (lang === 'js') {
                lang = 'javascript';
            }

            if (lang && hljs.getLanguage(lang)) {
                try {
                    let html = str;

                    if (typeof beautifyList[lang] !== "undefined") {
                        html = beautifyList[lang](html, beautifyOpt);
                    } else if (lang !== 'none') {
                        html = beautifyList['javascript'](html, beautifyOpt);
                    }
                    html = hljs.highlight(lang, html, true).value;

                    return '<pre class="hljs"><code>' + html + '</code></pre>';
                } catch (e) {
                }
            }
            let html = str;
            if (lang && typeof beautifyList[lang] !== "undefined") {
                html = beautifyList[lang](html);
            } else if (lang !== 'none') {
                html = beautifyList['javascript'](html);
            }
            html = md.utils.escapeHtml(html, beautifyOpt);
            return '<pre class="hljs"><code>' + html + '</code></pre>';
        }
    });
    export default {
        name: "ezm-markdown",
        components: {},
        props: {},
        data() {
            return {
                html: '',
                circleTimeout: '',
                lastSlots: '',
                circleStart: false
            }
        },
        mounted() {
            this.$nextTick(() => {
                if (process.env.NODE_ENV === 'development' && !this.circleStart) {
                    this.circleWatchSlotsChange();
                } else {
                    this.reload();
                }
            });
        },
        activated() {
        },
        beforeRouteLeave() {
            clearTimeout(this.circleTimeout);
        },
        methods: {
            reload() {
                let t = VNodeUtil.renderArrayToHtmlText(this.$slots.default);
                this.html = md.render(this.trimLine(t));
            },

            trimLine(text) {
                text = text.replace(/\[space\]/ig, '&nbsp;');
                text = text.replace(/\[table\]/ig, '&nbsp;'.repeat(4));
                text = text.replace(/\[br\]/ig, '\n');
                let ts = text.split('\n');
                let isCode = false;
                let first = null;
                return ts.map(x => {
                    if (/```/.test(x)) {
                        x = x.trim();
                        isCode = !isCode;
                        if (!isCode) first = null;
                    } else {
                        if (!isCode) {
                            x = x.trim();
                        } else {
                            if (first === null) first = this.getSNumber(x);
                            let reg = new RegExp(`^\\s{${first}}`);
                            x = x.replace(reg, '');
                        }
                    }

                    return x;
                }).join('\n');
            },
            getSNumber(t) {
                let sum = 0;
                t = [...t];
                for (let i = 0; i < t.length; i++) {
                    if (t[i] === ' ') sum++;
                    else return sum;
                }
            },
            circleWatchSlotsChange() {
                this.circleStart = true;
                let j = JSON.stringify(this.$slots);
                if (j !== this.lastSlots) {
                    this.reload();
                }
                this.lastSlots = j;
                this.circleTimeout = setTimeout(this.circleWatchSlotsChange, 1000);


            }
        },
        watch: {
            slotsDefaultJson() {
                this.reload();
            }
        },
        computed: {
            slotsDefaultJson() {
                return JSON.stringify(this.$slots);
            }
        },
    }
</script>

<style lang="less" scoped>

    .ezm-markdown {
        line-height: 2;
        font-size: 10.3pt
    }
</style>