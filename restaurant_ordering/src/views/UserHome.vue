<template>
    <div>
        <el-header class="header-container">
            <!-- 左侧标题 -->
            <div class="header-left">
                <span class="system-title">欢迎登录餐厅点餐系统</span>
            </div>

            <!-- 中间导航菜单 -->
            <el-menu mode="horizontal" router :default-active="$route.path" class="nav-menu"
                background-color="transparent" text-color="black" active-text-color="white">
                <el-menu-item index="/userHome/userIndex">主页</el-menu-item>
                <el-menu-item index="/userHome/deshesIndex">美食广场</el-menu-item>
                <el-menu-item index="/userHome/myOrders">我的订单</el-menu-item>
                <el-menu-item index="/userHome/chat">在线咨询</el-menu-item>
            </el-menu>

            <!-- 右侧用户操作 -->
            <div class="header-right">
                <el-dropdown trigger="click" @command="handleCommand">
                    <div class="user-panel">
                        <el-avatar :size="36" :src="user.avatar"></el-avatar>
                        <span class="username">{{ user.name }}</span>
                        <i class="el-icon-arrow-down"></i>
                    </div>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="myInfo">个人信息</el-dropdown-item>
                        <el-dropdown-item command="changePassword">更改密码</el-dropdown-item>
                        <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </el-header>
        <router-view></router-view>
    </div>
</template>

<script>
export default {
    data() {
        return {
            user:''
        }
    },
    methods: {
        handleCommand(command) {
            if (command === 'logout') {
                localStorage.removeItem("user")
                this.$router.push('/login')
            }
            if (command === 'myInfo') {
                this.$router.push('/userHome/myInfo')
            }
            if (command == 'changePassword') {
                this.$router.push('/userHome/changePassword')
            }
        }
    },
    created(){
        this.user = JSON.parse(localStorage.getItem("user"))
    }
}
</script>

<style lang="less" scoped>
.system-title{
    font-size: 20px;
    font-weight: 600;
    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
}
.user-panel {
    display: flex;
    align-items: center;
    padding: 8px 12px;
    border-radius: 20px;
    background: rgba(255, 255, 255, 0.1);
    transition: all 0.3s;
    cursor: pointer;

    &:hover {
        background: rgba(255, 255, 255, 0.2);
        box-shadow: 0 2px 8px rgba(102, 166, 255, 0.2);
    }

    .username {
        margin: 0 8px;
        color: #fff;
        font-weight: 500;
    }
}

.header-container {
    background: linear-gradient(135deg, #89f7fe 0%, #66a6ff 100%);
    box-shadow: 0 2px 12px 0 rgba(102, 166, 255, 0.3);
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 30px;
    transition: background 0.5s ease;
}

/* 悬停动画 */
.nav-menu.el-menu--horizontal>.el-menu-item:not(.is-active):hover {
    background: rgba(255, 255, 255, 0.1) !important;
    transform: translateY(-2px);
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

/* 激活项指示条动画 */
.el-menu--horizontal>.el-menu-item.is-active::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 50%;
    width: 60%;
    height: 3px;
    background: #ffffff;
    transform: translateX(-50%);
    animation: underline 0.3s ease;
}

@keyframes underline {
    from {
        width: 0
    }

    to {
        width: 60%
    }
}
</style>