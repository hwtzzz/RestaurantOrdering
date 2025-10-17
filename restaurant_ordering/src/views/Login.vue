<template>
  <div id="box">

    <div class="card">

      <div class="title">
        餐厅点餐系统
      </div>

      <div class="wave-group" id="input1">
        <input required type="text" class="input" v-model="loginForm.username">
        <span class="bar"></span>
        <label class="label">
          <span class="label-char" style="--index: 0">用</span>
          <span class="label-char" style="--index: 1">户</span>
          <span class="label-char" style="--index: 2">名</span>
        </label>
      </div>

      <div class="wave-group" id="input2">
        <input required type="password" class="input" v-model="loginForm.password">
        <span class="bar"></span>
        <label class="label">
          <span class="label-char" style="--index: 0">密</span>
          <span class="label-char" style="--index: 1">码</span>
        </label>
      </div>

      <div class="radio-button-container">
        <div class="radio-button">
          <input type="radio" class="radio-button__input" v-model="loginForm.role" id="radio1" name="radio-group"
            value="user">
          <label class="radio-button__label" for="radio1">
            <span class="radio-button__custom"></span>
            顾客
          </label>
        </div>
        <div class="radio-button">
          <input type="radio" class="radio-button__input" v-model="loginForm.role" id="radio3" name="radio-group"
            value="admin">
          <label class="radio-button__label" for="radio3">
            <span class="radio-button__custom"></span>
            管理员
          </label>
        </div>
      </div>


      <button @click="login">登录</button>
      <div class="toRegister" @click="toRegister">没有账号？去注册</div>



    </div>

  </div>
</template>

<script>
import { userLogin } from '@/api/UserAPI';
import { adminLogin } from '@/api/AdminAPI';
export default {
  name: 'CampusRecruitmentLogin',

  data() {
    return {
      loginForm: {
        username: '',
        password: '',
        role: ''
      }
    };
  },

  mounted() {

  },

  methods: {
    toRegister() {
      this.$router.push('/register')
    },
    async login() {
      let a = this.loginForm
      if (a.username == '') {
        this.$message.error("请输入用户名")
        return;
      }
      if (a.password == '') {
        this.$message.error("请输入密码")
        return;
      }
      if (a.role == '') {
        this.$message.error("请选择角色")
        return;
      }
      //用户登录
      if (a.role == 'user') {
        let { data: res } = await userLogin(this.loginForm.username, this.loginForm.password)
        if (res.success) {
          this.$router.push('/userHome')
          localStorage.setItem("user", JSON.stringify(res.result));
          localStorage.setItem("usertype", a.role);
          return;
        } else {
          this.$message.error(res.msg)
        }
      }
      //管理员
      if (a.role == 'admin') {
        let { data: res } = await adminLogin(this.loginForm.username, this.loginForm.password)
        if (res.success) {
          localStorage.setItem("admin", JSON.stringify(res.result));
          localStorage.setItem("usertype", a.role);
          this.$router.push('/adminHome')
          return;
        } else {
          this.$message.error(res.msg)
          return;
        }
      }
    }
  },
};
</script>
<style scoped>
#box {
  background: url("@/assets/orderingBG2.jpg") no-repeat center center fixed;
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
}

.card {
  width: 650px;
  height: 550px;
  border-radius: 30px;
  background: rgba(224, 224, 224, 0.4);
  /* box-shadow: 5px 5px 15px #c34a36,
    -5px -5px 10px #ff9671; */
  margin: 180px auto;
  position: relative;
}

.title {
  position: absolute;
  top: 80px;
  font-size: 40px;
  font-weight: 900;
  left: 140px;
  letter-spacing: 28px;
}

#input1,
#input2 {
  left: 175px;
}

#input1 {
  top: 180px;
}

#input2 {
  top: 250px;
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
  color: rgb(255, 255, 255);
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
  width: 30%;
  display: flex;
  align-items: center;
  justify-content: space-around;
  gap: 24px;
  position: absolute;
  top: 340px;
  left: 220px;
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

button {
  width: 200px;
  height: 50px;
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
  top: 420px;
  left: 225px;
  font-weight: 600;
  font-size: 17px;
  letter-spacing: 15px;
  text-align: center;
}

.toRegister {
  position: absolute;
  top: 490px;
  left: 255px;
  font-weight: 600;
  font-size: 17px;
  cursor: pointer;
  color: #6b6969;
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
</style>