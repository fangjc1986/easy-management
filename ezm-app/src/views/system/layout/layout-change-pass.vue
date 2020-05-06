<template>
    <div class="layout-change-pass">
        <el-form
            ref="form"
            :rules="rules"
            :model="form"
            label-width="80px">
            <el-form-item label="密码" prop="password">
                <el-input type="password" v-model="form.password"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
                <el-input type="password" v-model="form.newPassword"></el-input>
            </el-form-item>
            <el-form-item label="重复密码" prop="rePassword">
                <el-input type="password" v-model="form.rePassword"></el-input>
            </el-form-item>
        </el-form>
        <div class="text-right">
            <el-button
                :loading="loading"
                type="primary"
                @click="submit"
                icon="iconfont icon-save"
            >保存密码
            </el-button>
        </div>
    </div>
</template>

<script>
    import {formValidate} from "@/utils/element.util";
    import {accessChangePass} from "@/api/sys/access";
    import {mapState} from "vuex";
    import {DialogUtil} from "@/utils/common.util";

    export default {
        name: 'layout-change-pass',
        mixins: [],
        components: {},
        props: {
            visible: {
                default: false,
            },
        },
        data() {
            return {
                loading: false,
                form: {
                    id: '',
                    password: '',
                    newPassword: '',
                    rePassword: '',
                },
                rules: {
                    password: [
                        {required: true, message: '密码不能为空'},
                        {min: 6, max: 40, message: '6-40字符之间'},
                    ],
                    newPassword: [
                        {required: true, message: '密码不能为空'},
                        {min: 6, max: 40, message: '6-40字符之间'},
                    ],
                    rePassword: [
                        {min: 6, max: 40, message: '6-40字符之间'},
                        {
                            validator: (rule, value, cb) => {
                                if (this.form.newPassword !== value) return cb(new Error("两次密码输入不一致"));
                                return cb();
                            }
                        }
                    ],
                }
            }
        },
        computed: {
            ...mapState('user', ['loginUser']),
        },
        watch: {},
        created() {
        },
        mounted() {
        },
        destroyed() {
        },
        methods: {
            closeDialog() {
                this.$emit('update:visible', false);
            },
            submit() {
                formValidate(this.$refs.form).then(() => {
                    this.loading = true;
                    this.form.id = this.loginUser.id;
                    accessChangePass(this.form).then(resp => {
                        DialogUtil.toast("密码修改成功！");
                        this.closeDialog();
                    }).finally(() => this.loading = false);
                });
            }
        }
    };
</script>

<style scoped lang="less">
    .layout-change-pass {

    }
</style>