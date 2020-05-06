<template>
    <div class="ezm-edit-buttons">
        <el-button-group class="mr-1px">
            <el-button
                icon="el-icon-arrow-left"
                :loading="pageInstance.loading"
                @click="pageInstance.setShowEditWindow(false)"
                v-if="editInstance.showBackButton"
            >{{ editInstance.backButtonText }}
            </el-button>
        </el-button-group>
        <slot name="back-button-right"></slot>
        <el-button-group class="mr-1px">
            <el-button
                icon="el-icon-refresh-left"
                :loading="pageInstance.loading"
                v-if="editInstance.showClearButton"
                @click="pageInstance.resetEditForm"
            >{{ editInstance.clearButtonText }}
            </el-button>
        </el-button-group>
        <slot name="clear-button-right"></slot>
        <el-button-group>
            <el-button
                icon=" el-icon-plus"
                :loading="pageInstance.loading"
                v-if="editInstance.showAddButton"
                @click="editInstance.clickAdd"
            >{{ editInstance.addButtonText }}
            </el-button>
            <el-button
                class="ml-xxxs"
                icon=" el-icon-circle-check"
                :loading="pageInstance.loading"
                @click="editInstance.clickSave"
                v-if="pageInstance.editForm.id && editInstance.showSaveButton"
            >{{ editInstance.saveButtonText }}
            </el-button>
        </el-button-group>
    </div>
</template>

<script>
    export default {
        name: 'ezm-edit-buttons',
        mixins: [],
        components: {},
        props: {
            pageInstance: Object,
            editInstance: Object,
        },
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
    .ezm-edit-buttons {

    }
</style>