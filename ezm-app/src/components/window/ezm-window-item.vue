<template>
    <div class="ezm-window-item"
         v-show="show"
         :style="{'max-height' : maxHeight + 'px'}"
         :class="$parent.flexShow ? 'flex-box flex-column flex-grow' : ''"
         @scroll="scroll">
        <slot></slot>
    </div>
</template>
<script>
    import ReloadMixin from "@/mixin/ReloadMixin";
    import {createUUID} from "@/utils/common.util";

    export default {
        name: 'ezm-window-item',
        mixins: [ReloadMixin],
        components: {},
        props: {
            title: {
                default: createUUID(),
            },
            icon: {
                default: '',
            },
        },
        data() {
            return {
                show: false,
                maxHeight: 0,
                lastScrollTop: 0,
            }
        },
        computed: {},
        watch: {},
        created() {
            // let p = this.$parent;
            // let i = 10;
            // while (i-- > 0) {
            //     if( !p ) break;
            //     if( p.$options._componentTag === 'ezm-window') {
            //         p.pushTabs(this);
            //         this.maxHeight = p.maxHeight;
            //         break
            //     }
            //     p = p.$parent;
            // }
            //
            let p = this.getParent();
            p.pushTabs(this);
            this.maxHeight = p.maxHeight;

        },
        mounted() {
        },
        destroyed() {
            this.getParent().clearTabs(this);
        },
        methods: {
            getParent(){
                let p = this.$parent;
                let i = 10;
                while (i-- > 0) {
                    if( !p ) break;
                    if( p.$options._componentTag === 'ezm-window') {
                        return p;
                    }
                    p = p.$parent;
                }
            },
            setShow(v) {
                this.show = v;
            },

            scroll(e) {
                this.lastScrollTop = e.target.scrollTop;
            },

            reload() {
                this.$el.scrollTo(0, 0);
            },
            tabLoad() {
                setTimeout(() => {
                    this.$el.scrollTo(0, this.lastScrollTop || 0);
                });
            },
        },
    };
</script>

<style scoped lang="less">
    .ezm-window-item {
        overflow-y: auto;
        overflow-x: hidden;

        &::-webkit-scrollbar {
            background-color: transparent;
            width: 5px;
        }

        &::-webkit-scrollbar-thumb {
            background-color: rgba(0, 0, 0, .4);
            border-radius: 3px;
        }

        &::-webkit-scrollbar-corner {
            background-color: transparent;
            height: 0;
        }
    }
</style>