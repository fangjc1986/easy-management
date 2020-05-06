<template>
    <div class="ezm-edit-window pos-a flex-box flex-column flex-center flex-grow"
         :class="windowClass"
         :style="windowStyle"
         v-if="valueComp || value"
         @click="maskClick"
    >
        <transition name="fade-top">
            <div class="pos-r content pa-1px bg-white bg-aqua"
                 :style="contentStyle"
                 style="box-sizing: border-box"
                 v-if="value && valueComp"
                 @click="(e)=>e.stopPropagation()"
            >
                <div class="wh100p pa-xs scroll-y-thin bg-white"
                     style="box-sizing: border-box;">
                    <slot></slot>
                </div>
                <div class="close-button"
                     @click="$emit('input',false)"
                >
                    <i class="iconfont icon-times"></i>
                </div>
            </div>
        </transition>
    </div>
</template>

<script>
    import ReloadMixin from "@/mixin/ReloadMixin";
    import {DomUtil} from "@/utils/common.util";
    import {mapState} from "vuex";

    export default {
        name: 'ezm-edit-window',
        mixins: [ReloadMixin],
        components: {},
        props: {
            top: {
                default: '10%',
            },
            height: {
                default: 'auto',
            },
            width: {
                default: '95%',
            },
            value: {
                default: false,
            },
            clickMaskClose: {
                default: false,
            },
            maxHeight: {
                default: "90%"
            },
            minHeight: {
                default: '0'
            },
            fixed: {
                default: false,
            }
        },
        data() {
            return {
                valueComp: this.value,
                closeClass: true,
                zIndex: 0,
            }
        },
        computed: {
            ...mapState('layout', ['dialogZIndex']),
            contentStyle() {
                return {
                    'top': DomUtil.styleSize(this.top),
                    'height': DomUtil.styleSize(this.height),
                    'width': DomUtil.styleSize(this.width),
                    'max-height': DomUtil.styleSize(this.maxHeight),
                    'min-height': DomUtil.styleSize(this.minHeight),
                }
            },
            windowStyle() {
                return {
                    'z-index': this.zIndex,
                }
            },
            windowClass() {
                return {
                    close: !(this.valueComp && this.value),
                    fixed: this.fixed,
                }
            }
        },
        watch: {
            value(v) {
                if (v) {
                    setTimeout(() => this.valueComp = this.value);
                    this.zIndex = this.dialogZIndex;
                    this.$store.dispatch("layout/incDialogZIndex");
                } else {
                    setTimeout(() => {
                        this.valueComp = this.value;
                    }, 400);
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
            unload() {
                this.$emit('input', false);

            },
            maskClick() {
                if (this.clickMaskClose) {
                    this.$emit('input', false);
                }
            }
        }
    };
</script>

<style scoped lang="less">
    .ezm-edit-window {
        transition: background-color .4s ease;
        background-color: rgba(255, 255, 255, .7);

        &.close {
            background-color: rgba(255, 255, 255, 0);
        }

        top: 0;
        left: 0;
        right: 0;
        bottom: 0;

        &.fixed {
            position: fixed;

        }

    }

    .content {
        border: 1px solid #e7e1cd;
        box-shadow: 0 1px 5px #e7e1cd;
    }

    .ezm-edit-window {

    }

    .close-button {
        position: absolute;
        @size: 20px;
        @thmColor: #ff2165;
        height: 20px;
        width: 20px;
        border: 1px solid @thmColor;
        color: #ffffff;
        background-color: @thmColor;
        cursor: pointer;
        line-height: 20px;
        text-align: center;
        border-radius: 50%;
        right: -@size/2;
        top: -@size/2;
        transition: ease all .4s;
        box-shadow: 0 0 5px @thmColor;
        z-index: 101;

        &:hover {
            transform: scale(1.2);
        }
    }
</style>