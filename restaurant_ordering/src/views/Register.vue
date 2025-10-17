<template>
    <div id="box">
        <div class="card">
            <div class="title">注册</div>
            <!-- 注册输入框 -->
            <div class="wave-group" id="input1">
                <input required type="text" class="input" v-model="registerForm.username">
                <span class="bar"></span>
                <label class="label">
                    <span class="label-char" style="--index: 0">用</span>
                    <span class="label-char" style="--index: 1">户</span>
                    <span class="label-char" style="--index: 2">名</span>
                </label>
            </div>

            <div class="wave-group" id="input2">
                <input required type="password" class="input" v-model="registerForm.password">
                <span class="bar"></span>
                <label class="label">
                    <span class="label-char" style="--index: 0">密</span>
                    <span class="label-char" style="--index: 1">码</span>
                </label>
            </div>

            <div class="wave-group" id="input3">
                <input required type="password" class="input" v-model="registerForm.repassword">
                <span class="bar"></span>
                <label class="label">
                    <span class="label-char" style="--index: 0">确</span>
                    <span class="label-char" style="--index: 1">认</span>
                    <span class="label-char" style="--index: 2">密</span>
                    <span class="label-char" style="--index: 3">码</span>
                </label>
            </div>

            <div class="wave-group" id="input4">
                <input required type="text" class="input" v-model="registerForm.name">
                <span class="bar"></span>
                <label class="label">
                    <span class="label-char" style="--index: 0">姓</span>
                    <span class="label-char" style="--index: 1">名</span>
                </label>
            </div>

            <div class="wave-group" id="input5">
                <input required type="text" class="input" v-model="registerForm.phone">
                <span class="bar"></span>
                <label class="label">
                    <span class="label-char" style="--index: 0">电</span>
                    <span class="label-char" style="--index: 1">话</span>
                </label>
            </div>

            <div class="radio-button-container">
                <div class="radio-button">
                    <input type="radio" class="radio-button__input" id="radio1" name="radio-group" value="1"
                        v-model="registerForm.sex">
                    <label class="radio-button__label" for="radio1">
                        <span class="radio-button__custom"></span>
                        男
                    </label>
                </div>
                <div class="radio-button">
                    <input type="radio" class="radio-button__input" id="radio2" name="radio-group" value="2"
                        v-model="registerForm.sex">
                    <label class="radio-button__label" for="radio2">
                        <span class="radio-button__custom"></span>
                        女
                    </label>
                </div>
            </div>

            <button @click="register">注&nbsp;&nbsp;&nbsp;&nbsp;册</button>
            <div class="toLogin" @click="toLogin">已有账号？去登录</div>

        </div>
    </div>
</template>

<script>
import { register } from '@/api/UserAPI';
export default {
    name: 'CampusRecruitmentRegister',

    data() {
        return {
            registerForm: {
                username: '',
                password: '',
                repassword: '',
                name: '',
                phone: '',
                sex: '',
                avatar: ''
            }
        };
    },

    mounted() {

    },

    methods: {
        toLogin() {
            this.$router.push('/login')
        },
        async register() {
            let a = this.registerForm
            if (a.username == '') {
                this.$message.error("用户名不能为空!")
                return;
            }
            if (a.username.length > 12 || a.username.length < 6) {
                this.$message.error("用户名长度只能在6~12!")
                return;
            }
            if (a.password == '') {
                this.$message.error("密码不能为空!")
                return;
            }
            if (a.password.length > 12 || a.password.length < 6) {
                this.$message.error("密码长度只能在6~12!")
                return;
            }
            if (a.repassword != a.password) {
                this.$message.error("两次密码不一致!")
                return;
            }
            if (a.name == '') {
                this.$message.error("姓名不能为空!")
                return;
            }
            if (a.phone == '') {
                this.$message.error("电话不能为空!")
                return;
            }
            if (a.phone.length != 11) {
                this.$message.error("电话格式不正确!")
                return;
            }
            if (a.sex == '') {
                this.$message.error("请选择性别!")
                return;
            }
            let { data: res } = await register(a)
            if (res.success) {
                this.$message.success("注册成功请登录!")
                this.$router.push('/login')
                return
            } else {
                this.$message.error(res.message)
            }
        }
    },
};
</script>
<style scoped>
#box {
    background: url("@/assets/orderingBG.jpg") no-repeat center center fixed;
    width: 100%;
    height: 100%;
    position: fixed;
    background-size: 100% 100%;
}

.card {
    width: 650px;
    height: 600px;
    border-radius: 30px;
    background: rgba(224, 224, 224, 0.4);
    /* box-shadow: 15px 15px 30px #bebebe,
        -15px -15px 30px #ffffff; */
    margin: 150px auto;
    position: relative;
}

.title {
    position: absolute;
    top: 60px;
    font-size: 30px;
    font-weight: 900;
    left: 285px;
    letter-spacing: 20px;
}

/* 输入框css样式 */
#input1,
#input2,
#input3,
#input4,
#input5,
#input6 {
    left: 175px;
}

#input1 {
    top: 120px;
}

#input2 {
    top: 180px;
}

#input3 {
    top: 240px;
}

#input4 {
    top: 300px;
}

#input5 {
    top: 360px;
}

#input6 {
    top: 420px;
}

.wave-group {
    position: absolute;
}

.wave-group .input {
    font-size: 16px;
    padding: 10px 10px 10px 5px;
    display: block;
    width: 300px;
    border: none;
    border-bottom: 1px solid #515151;
    background: transparent;
}

.wave-group .input:focus {
    outline: none;
}

.wave-group .label {
    color: #424040;
    font-size: 18px;
    font-weight: normal;
    position: absolute;
    pointer-events: none;
    left: 5px;
    top: 10px;
    display: flex;
}

.wave-group .label-char {
    transition: 0.2s ease all;
    transition-delay: calc(var(--index) * .05s);
}

.wave-group .input:focus~label .label-char,
.wave-group .input:valid~label .label-char {
    transform: translateY(-20px);
    font-size: 14px;
    color: #ffffff;
}

.wave-group .bar {
    position: relative;
    display: block;
    width: 300px;
}

.wave-group .bar:before,
.wave-group .bar:after {
    content: '';
    height: 2px;
    width: 0;
    bottom: 1px;
    position: absolute;
    background: #ffffff;
    transition: 0.2s ease all;
    -moz-transition: 0.2s ease all;
    -webkit-transition: 0.2s ease all;
}

.wave-group .bar:before {
    left: 50%;
}

.wave-group .bar:after {
    right: 50%;
}

.wave-group .input:focus~.bar:before,
.wave-group .input:focus~.bar:after {
    width: 50%;
}


.radio-button-container {
    display: flex;
    align-items: center;
    gap: 24px;
    position: absolute;
    top: 420px;
    left: 260px;
}

.radio-button {
    display: inline-block;
    position: relative;
    cursor: pointer;
}

.radio-button__input {
    position: absolute;
    opacity: 0;
    width: 0;
    height: 0;
}

.radio-button__label {
    display: inline-block;
    padding-left: 30px;
    margin-bottom: 10px;
    position: relative;
    font-size: 15px;
    color: #201e1e;
    font-weight: 600;
    cursor: pointer;
    text-transform: uppercase;
    transition: all 0.3s ease;
}

.radio-button__custom {
    position: absolute;
    top: 0;
    left: 0;
    width: 20px;
    height: 20px;
    border-radius: 50%;
    border: 2px solid #555;
    transition: all 0.3s ease;
}

.radio-button__input:checked+.radio-button__label .radio-button__custom {
    background-color: #ffffff;
    border-color: transparent;
    transform: scale(0.8);
    box-shadow: 0 0 20px #ffffff;
}

.radio-button__label:hover .radio-button__custom {
    transform: scale(1.2);
    border-color: #ffffff;
    box-shadow: 0 0 20px #ffffff;
}

button:hover {
    background-color: #F37C57;
    color: hsl(0, 0%, 100%);
    box-shadow: rgb(243, 124, 87) 0px 7px 29px 0px;
}

button:active {
    background-color: #F37C57;
    color: hsl(0, 0%, 100%);
    box-shadow: rgb(243, 124, 87) 0px 0px 0px 0px;
    transition: 100ms;
}

button {
    width: 200px;
    padding: 17px 40px;
    border-radius: 50px;
    cursor: pointer;
    border: 0;
    background-color: white;
    box-shadow: rgb(0 0 0 / 5%) 0 0 8px;
    letter-spacing: 1.5px;
    text-transform: uppercase;
    font-size: 15px;
    transition: all 0.5s ease;
    position: absolute;
    top: 480px;
    left: 225px;
    font-weight: 600;
    font-size: 17px;
}

.toLogin {
    position: absolute;
    top: 540px;
    left: 255px;
    font-weight: 600;
    font-size: 17px;
    cursor: pointer;
    color: #6b6969;
}
</style>