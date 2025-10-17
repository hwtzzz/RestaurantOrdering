import request from '@/api/request.js'

export const adminLogin = function (username, password) {
    return request.get('/admin/login/' + username + '/' + password, {
    })
}

export const getAdminList = function (currentPage, pageSize) {
    return request.get('/admin/getAdminList/' + currentPage + '/' + pageSize, {
    })
}

//添加用户信息
export const addAdmin = function (adminInfo) {
    return request({
        url: '/admin/addAdmin',
        method: 'POST',
        data: adminInfo,
        header: { 'Content-Type': 'multipart/form-data; boundary=----WebKitFormBoundaryVCFSAonTuDbVCoAN' }
    })
}

//修改用户信息
export const updateAdmin = function (adminInfo) {
    return request({
        url: '/admin/updateAdmin',
        method: 'POST',
        data: adminInfo,
        header: { 'Content-Type': 'multipart/form-data; boundary=----WebKitFormBoundaryVCFSAonTuDbVCoAN' }
    })
}

//删除用户
export const deleteAdmin = function (adminId) {
    return request.get('/admin/deleteAdmin/' + adminId, {
    })
}

export const searchAdmin = function (name, phone) {
    return request.post('/admin/searchAdmin', {
        name, phone
    })
}

export const resetPassword = function (adminId) {
    return request.get('/admin/resetPassword/' + adminId, {
    })
}

export const getDataInfo = function () {
    return request.get('/admin/getDataInfo', {
    })
}

//这个修改密码的方法所有人都可以使用
export const updatePassword = function (userType, id, password) {
    return request.get('/admin/updatePassword/' + userType + '/' + id + '/' + password, {
    })
}