<template>
    <div class="layout-middle-tab-bar mt-sm mlr-sm flex-box"
         onselectstart="return false"
    >
        <div class="flex-grow pos-r" style="width: 0;">
            <el-tabs :value="activeTabsName"
                     @input="(v)=>$store.dispatch('tabs/setActiveTabsName',v)"
                     type="card"
                     ref="menuBlock"
                     class="body-tab-menu"
                     @tab-remove="(v)=>$store.dispatch('tabs/closeTabs',v)">
                <el-tab-pane label="首页" name="/system/index" :closable="false"></el-tab-pane>
                <el-tab-pane
                    closable
                    v-for="(item, index) in showTabs"
                    :key="item.path"
                    :label="item.meta.title"
                    :name="item.path"
                    :ref="`tab_${index+1}`"
                >
                </el-tab-pane>
            </el-tabs>
            <el-dropdown @command="handleCommand2" trigger="click" placement="bottom"
                         style="position:absolute;width: 1px;height: 0;bottom: 5px;" :style="tabRightStyle"
                         @visible-change="(v)=>rightMenuShow=v"
            >
                <div class="pos-a" ref="dropdownRight"></div>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="now">
                        <i class="iconfont icon-dot-circle1"></i>
                        关闭当前页
                    </el-dropdown-item>
                    <el-dropdown-item command="other">
                        <i class="iconfont icon-window-maximize"></i>
                        关闭其他
                    </el-dropdown-item>
                    <el-dropdown-item command="all">
                        <i class="iconfont icon-window-restore1"></i>
                        关闭所有
                    </el-dropdown-item>
                    <el-dropdown-item command="left">
                        <i class="iconfont icon-hand-point-left1"></i>
                        关闭左侧
                    </el-dropdown-item>
                    <el-dropdown-item command="right">
                        <i class="iconfont icon-hand-point-right1"></i>
                        关闭右侧
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
        <div class="flex-shk-off text-center flex-box opt-border">
            <div class="flex-shk-off flex-box flex-center-all cur-p" title="关闭其他"
                 @click="handleCommand('other')"
            >
                <i class="iconfont icon-times"></i>
            </div>
            <div class="flex-shk-off cur-p">
                <el-dropdown @command="handleCommand" placement="bottom-end" class="wh100p">
                    <div class="wh100p flex-box flex-center-all">
                        <i class="iconfont icon-caret-down"></i>
                    </div>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="now">
                            <i class="iconfont icon-dot-circle1"></i>
                            关闭当前页
                        </el-dropdown-item>
                        <el-dropdown-item command="other">
                            <i class="iconfont icon-window-maximize"></i>
                            关闭其他
                        </el-dropdown-item>
                        <el-dropdown-item command="all">
                            <i class="iconfont icon-window-restore1"></i>
                            关闭所有
                        </el-dropdown-item>
                        <el-dropdown-item command="left">
                            <i class="iconfont icon-hand-point-left1"></i>
                            关闭左侧
                        </el-dropdown-item>
                        <el-dropdown-item command="right">
                            <i class="iconfont icon-hand-point-right1"></i>
                            关闭右侧
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>
    </div>
</template>

<script>
    import {mapState} from "vuex";


    export default {
        name: 'layout-middle-tab-bar',
        mixins: [],
        components: {},
        props: {},
        data() {
            return {
                rightTarget: null,
                tabsScrollerContainer: null,
                rightMenuShow: false,
                tableScrollLeft: 0,
            }
        },
        computed: {
            ...mapState('tabs', ['showTabs', 'activeTabsName']),
            tabRightStyle() {
                return {
                    left: this.rightTarget ? this.rightTarget.offsetLeft - this.tableScrollLeft + this.rightTarget.offsetWidth / 2 + 10 + 'px' : 0,
                }
            }
        },
        watch: {
            activeTabsName(v) {
                if (v === this.$route.path) {
                    return;
                }
                if (v === '/system/index') {
                    this.$router.push({path: '/system/index'});
                } else {
                    let t = this.showTabs.find(x => x.path === v);
                    this.$router.push({path: t.path, query: t.query});
                }
            },
            showTabs() {
                this.resetListener();
            },
        },
        created() {
        },
        mounted() {
            this.$store.dispatch("tabs/pushTo", this.$route);
            this.resetListener();
        },
        destroyed() {
        },
        methods: {
            resetListener() {
                setTimeout(() => {
                    let tabs = this.$refs.menuBlock.$el.querySelectorAll('.el-tabs__item');
                    tabs.forEach(x => x.oncontextmenu = this.rightClickTabs)
                });
            },
            rightClickTabs(e) {
                if (!(e.target.id + '').includes('tab-')) return false;
                this.rightTarget = e.target;
                this.tabsScrollerContainer = this.$refs.menuBlock.$el.querySelector(".el-tabs__nav-scroll > .el-tabs__nav");
                this.arrow = this.$refs.menuBlock.$el.querySelector(".el-tabs__nav-prev");
                let res = this.tabsScrollerContainer.style.transform.match(/\d+/);
                this.tableScrollLeft = res[0] * 1 - (this.arrow ? 20 : 0);
                let path = this.$refs.dropdownRight.click();
                setTimeout(() => {
                    if (!this.rightMenuShow) this.$refs.dropdownRight.click();
                }, 100);
                return false;
            },
            handleCommand(cmd) {
                this.$store.dispatch("tabs/closeByCmd", cmd);
            },
            handleCommand2(cmd) {
                if (!['all'].includes(cmd)) this.rightTarget.click();
                setTimeout(() => this.handleCommand(cmd));
            },

        }
    };
</script>

<style scoped lang="less">
    .layout-middle-tab-bar {

    }

    @borderColor: #E4E7ED;
    @radius: 0;

    .opt-border {
        border: 1px solid @borderColor;
        border-top-left-radius: @radius;
        border-top-right-radius: @radius;

        > div {
            font-size: 12px;
            width: 30px;

            &:not(:first-of-type) {
                border-left: 1px solid @borderColor;
            }
        }
    }
</style>