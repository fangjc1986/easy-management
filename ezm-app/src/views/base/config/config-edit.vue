<template>
    <div class="ezm-edit-container">
        <ezm-window
            :icon="iconComp"
            :title="titleComp"
            :show-close="showCloseButton"
            @on-click-close="onClickClose"
            :show-tabs="showTabsComp"
        >
            <el-form
                ref="form"
                label-position="top"
                :model="pageInstance.editForm"
                class="ezm-edit-form plr-xs ptb-sm"
                :rules="pageInstance.rules"
            >
                <ezm-window-item
                    v-for="tab in tabsComp"
                    :title="tab"
                    :key="tab"
                >

                    <el-row>
                        <el-col
                            v-for="field in fieldsComp"
                            :key="field.prop"
                            v-if="field.editGroup === tab && field.editRender"
                            class="edit-form-col plr-xs"
                            :sm="getCustomEditSpan(field,'sm')"
                            :md="getCustomEditSpan(field,'md')"
                            :lg="getCustomEditSpan(field,'lg')"
                            :xl="getCustomEditSpan(field,'xl')"
                            :xs="getCustomEditSpan(field,'xs')"
                        >
                            <el-form-item
                                :prop="field.prop"
                                :label="field.label"
                            >
                                <template slot="label" v-if="field.editRemarkText">
                                    {{ field.label }}
                                    <el-tooltip class="item" effect="dark" :content="field.editRemarkText" placement="top">
                                        <i class="el-icon-warning cur-p" style="font-size: 1.1em;"></i>
                                    </el-tooltip>
                                </template>
                                <render-edit-input
                                    :width="field.editWidth || ''"
                                    :field="field"
                                    v-model="pageInstance.editForm[field.prop]"
                                    :disabled="getInputDisabled(field)"
                                ></render-edit-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </ezm-window-item>

            </el-form>

            <template slot="button-left">
                <ezm-edit-buttons
                    :page-instance="pageInstance"
                    :edit-instance="this"
                ></ezm-edit-buttons>
            </template>
        </ezm-window>

    </div>
</template>

<script>
    import EzmWindow from "@/components/window/ezm-window";
    import EzmWindowItem from "@/components/window/ezm-window-item";
    import EditMixin from "@/mixin/EditMixin";
    import RenderEditInput from "@/components/render/render-edit-input";
    import EzmEditButtons from "@/components/editor/ezm-edit-buttons";

    export default {
        name: 'config-edit',
        mixins: [EditMixin],
        components: {EzmEditButtons, RenderEditInput, EzmWindowItem, EzmWindow},
        props: {},
        data() {
            return {}
        },
        computed: {},
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
    .config-edit {
    }
</style>