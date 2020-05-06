<template>
    <el-select
        :value="valueComp"
        :placeholder="placeholder"
        :multiple-limit="multipleLimit"
        :multiple="multiple"
        :filterable="filterable"
        :loading-text="loadingText"
        collapse-tags
        @input="onInput"
    >
        <el-option
            v-for="item in list"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        ></el-option>
    </el-select>
</template>

<script>
    import {TypeUtil} from "@/utils/common.util";
    import ReloadMixin from "@/mixin/ReloadMixin";
    import {dictDetailGetList} from "@/api/base/dict_detail";

    export default {
        name: 'ezm-dict-selector',
        mixins: [ReloadMixin],
        components: {},
        props: {
            value: {
                default: "",
            },
            placeholder: {
                default: '请选择'
            },
            multipleLimit: {
                default: 0,
            },
            multiple: {
                default: false,
            },
            filterable: {
                default: false,
            },
            loadingText: {
                default: "加载选项中..."
            },
            fieldValue: {
                default: 'code',
            },
            fieldShow: {
                default: 'name',
            },
            defaultValue: {
                default: '',
            },
            defaultShow: {
                default: '全部',
            },
            showDefault: {
                default: false,
            },
            options: {
                default: () => [],
            },
            dictCode: {
                default: '',
            },
            isNumberValue: {
                default: false,
            },
        },
        data() {
            return {
                list: [],
            }
        },
        computed: {
            valueComp() {
                if (!this.multiple) return this.value || this.defaultValue;
                if (TypeUtil.isEmpty(this.value)) return [];
                if (TypeUtil.isString(this.value)) return this.value.split(',').map(x => {
                    if (this.isNumberValue) {
                        try {
                            return x * 1;
                        } catch (e) {
                        }
                    }
                    return x;
                });
                return this.value;
            }
        },
        watch: {},
        created() {


        },
        mounted() {
        },
        destroyed() {
        },
        methods: {
            reload() {
                let def = [];
                if (this.showDefault) {
                    def = [{
                        value: this.defaultValue,
                        label: this.defaultShow,
                    }];
                }
                dictDetailGetList({
                    size: 999,
                    dictCode: this.dictCode
                }).then(resp => {
                    this.list = def.concat(resp.records.sort((a, b) => (a.rank || 1) - (b.rank || 1)).map(x => {
                        x.label = x.code + '-' + x.name;
                        x.value = x.code;
                        return x;
                    }));
                })
            },
            onInput(v) {
                this.$emit('input', v);
            }
        }
    };
</script>

<style scoped lang="less">
    .ezm-dict-selector {

    }
</style>