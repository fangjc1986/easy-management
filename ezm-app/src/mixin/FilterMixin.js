import {getFilterFieldName} from "@/utils/element.util";

export default {
    props: {
        pageInstance: Object,
    },
    data() {
        return {}
    },
    computed: {
        fieldsComp() {
            return this.pageInstance.fields.concat().sort((a, b) => {
                return (a.filterRank || 0) - (b.filterRank || 0);
            })
        }
    },
    methods: {
        getFieldName(field) {
            let params = field.params || {};
            return params.filterField || field.prop;
        },
        getInputDisabled(field) {
            if( field.filterDisabled ) return true;
            return this.pageInstance.defaultFilterLocked && Object.keys( this.pageInstance.defaultFilterForm ).includes(this.getFieldName(field));
        }

    },
}