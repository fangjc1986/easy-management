<template>
    <div class="ezm-froala-editor pos-r">
        <div ref="container">
        </div>
    </div>
</template>

<script>
    import FroalaEditor from "froala-editor";
    import "froala-editor/js/plugins.pkgd.min"
    import 'froala-editor/js/languages/zh_cn';
    import {cookiesUtil} from "@/utils/common.util";
    import "froala-editor/css/froala_editor.pkgd.css";

    const toolbar = {
        'moreText': {
            'buttons': ['bold', 'italic', 'underline', 'strikeThrough', 'subscript', 'superscript', 'fontFamily', 'fontSize', 'textColor', 'backgroundColor', 'inlineClass', 'inlineStyle', 'clearFormatting']
        },
        'moreParagraph': {
            'buttons': ['alignLeft', 'alignCenter', 'formatOLSimple', 'alignRight', 'alignJustify', 'formatOL', 'formatUL', 'paragraphFormat', 'paragraphStyle', 'lineHeight', 'outdent', 'indent', 'quote']
        },
        'moreRich': {
            'buttons': ['insertLink', 'insertImage', 'insertVideo', 'insertFile', 'imageInfo', 'insertTable', 'emoticons', 'fontAwesome', 'specialCharacters', 'embedly', 'insertHR'],
            'buttonsVisible': 10
        },
        'moreMisc': {
            'buttons': ['undo', 'redo', 'fullscreen', 'print', 'getPDF', 'spellChecker', 'selectAll', 'html', 'help'],
            'align': 'right',
            'buttonsVisible': 3
        }
    };

    export default {
        name: 'ezm-froala-editor',
        mixins: [],
        components: {},
        props: {
            value: '',
            placeholder: {
                default: '请输入您的内容!'
            },
            height: {
                default: 500,
            },
            zIndex: {
                default: 100,
            },
            disabled: {
                default: false,
            }
        },
        data() {
            return {
                editor: null,
                content: '',
            }
        },
        computed: {},
        watch: {
            disabled(v){
                if( !this.editor ) return;
                if( v ){
                    this.editor.edit.off();
                } else {
                    this.editor.edit.on();
                }
            }
        },
        created() {
        },
        mounted() {
            this.editor = new FroalaEditor(this.$refs.container, {
                language: 'zh_cn',
                toolbarButtons: toolbar,
                placeholderText: this.placeholder,
                height: this.height,
                zIndex: this.zIndex,
                attribution: true,
                // imageEditButtons: ['imageDisplay', 'imageAlign', 'imageInfo', 'imageRemove'],
                imageUploadParams: {
                    token: cookiesUtil.get("token"),
                    bizCode: 1,
                    image: 1,
                },
                imageUploadURL: "/api/ezm-server/common/upload/froala",
                imageMaxSize: 1024 * 1024 * 5,
                imageDefaultWidth: 0,
                imageManagerDeleteParams: {
                    token: cookiesUtil.get("token"),
                },
                imageManagerDeleteURL: "/api/ezm-server/common/upload/froalaImageDelete",
                imageManagerLoadParams: {
                    token: cookiesUtil.get("token"),
                },
                imageManagerLoadURL: "/api/ezm-server/common/upload/froalaImageList",
                fileMaxSize: 1024 * 1024 * 20,
                fileUploadParams: {
                    token: cookiesUtil.get("token"),
                    bizCode: 1,
                    image: 0,
                },
                fileUploadURL: "/api/ezm-server/common/upload/froala",
                videoMaxSize: 1024 * 1024 * 50,
                videoUploadParams: {
                    token: cookiesUtil.get("token"),
                    bizCode: 1,
                    image: 1,
                },
                videoUploadURL: "/api/ezm-server/common/upload/froala",
                events: {
                    'contentChanged': () => {
                        // Do something here.
                        // this is the editor instance.
                        this.$emit('input', this.editor.html.get());
                    },
                    'initialized': () => {
                        // Do something here.
                        // this is the editor instance.
                        this.editor.html.set(this.value);
                    },
                }
            });
            setTimeout(()=>{
                if( this.disabled ){
                    this.editor.edit.off();
                }
            })
        },
        destroyed() {
            this.editor = null;
        },
        methods: {
            reload() {

            },
            onEditorFocus() {

            },
            onEditorBlur() {

            },
            onEditorChange() {

            }
        }
    };
</script>

<style scoped lang="less">
    .ezm-froala-editor {
    }
</style>