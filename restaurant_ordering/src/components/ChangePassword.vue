<template>

    <div>
        <div class="change-password">
            <el-form :model="form" :rules="rules" ref="changePasswordForm" label-width="120px" class="demo-ruleForm">
                <el-form-item label="当前密码" prop="currentPassword">
                    <el-input type="password" v-model="form.currentPassword" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="newPassword">
                    <el-input type="password" v-model="form.newPassword" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="confirmPassword">
                    <el-input type="password" v-model="form.confirmPassword" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('changePasswordForm')">提交</el-button>
                    <el-button @click="resetForm('changePasswordForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import { updatePassword } from '@/api/AdminAPI';
export default {
    data() {
        const validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            }
            else if (value.length < 6) {
                callback(new Error('密码长度不能小于6位'));
            }
            else {
                callback();
            }
        };

        const validateConfirmPass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.form.newPassword) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };

        return {
            form: {
                currentPassword: '',
                newPassword: '',
                confirmPassword: ''
            },
            id: '',
            password: '',
            userType: '',
            rules: {
                currentPassword: [
                    { validator: validatePass, trigger: 'blur' }
                ],
                newPassword: [
                    { validator: validatePass, trigger: 'blur' }
                ],
                confirmPassword: [
                    { validator: validateConfirmPass, trigger: 'blur' }
                ],
            },
        };
    },
    methods: {
        async submitForm(formName) {
            if (this.password != this.form.currentPassword) {
                this.$message.error("原密码错误!")
                return;
            }
            this.$refs[formName].validate(async (valid) => {
                if (valid) {
                    let { data: res } = await updatePassword(this.userType, this.id, this.form.newPassword)
                    if (res.success) {
                        this.$message.success("密码修改成功!")
                        this.password = this.form.newPassword
                        if (this.userType == 'admin') {
                            let a = JSON.parse(localStorage.getItem("admin"))
                            a.password = this.form.newPassword
                            localStorage.setItem("admin", JSON.stringify(a));
                        }
                        if (this.userType == 'user') {
                            let a = JSON.parse(localStorage.getItem("user"))
                            a.password = this.form.newPassword
                            localStorage.setItem("user", JSON.stringify(a));
                        }
                        this.$refs[formName].resetFields();
                    } else {
                        this.$message.error(res.msg)
                    }
                } else {
                    return false;
                }
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
    },
    async created() {
        this.userType = localStorage.getItem("usertype")
        if (this.userType == 'admin') {
            let a = JSON.parse(localStorage.getItem("admin"))
            this.password = a.password
            this.id = a.adminId
        }
        if (this.userType == 'user') {
            let a = JSON.parse(localStorage.getItem("user"))
            this.password = a.password
            this.id = a.userId
        }
    }
};
</script>

<style scoped>
.change-password {
    width: 500px;
    height: 300px;
    margin: 15% auto;
}
</style>