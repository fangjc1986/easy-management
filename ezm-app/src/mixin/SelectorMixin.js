export default {
    props: {
        value: {
            default: '',
        },
        canEdit: {
            default: false,
        },
        needAjax: {
            default: false,
        },
        fieldValue: {
            default: 'id',
        },
        fieldShow: {
            default: 'id',
        },
        selectType: {
            default: 'radio',
        },
        defaultValue: {
            default: '',
        },
        defaultShow: {
            default: ''
        },
        disabled: {
            default: false,
        },
        placeholder:{
            default: '',
        },
        width:{
            default: 900,
        },
        defaultFilterForm:{
            default: null,
        },
        defaultFilterLocked:{
            default: false,
        }
    },
    data() {
        return {
            show: false,
            listMethod: null,
            showText: '',
            selectorPageInstance: {},
            instance: this,
        }
    },
    mounted() {
        this.getValueInfo();
    },
    watch:{
        value(v){
            this.showText = v;
        }
    },
    computed: {
        showTextComp() {
            if (this.showText) return this.showText;
            if (this.value === this.defaultValue) return this.defaultShow;
            return this.value;
        },
        titleComp() {
            return '选择 ' + this.selectorPageInstance.pageName;
        },

    },
    methods: {
        onInput(v) {
            if (this.canEdit) {
                this.$emit("input", v);
                this.showText = v;
            }
        },
        getValueInfo() {
            if (!this.listMethod && !this.needAjax) return;
            let params = {};
            params[this.fieldValue] = this.value || "---";
            params.size = 1;
            this.listMethod(params).then(resp => {
                if (!resp.records.length) return;
                this.showText = resp.records[0][this.fieldShow];
            });
        },
        clickInput() {
            if (this.canEdit) return;
            this.show = true;
        },
        clickButton() {
            this.show = true;
        },
        selectorConfirm(records) {
            this.$emit('on-change', records);
            if (!records.length) {
                this.$emit('input', this.defaultValue);
                this.showText = this.defaultShow;
            } else if (this.selectType === 'radio') {
                this.showText = records[0][this.fieldShow];
                this.$emit('input', records[0][this.fieldValue]);
            } else return this.$emit('input', records.map(x => {
                this.showText = x[this.fieldShow];
                return x[this.fieldValue];
            }).join(','));
            this.show = false;
        }
    },
}
















