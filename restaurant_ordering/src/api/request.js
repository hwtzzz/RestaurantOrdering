import axios from 'axios'

axios.defaults.baseURL = 'http://127.0.0.1:8888/api'
// 调用 axios.create() 函数，创建一个 axios 的实例对象，用 request 来接收
const request = axios.create({
})

export default request