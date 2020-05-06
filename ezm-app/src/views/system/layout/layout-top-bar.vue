<template>
    <div class="layout-top-bar flex-box flex-center pl-md pr-xs h100p">
        <div class=" menu-icon" :class="$store.state.layout.leftShrink ? 'shrink' : ''">
            <i class="bar-icon iconfont icon-indent"
               @click="clickLeftMenu"
            ></i>
        </div>
        <div class="layout-top-breadcrumb ml-sm">
            <transition name="fade-right">
                <el-breadcrumb separator="/" v-if="showBreadCrumb">
                    <el-breadcrumb-item></el-breadcrumb-item>
                    <el-breadcrumb-item
                        v-for="item in breadcrumbList"
                        :key="item"
                    >{{item}}
                    </el-breadcrumb-item>
                </el-breadcrumb>
            </transition>
        </div>

        <div class="flex-grow"></div>

        <div class="flex-shk-off layout-top-search-page">
            <el-autocomplete
                popper-class="my-autocomplete"
                v-model="state"
                :fetch-suggestions="querySearch"
                placeholder="查找页面"
                @select="handleSelect">
                <i
                    class="el-icon-search el-input__icon"
                    slot="prefix"
                    @click="handleIconClick">
                </i>
                <template slot-scope="{ item }">
                    <div class="mtb-xxs">
                        <div class="name"><i class="iconfont mr-xs" :class="item.icon"></i>{{ item.meta.title }}</div>
                        <div class="addr">{{ item.path }}</div>
                    </div>
                </template>
            </el-autocomplete>
        </div>

        <div class="flex-shk-off ml-sm">
            <el-dropdown trigger="click">
                <div class="el-dropdown-link cur-p" style="font-size: 12px;">
                    <i class="iconfont icon-user"></i>
                    您好，{{ loginUser.nickName || loginUser.userName }}
                    <template v-if="loginUser.nickName">({{loginUser.userName}})</template>
                    <i class="el-icon-arrow-down el-icon--right"></i>
                </div>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item icon="el-icon-lock" @click.native="clickChangePass">修改密码</el-dropdown-item>
                    <el-dropdown-item icon="el-icon-switch-button" @click.native="clickSignOut">退出登录</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
        <div class="flex-shk-off mlr-xs">
            <theme-color-picker/>
        </div>

        <div class="flex-shk-off mlr-xs">
            <el-select
                :value="tenantId"
                @input="(v)=> $store.commit('tenant/setTenantId',v)"
                placeholder="隔离组（组合）"
            >
                <el-option
                    v-for="item in tenantList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                />
            </el-select>
        </div>

        <ezm-edit-dialog
            v-model="showChangePassDlg"
            width="300px"
        >
            <ezm-window title="修改密码" icon="iconfont icon-lock">
                <ezm-window-item class="pa-sm">
                    <layout-change-pass
                        :visible.sync="showChangePassDlg"
                    />
                </ezm-window-item>
            </ezm-window>
        </ezm-edit-dialog>

    </div>
</template>

<script>
    import {mapState} from "vuex";
    import ThemeColorPicker from "@/components/theme/theme-color-picker";
    import LayoutChangePass from "@/views/system/layout/layout-change-pass";
    import {treeTrace, XeUtil} from "@/utils/common.util";
    import EzmEditDialog from "@/components/editor/container/ezm-edit-dialog";
    import EzmWindow from "@/components/window/ezm-window";
    import EzmWindowItem from "@/components/window/ezm-window-item";
    let pinyin = require("pinyin");


    export default {
        name: 'layout-top-bar',
        mixins: [],
        components: {EzmWindowItem, EzmWindow, EzmEditDialog, LayoutChangePass, ThemeColorPicker},
        props: {},
        data() {
            return {
                showChangePassDlg: false,
                breadcrumbList: [],
                showBreadCrumb: true,
                showBreadCrumbTimer: null,
                restaurants: [],
                state: ''
            }
        },
        computed: {
            ...mapState('layout', ['leftShrink']),
            ...mapState('user', ['loginUser']),
            ...mapState('tenant', ['tenantId', 'tenantList']),
            ...mapState('route', ['menuTree', 'routeAuthList']),
            ...mapState('tabs', ['activeTabsName']),
        },
        watch: {
            activeTabsName(v) {
                this.changeBreadCrumb(v);
            }
        },
        created() {
        },
        mounted() {
            this.changeBreadCrumb(this.activeTabsName);
        },
        destroyed() {
        },
        methods: {
            querySearch(queryString, cb) {
                // 调用 callback 返回建议列表的数据
                cb(this.routeAuthList.filter(r => {
                    if (r.type !== 1 || r.children) return false;
                    if( r.meta.title.indexOf(queryString) !== -1 || r.path.indexOf(queryString) !== -1 ){
                        return true;
                    }
                    let py = pinyin(r.meta.title,{style:pinyin.STYLE_FIRST_LETTER});
                    py = XeUtil.flatten(py).join('');
                    return py.indexOf(queryString.toLowerCase()) !== -1;
                }));
            },
            handleSelect(item) {
                this.$store.dispatch('tabs/pushTo', item);
                setTimeout(() => this.state = "");
            },
            handleIconClick(ev) {
            },
            changeBreadCrumb(v) {
                clearTimeout(this.showBreadCrumbTimer);
                this.showBreadCrumb = false;
                this.showBreadCrumbTimer = setTimeout(() => {
                    let trace = treeTrace(this.menuTree, (x) => x.path === v, 'meta');
                    this.breadcrumbList = trace.map(x => x.title);
                    this.showBreadCrumb = true;
                }, 400);
            },
            clickLeftMenu() {
                this.$store.dispatch("layout/setLeftShrink", !this.leftShrink);
            },
            clickChangePass() {
                this.showChangePassDlg = true;
            },
            clickSignOut() {
                this.$store.commit("user/signOut");

            }
        }
    };
</script>

<style scoped lang="less">
    .bar-icon {
        font-size: 16px;
        cursor: pointer;
    }

    .menu-icon {
        transform: rotateY(180deg);
        transition: all ease .4s;

        &.shrink {
            transform: rotateY(0deg);
        }
    }

    .my-autocomplete {
        li {
            line-height: normal;

            .name {
                text-overflow: ellipsis;
                overflow: hidden;
                line-height: 14px;
            }

            .addr {
                font-size: 12px;
                color: #b4b4b4;
            }

            .highlighted .addr {
                color: #ddd;
            }
        }
    }
</style>