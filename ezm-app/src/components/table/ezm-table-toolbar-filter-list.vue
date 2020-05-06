<template>
    <el-popover
        placement="bottom-end"
        trigger="hover"
    >
        <el-button icon="el-icon-s-operation" slot="reference" title="列筛选" circle></el-button>
        <div class="list-scroll-container">
            <div class=""
                 v-for="column in columns"
                 :key="column.id"
                 v-if="!excludedTypes.includes(column.type)"
            >
                <el-checkbox v-model="column.visible" :label="column.title" @click.native="clickCheckbox"></el-checkbox>
            </div>
        </div>
    </el-popover>
</template>

<script>
    export default {
        name: 'ezm-table-toolbar-filter-list',
        mixins: [],
        components: {},
        props: {
            ezmTable: Object,
        },
        data() {
            return {
                columns: [],
                table: null,
                excludedTypes: ['checkbox', 'seq', 'radio'],
            }
        },
        computed: {},
        watch: {},
        created() {
        },
        mounted() {
            this.table = this.ezmTable.$refs.table;
            setTimeout(() => {
                this.columns = this.table.getColumns();
            });
        },
        destroyed() {
        },
        methods: {
            clickCheckbox() {
                setTimeout(() => {
                    this.table.refreshColumn();
                });
            }
        }
    };
</script>

<style scoped lang="less">
    .ezm-table-toolbar-filter-list {

    }

    .list-scroll-container {
        max-height: 300px;
        overflow: auto;
    }
</style>