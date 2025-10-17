import Admin from '@/components/admin/Admin.vue'
import Banner from '@/components/admin/Banner.vue'
import Dishes from '@/components/admin/Dishes.vue'
import DishesType from '@/components/admin/DishesType.vue'
import Order from '@/components/admin/Order.vue'
import User from '@/components/admin/User.vue'
import Welcome from '@/components/admin/Welcome.vue'
import ChangePassword from '@/components/ChangePassword.vue'
import Chat from '@/components/Chat.vue'
import DeshesIndex from '@/components/user/DeshesIndex.vue'
import MyInfo from '@/components/user/MyInfo.vue'
import MyOrders from '@/components/user/MyOrders.vue'
import UserIndex from '@/components/user/UserIndex.vue'
import AdminHome from '@/views/AdminHome.vue'
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'
import UserHome from '@/views/UserHome.vue'
import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '默认登录页',
    component: Login
  },
  {
    path: '/login',
    name: '登录页',
    component: Login
  },
  {
    path: '/register',
    name: '注册页',
    component: Register
  },
  {
    path: '/adminHome',
    name: '管理员页面',
    component: AdminHome,
    redirect: '/adminHome/user',
    children: [
      {
        path: 'welcome',
        component: Welcome
      },
      {
        path: 'banner',
        component: Banner
      },
      {
        path: 'user',
        component: User,
        meta: {
          title: '用户管理'
        }
      },
      {
        path: 'admin',
        component: Admin,
        meta: {
          title: '管理员管理'
        }
      },
      {
        path: 'changePassword',
        component: ChangePassword,
        meta: {
          title: '密码修改'
        }
      },
      {
        path: 'dishesType',
        component: DishesType,
        meta: {
          title: '菜品分类'
        }
      },
      {
        path: 'dishes',
        component: Dishes,
        meta: {
          title: '菜品管理'
        }
      },
      {
        path: 'order',
        component: Order,
        meta: {
          title: '订单管理'
        }
      },
      {
        path: 'chat',
        component: Chat,
        meta: {
          title: '顾客咨询'
        }
      },
    ]
  },
  {
    path: '/userHome',
    name: '用户页面',
    component: UserHome,
    redirect: '/userHome/userIndex',
    children: [
      {
        path: 'userIndex',
        component: UserIndex
      },
      {
        path: 'myOrders',
        component: MyOrders
      },
      {
        path: 'deshesIndex',
        component: DeshesIndex
      },
      {
        path: 'changePassword',
        component: ChangePassword
      },
      {
        path: 'myInfo',
        component: MyInfo
      },
      {
        path: 'chat',
        component: Chat
      },
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
