import elementResizeDetectorMaker from 'element-resize-detector'
import {debThr} from "@/utils/common.util";

export default {
    props: {
        minHeight: {
            default: '300px',
        },
        height: {
            default: '300px'
        }
    },
    data() {
        return {
            resize_detector: null,
            resize_tableHeight: '0',
        }
    },
    mounted() {
        this.resize_detector = elementResizeDetectorMaker();
        this.startTableHeightDetector();
    },
    deactivated() {
        this.resize_detector.removeListener(this.$refs.tableContainer, this.tableHeightDetectorListener);
    },
    methods: {
        startTableHeightDetector() {
            this.resize_detector.listenTo(this.$refs.tableContainer, this.tableHeightDetectorListener);
        },
        tableHeightDetectorListener(ele) {
            debThr.thrAndDeb(this._uid+'tableHeightDetectorListener', ()=>{
                this.resize_tableHeight = ele.offsetHeight + 'px';
                this.$refs.table.recalculate();
            });
        },
    }
}