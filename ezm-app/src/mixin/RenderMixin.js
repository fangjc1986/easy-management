import {isArray, isObject} from "xe-utils/dist/xe-utils";
import Vue from 'vue';
import EzmTextInput from "@/components/editor/ezm-text-input";
import {DomUtil} from "@/utils/common.util";


export default {
    methods: {
        onInput(v) {
            if (this.renderComp.outputFormatter) {
                v = this.renderComp.outputFormatter(v);
            }
            this.$emit('input', v);
        },
    },
    computed: {
        valueComp() {
            if (this.renderComp.inputFormatter) {
                return this.renderComp.inputFormatter(this.value);
            }
            return this.value;
        },
        nameComp() {
            if (isObject(this.renderComp.name)) {
                Vue.component(this.renderComp.name.name, this.renderComp.name);
                return this.renderComp.name.name;
            }
            return this.renderComp.name;
        },
        renderOptions() {
            let baseProps = {};
            if (isObject(this.renderComp.name)) {

            }
            let placeholder = '请输入' + this.field.label;
            if (this.isFilter && this.renderComp.props) {
                placeholder = "全部";
            }
            return {
                style: {
                    'width': DomUtil.styleSize(this.width) || '100%',
                },
                props: {
                    disabled: this.disabled,
                    value: this.valueComp,
                    placeholder: placeholder,
                    ...(this.renderComp.props || {})
                },
                attrs: {
                    disabled: this.disabled,
                    placeholder: placeholder,
                    options: this.renderComp.options || {},
                    ...(this.renderComp.attrs || {})
                },
                on: {
                    input: this.onInput,
                    ...(this.renderComp.on || {})
                },
            };
        },
        renderSelectedOptions() {
            let options = this.renderComp.options;
            if (!options || !isArray(options)) return [];
            return options.map(x => {
                return this.$createElement("ElOption", {
                    props: {
                        value: x.value,
                        label: x.label,
                    }
                })
            });

        }

    }
}