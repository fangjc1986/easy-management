<template>
    <div class="login pos-r ">
        <div class="" style="height: 100px;"></div>
        <div class="login-window box-card-shadow pa-md pos-r" style="z-index: 2">
            <div class="title text-center mb-md flex-box flex-center-all">
                <div class="mr-sm">
                    <i class="iconfont icon-vuejs" style="color: #7eceb2;font-size: 2em;"></i>
                </div>
                <div class="">
                    {{ title }}
                </div>
            </div>
            <div class="">
                <el-form
                    size="medium"
                    :rules="rules"
                    :model="form"
                    ref="form"
                >
                    <el-row>
                        <el-col>
                            <el-form-item prop="userName">
                                <el-input v-model="form.userName">
                                    <i class="iconfont icon-user" slot="prepend"></i>
                                </el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col>
                            <el-form-item prop="password">
                                <el-input type="password" v-model="form.password" @keypress.enter.native="submit">
                                    <i class="iconfont icon-lock" slot="prepend"></i>
                                </el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col>
                            <el-form-item class="" prop="authCode">
                                <el-input v-model="form.authCode" class="image-code" @keypress.enter.native="submit">
                                    <img :src="imageCodeUrl" class="cur-p" slot="append" @click="clickImg"/>
                                </el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>

                <el-button
                    size="medium"
                    type="primary"
                    style="width: 100%;"
                    :loading="loading"
                    @click="submit"
                >
                    登录
                </el-button>
            </div>
        </div>
        <div class="pos-a flex-box flex-center-all time-bg"
             onselectstart="return false;"
             style="cursor: default"
        >
            {{ time }}
        </div>
    </div>
</template>

<script>

    import dayjs from "dayjs";
    import setting from "@/setting";
    import {signInRequest} from "@/api/sys/sign";

    export default {
        name: 'login',
        mixins: [],
        components: {},
        props: {},
        data() {
            return {
                loading: false,
                form: {
                    userName: 'admin',
                    password: '123456',
                    imageCode: '',
                },
                rules: {
                    userName: [
                        {required: true, message: '用户名不能为空!'},
                    ],
                    password: [
                        {required: true, message: '密码不能为空!'},
                    ],
                    authCode: [
                        {required: true, message: '图片验证码不能为空！'},
                    ],
                },
                urlTail: Math.random(),
                time: "",
                tOut: null,
                title: process.env.VUE_APP_LOGIN_TITLE,
            }
        },
        computed: {
            imageCodeUrl() {
                let domain = setting.serverBaseUrl;
                return domain + '/common/image-code/getLoginCode?' + this.urlTail;
            }
        },
        watch: {},
        created() {
        },
        mounted() {
            this.timeCircle();
            this.$store.commit("user/clearUser");
            this.$store.commit("route/clearRoute");
            this.$store.dispatch("tabs/clearTabs");
        },
        destroyed() {
            clearTimeout(this.tOut);
        },
        activated() {

        },
        methods: {
            timeCircle() {
                this.time = dayjs().format('HH:mm:ss');
                this.tOut = setTimeout(() => {
                    this.timeCircle();
                }, 1000);
            },
            clickImg() {
                this.urlTail = Math.random();
            },

            submit() {
                this.loading = true;
                signInRequest(this.form).then(resp => {
                    this.$store.commit("user/setUser", resp);
                    this.$router.push({path: '/system/index'});
                }).catch(() => {
                    this.clickImg();
                    this.loading = false;
                });
            },
        }
    };
</script>

<style scoped lang="less">
    .login {
        background-color: #eef5fc;
    }

    .login-window {
        width: 270px;
        background-color: #fff;
        margin-left: auto;
        margin-right: auto;
    }

    .title {
        font-weight: bold;
    }

    .time-bg {
        color: #e8e8e8;
        opacity: .7;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        font-size: 300px;
    }
</style>