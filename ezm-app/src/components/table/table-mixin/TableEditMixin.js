export default {
    props: {},
    data() {
        return {
            lastScrollPos: {
                scrollLeft: 0,
                scrollTop: 0,
            }
        }
    },
    methods: {
        revertRow(row) {
            return this.$refs.table.revertData(row);
        },
        reloadRow(row) {
            return this.$refs.table.reloadRow(row, row);
        },
        currentChange({row}) {
        },
        reloadData() {
            // let scroll = this.lastScrollPos;
            // this.$refs.table.reloadData(this.pageInstance.paginate.records).then(() => {
            //     this.scrollToLast(scroll);
            // });
            this.refreshThenScroll();
        },
        refreshThenScroll() {
            let scroll = this.lastScrollPos;
            this.pageInstance.getList(() => this.scrollToLast(scroll));
        },
        tableScroll(e) {
            this.lastScrollPos = e;
        },
        scrollToLast(scroll = null) {
            let s = scroll || this.lastScrollPos;
            this.$refs.table.scrollTo(s.scrollLeft, s.scrollTop);
        }
    }
}