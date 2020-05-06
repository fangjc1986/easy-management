<template>
    <div class="ezm-window box-card-shadow" :class="flexShow ? 'flex-box flex-column flex-grow' : ''">
        <div class="flex-box flex-center title-bar flex-shk-off">
            <div class="title one-line">
                <i class="pr-xs" :class="icon" v-if="icon"></i>
                <span class="font-xs">{{ title }}</span>
            </div>
            <slot name="title-right"></slot>
            <div class="title-menu-container flex-box " v-if="showTabs">
                <div class="title-menu-item"
                     v-for="(item, i) in tabs"
                     :key="item._uid + '_title-menu-item'"
                     :class="item.title === activeTabsName ? 'is-active' : '' "
                     @click="clickMenuItem(item)"
                >
                    <i class="mr-xxs" :class="item.icon" v-if="item.icon"></i>
                    <span>{{ item.title}}</span>
                </div>
            </div>
            <slot name="menu-right"></slot>
            <div class="flex-grow h100p " :class="flexShow? '' : 'cur-p'" @click="clickBar"></div>
            <div class="button-left-container flex-shk-off">
                <slot name="button-left"></slot>
            </div>
            <div class="close-button" v-if="showClose"
                 @click="clickCloseButton"
            >
                <i class="el-icon-close"></i>
            </div>
        </div>
        <div class="body-container" :style="bodyStyle"
             :class=" [trans ? 'body-trans' : '', flexShow ? 'flex-box flex-column flex-grow' : '']"
        >
            <div class="pos-r ma-1px" :class="[containerClass, flexShow? 'flex-box flex-column flex-grow' : '']"
                 ref="bodyInnerContainer">
                <slot></slot>
            </div>
        </div>
    </div>
</template>

<script>
    import ScrollMixin from "@/mixin/ScrollMixin";
    import RenderItem from "@/components/window/render-item";

    export default {
        name: 'ezm-window',
        mixins: [ScrollMixin],
        components: {RenderItem},
        props: {
            // 关闭按钮
            showClose: {
                default: false
            },
            value: {
                default: '',
            },
            expand: {
                default: true
            },
            maxHeight: {
                default: 'auto'
            },
            icon: {
                default: 'el-icon-folder',
            },
            showTabs: {
                default: false,
            },
            title: {
                default: "标题",
            },
            containerClass: {
                default: "",
            },
            flexShow: {
                default: false,
            }
        },
        data() {
            return {
                tabs: [],
                activeTabsName: this.value,
                expandSelf: this.expand,
                height: 'auto',
                scrollTimeout: null,
                lastScrollTop: {},
                zeroHeight: '0',
                trans: false,
            }
        },
        computed: {
            bodyStyle() {
                return {
                    'height': this.height,
                }
            }
        },
        watch: {
            activeTabsName(v) {
                this.$emit('input', v);
                this.tabs.forEach(x => {
                    x.setShow(x.title === v);
                });
            },
            value(v) {
                this.activeTabsName = v;
            },
            expandSelf(v) {
                this.resetHeight();
            },
        },
        created() {
        },
        mounted() {
        },
        destroyed() {
        },
        activated() {
        },
        methods: {
            setShowTab() {
                this.tabs.forEach(x => {
                    x.setShow(x.title === this.activeTabsName);
                });
            },
            clearTabs(vue) {
                let index = this.tabs.findIndex(x => x === vue);
                if (index < 0) return;
                this.tabs.splice(index, 1);
            },
            pushTabs(vue) {
                if (!this.tabs.find(x => x === vue)) {
                    this.tabs.push(vue);
                }
            },
            clickCloseButton() {
                this.$emit('on-click-close');
            },

            clickMenuItem(item) {
                this.activeTabsName = item.title;
            },
            clickBar() {
                this.expandSelf = !this.expandSelf;
            },
            resetHeight() {
                if (this.flexShow) return this.height = '100%';
                clearTimeout(this.scrollTimeout);
                let h = this.$refs.bodyInnerContainer.offsetHeight;
                h = h > this.maxHeight ? this.maxHeight : h;
                if (!this.expandSelf) {
                    this.height = h + 3 + 'px';
                }
                setTimeout(() => {
                    this.height = this.expandSelf ? (h + 3 + 'px') : '0';
                }, 20);
                this.scrollTimeout = setTimeout(() => {
                    this.height = this.expandSelf ? 'auto' : '0';
                }, 400);
            },
            reload() {
                this.expandSelf = this.expand;
                this.activeTabsName = this.activeTabsName || this.tabs[0].title;
                this.setShowTab();
                this.trans = false;
                setTimeout(() => {
                    this.resetHeight();
                    setTimeout(() => this.trans = true, 200);
                });
            },

        }
    };
</script>

<style scoped lang="less">
    .ezm-window {
    }

    .title-bar {
        border: 1px solid #E4E7ED;
        height: 30px;
        padding-left: 10px;
        padding-right: 1px;

        .title {
            padding-right: 10px;

            .iconfont {

            }
        }

        .close-button {
            @closeHeight: 26px;
            height: @closeHeight;
            width: @closeHeight;
            line-height: @closeHeight;
            text-align: center;
            background-color: #ff4d7d;
            color: #ffff;
            cursor: pointer;
            margin-left: 1px;
            transition: all ease .2s;

            &:hover {
                opacity: .7;
            }
        }

        .title-menu-container {
            justify-self: flex-end;
            align-self: flex-end;
            position: relative;
            bottom: -1px;

            .title-menu-item {
                font-size: 13px;
                border-top: 1px solid #E4E7ED;
                border-right: 1px solid #E4E7ED;
                padding: 3px 10px;
                border-bottom: 1px solid #E4E7ED;
                background-color: #ffffff;
                cursor: pointer;

                &:not(.is-active) {
                    background-color: #f0f0f0;

                    &:hover {
                        background-color: #ffffff;
                    }
                }

                &:first-of-type {
                    border-left: 1px solid #E4E7ED;
                }

                &.is-active {
                    border-bottom: 1px solid #ffffff;
                }

                > i.iconfont {

                }
            }
        }
    }

    .body-container {
        position: relative;
        box-sizing: border-box !important;

        border-bottom: 1px solid #E4E7ED;
        border-right: 1px solid #E4E7ED;
        border-left: 1px solid #E4E7ED;


        &.body-trans {
            transition: height ease .4s;
        }

        overflow: hidden;

        &:not(.shrink).fff {
            overflow: auto;

            &::-webkit-scrollbar {
                background-color: transparent;
                width: 5px;
            }

            &::-webkit-scrollbar-thumb {
                background-color: rgba(120, 120, 120, .6);
                border-radius: 3px;
            }

            &::-webkit-scrollbar-corner {
                background-color: transparent;
                height: 0;
            }
        }
    }


</style>