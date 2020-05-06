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

    export default {
        name: 'ezm-options-selector',
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
                default: 'id',
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
            listMethod: {
                default: null,
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
                if (TypeUtil.isString(this.value) || TypeUtil.isNumber(this.value)) return (this.value+'').split(',').map(x => {
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
        watch: {
            options(v){
                if( !this.listMethod ){
                    this.reload();
                }
            }
        },
        created() {

        },
        mounted() {
        },
        destroyed() {
        },
        methods: {
            reload(){
                let def = [];
                if (this.showDefault) {
                    def = [{
                        value: this.defaultValue,
                        label: this.defaultShow,
                    }];
                }
                if (this.listMethod) {
                    this.listMethod({
                        current: 1,
                        size: 999,
                    }).then(resp => {
                        let l = resp.records;
                        let res = l.map(r => {
                            r.label = r[this.fieldShow];
                            r.value = r[this.fieldValue];
                            return r;
                        });
                        this.list = def.concat(res);
                    })
                } else {
                    this.list = def.concat(this.options);
                }
            },
            onInput(v){
                this.$emit('input',v);
            }
        }
    };
</script>

<style scoped lang="less">
    .ezm-options-selector {

    }
</style>