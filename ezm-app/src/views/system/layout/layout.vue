<template>
    <div class="layout wh100p overflow-h flex-box">
        <div class="flex-box flex-column left-container flex-shk-off"
             :class="$store.state.layout.leftShrink ? 'shrink' : ''"
        >
            <div class="left-top flex-shk-off text-center white flex-box flex-center-all">
                <i class="iconfont icon-vuejs" style="color: #7eceb2;font-size: 2em;"></i>
                <span class="ml-sm" v-if="!$store.state.layout.leftShrink">{{ LEFT_TOP_TITLE }}</span>
            </div>
            <div class="left-menu flex-grow" style="height: 0;">
                <happy-scroll
                    color="rgba(100,100,100, .4)" size="5" resize
                    hide-horizontal
                >
                    <layout-left-menu></layout-left-menu>
                </happy-scroll>
            </div>
        </div>
        <div class="flex-box flex-grow flex-column ">
            <div class="top-bar flex-shk-off box-shadow">
                <layout-top-bar></layout-top-bar>
            </div>
            <div class="middle-tab-bar flex-shk-off">
                <layout-middle-tab-bar></layout-middle-tab-bar>
            </div>
            <div class="body flex-grow pos-r overflow-h " ref="bodyContainer">
                <transition name="fade">
                    <keep-alive class="">
                        <router-view class="page-router-view flex-box flex-column pos-r pa-sm h100p"></router-view>
                    </keep-alive>
                </transition>
            </div>
        </div>
    </div>
</template>

<script>

    import LayoutMiddleTabBar from "@/views/system/layout/layout-middle-tab-bar";
    import {mapState} from "vuex";
    import LayoutLeftMenu from "@/views/system/layout/layout-left-menu";
    import LayoutTopBar from "@/views/system/layout/layout-top-bar";
    import elementResizeDetectorMaker from "element-resize-detector";
    import {debThr, DomUtil} from "@/utils/common.util";

    export default {
        name: 'layout',
        mixins: [],
        components: {LayoutTopBar, LayoutLeftMenu, LayoutMiddleTabBar},
        props: {},
        data() {
            return {
                LEFT_TOP_TITLE: process.env.VUE_APP_TOP_TITLE,
                LEFT_TOP_TITLE_S: process.env.VUE_APP_TOP_TITLE_S,
                resize_detector: null,
            }
        },
        computed: {
            ...mapState('route', ['menuTree']),
        },
        watch: {},
        created() {
        },
        mounted() {
            this.resize_detector = elementResizeDetectorMaker();
            this.startTableHeightDetector();
            this.$store.dispatch("layout/setBodyPosition", DomUtil.getElementPosition(this.$refs.bodyContainer));
        },
        destroyed() {
            this.resize_detector.removeListener(this.$refs.bodyContainer, this.bodyChange);
        },
        methods: {
            startTableHeightDetector() {
                this.resize_detector.listenTo(this.$refs.bodyContainer, this.bodyChange);
            },
            bodyChange(ele) {
                debThr.thrAndDeb(this._uid + 'tableHeightDetectorListener', () => {
                    this.$store.dispatch("layout/setBodyPosition", DomUtil.getElementPosition(ele));
                });
            },
        }
    };
</script>

<style scoped lang="less">

    @menuLeftBgColor: #25476a;
    .layout {
        /*background-color: #ECF0F5;*/
        background: #ffffff;
    }

    @topBarHeight: 50px;

    .left-top {
        height: @topBarHeight;
        flex-wrap: nowrap;
        white-space: nowrap;
        overflow: hidden;
        line-height: @topBarHeight;
        color: #fff;
    }

    .left-container {
        background-color: #263238;
        width: 260px;
        transition: all ease .4s;
        overflow: hidden;
        box-shadow: 1px 3px 3px #ccc;
        z-index: 10;
        margin-right: 2px;

        &.shrink {
            width: 60px;
        }

        .left-menu {
            /*overflow: auto;*/

            /*&::-webkit-scrollbar {*/
            /*    background-color: transparent;*/
            /*    width: 5px;*/
            /*}*/

            /*&::-webkit-scrollbar-thumb {*/
            /*    background-color: rgba(255, 255, 255, .2);*/
            /*    border-radius: 3px;*/
            /*}*/

            /*&::-webkit-scrollbar-corner {*/
            /*    background-color: transparent;*/
            /*    height: 0;*/
            /*}*/
        }
    }


    .page-router-view {
        position: absolute;
        box-sizing: border-box;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        overflow-y: auto;
        overflow-x: hidden;
        background-color: #ffffff;

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

    .top-bar {
        height: @topBarHeight;
    }

    .body {

    }


</style>