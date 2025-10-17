import request from '@/api/request.js'

export const userLogin = function (username, password) {
  return request.get('/user/login/' + username + '/' + password, {
  })
}

//获取所有用户信息
export const getAllUser = function (currentPage, pageSize) {
  return request.get('/user/getUserList/' + currentPage + '/' + pageSize, {
  })
}

//添加用户信息
export const addUser = function (userInfo) {
  return request({
    url: '/user/addUser',
    method: 'POST',
    data: userInfo,
    header: { 'Content-Type': 'multipart/form-data; boundary=----WebKitFormBoundaryVCFSAonTuDbVCoAN' }
  })
}

//修改用户信息
export const updateUser = function (userInfo) {
  return request({
    url: '/user/updateUser',
    method: 'POST',
    data: userInfo,
    header: { 'Content-Type': 'multipart/form-data; boundary=----WebKitFormBoundaryVCFSAonTuDbVCoAN' }
  })
}

//删除用户
export const deleteUser = function (userId) {
  return request.get('/user/deleteUser/' + userId, {
  })
}

export const searchUser = function (name, phone) {
  return request.post('/user/searchUser', {
    name, phone
  })
}

export const resetPassword = function (userId) {
  return request.get('/user/resetPassword/' + userId, {
  })
}

export const updateMyInfo = function (myInfo) {
  return request({
    url: '/user/updateMyInfo',
    method: 'POST',
    data: myInfo,
    header: { 'Content-Type': 'multipart/form-data; boundary=----WebKitFormBoundaryVCFSAonTuDbVCoAN' }
  })
}

export const register = function (myInfo) {
  return request({
    url: '/user/register',
    method: 'POST',
    data: myInfo,
    header: { 'Content-Type': 'multipart/form-data; boundary=----WebKitFormBoundaryVCFSAonTuDbVCoAN' }
  })
}