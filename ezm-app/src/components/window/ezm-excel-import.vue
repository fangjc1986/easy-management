<template>
    <ezm-dialog
        :value="value"
        @input="(v) => $emit('input', v)"
        width="400"
    >
        <ezm-window :title="title" icon="iconfont icon-file-excel">
            <ezm-window-item class="plr-sm pb-sm pt-md">
                <el-form>
                    <el-form-item label="选择文件">
                        <input type="file"
                               ref="fileInput"
                               accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                               @change="fileOnChange"
                        />
                    </el-form-item>
                    <el-form-item label="下载模板" v-if="templateUrl">
                        <a :href="templateUrl" target="_blank">点击下载</a>
                    </el-form-item>
                </el-form>
            </ezm-window-item>
            <template slot="button-left">
                <el-button-group>
                    <el-button
                        icon="iconfont icon-share-square"
                        :loading="loading"
                        :disabled="!file"
                        @click="clickImportButton"
                    >确认导入
                    </el-button>
                </el-button-group>
            </template>
        </ezm-window>
    </ezm-dialog>
</template>

<script>
    import EzmEditWindow from "@/components/editor/container/ezm-edit-window";
    import EzmWindow from "@/components/window/ezm-window";
    import EzmWindowItem from "@/components/window/ezm-window-item";
    import EzmEditDialog from "@/components/editor/container/ezm-edit-dialog";
    import {DialogUtil, FileUtil} from "@/utils/common.util";
    import EzmDialog from "@/components/display/ezm-dialog";

    export default {
        name: 'ezm-excel-import',
        mixins: [],
        components: {EzmDialog, EzmEditDialog, EzmWindowItem, EzmWindow, EzmEditWindow},
        props: {
            value: {
                default: false,
            },
            title: {
                default: 'EXCEL导入'
            },
            templateUrl: {
                default: ''
            },
            loading: {
                default: false,
            },
            limitSize: {
                default: '5m',
            }
        },
        data() {
            return {
                file: null,
            }
        },
        computed: {},
        watch: {
            value(v) {
            }
        },
        created() {
        },
        mounted() {
        },
        destroyed() {
        },
        methods: {
            clickImportButton() {
                if (!this.file) return DialogUtil.toast("请选择 EXCEL 文件！", 'warning');
                this.$emit('on-click-import', this.file);
            },
            fileOnChange(e) {
                this.file = e.target.files[0];
                if (this.file.size > FileUtil.sizeString2Byte(this.limitSize)) {
                    e.target.value = '';
                    this.file = null;
                    return DialogUtil.toast("文件尺寸不能大于 " + this.limitSize, "error");
                }
            }
        }
    };
</script>

<style scoped lang="less">
    .ezm-excel-import {

    }
</style>