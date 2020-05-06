<template>
    <el-cascader
        :value="valueComp"
        :options="tree"
        :props="props"
        collapse-tags
        clearable
        :placeholder="placeholder"
        ref="cascader"
        :disabled="disabled"

        @input="(v)=>$emit('input',v)"
        @change="onChange"
    />
</template>

<script>
    import ReloadMixin from "@/mixin/ReloadMixin";
    import {list2Tree, listMerge2Tree, TypeUtil} from "@/utils/common.util";

    export default {
        name: 'ezm-tree-selector',
        mixins: [ReloadMixin],
        components: {},
        props: {
            value: {
                default: '',
            },
            disabled:{
                default: false,
            },
            options: {
                default: () => [],
            },
            listMethods: {
                default: () => [],
            },
            placeholder: {
                default: '请选择'
            },
            filterable: {
                default: true
            },
            showDefault: {
                default: false,
            },
            fieldValue: {
                default: () => ['id'],
            },
            fieldShow: {
                default: () => ['name'],
            },
            fieldPid: {
                default: () => ['pid'],
            },
            defaultValue: {
                default: ''
            },
            defaultShow: {
                default: '全部',
            },
            multiple: {
                default: false,
            },
            checkStrictly: {
                default: false,
            },
            emitPath: {
                default: true,
            },
            isNumberValue: {
                default: false,
            },
            focusMultiPromise: {
                default: false,
            }
        },
        data() {
            return {
                tree: [],
            }
        },
        computed: {
            props() {
                return {
                    multiple: this.multiple,
                    checkStrictly: this.checkStrictly,
                    emitPath: this.emitPath,
                }
            },
            valueComp() {
                if (!this.multiple) return this.value || this.defaultValue;
                if (TypeUtil.isEmpty(this.value)) return [];
                if (TypeUtil.isString(this.value) || TypeUtil.isNumber(this.value)) return (this.value + '').split(',').map(x => {
                    if (this.isNumberValue) {
                        try {
                            return x * 1;
                        } catch (e) {
                        }
                    }
                    return x;
                });
                return this.value;
            },
        },
        watch: {
        },
        created() {
        },
        mounted() {
        },
        destroyed() {
        },
        methods: {
            reload() {
                const def = this.showDefault ? [{
                    label: this.defaultShow,
                    value: this.defaultValue,
                }] : [];
                if (this.listMethods.length > 1 || this.focusMultiPromise) {
                    this.treeMultiplePromise(def);
                } else if (this.listMethods.length) {
                    this.treePid(def);
                } else {
                    this.tree = def.concat(this.options);
                }
            },
            treeMultiplePromise(def = []) {
                let promiseList = this.listMethods.map(getList => {
                    return getList({size: 999}).then(resp => {
                        return resp.records.map(x => {
                            x.value = x[this.fieldValue[0]];
                            x.label = x[this.fieldShow[0]];
                            return x;
                        });
                    })
                });
                Promise.all(promiseList).then(lists => {
                    for (let i = lists.length - 2; i >= 0; i--) {
                        lists[i] = listMerge2Tree(lists[i], lists[i + 1], this.fieldValue[i], this.fieldPid[i]);
                    }
                    this.tree = def.concat(lists[0]);
                });
            },
            treePid(def = []) {
                this.listMethods[0]({
                    size: 999,
                }).then(resp => {
                    let list = resp.records.map(x => {
                        x.value = x[this.fieldValue[0]];
                        x.label = x[this.fieldShow[0]];
                        return x;
                    });
                    this.tree = def.concat(list2Tree(list, "0", {
                        pidKey: this.fieldPid[0],
                        idKey: this.fieldValue[0]
                    }));
                })
            },
            onChange(a, b) {
                this.$emit('on-change', this.$refs.cascader.getCheckedNodes());
            }
        }
    };
</script>

<style scoped lang="less">
    .ezm-tree-selector {

    }
</style>