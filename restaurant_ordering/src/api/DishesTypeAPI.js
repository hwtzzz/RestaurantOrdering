import request from '@/api/request.js'

export const getDishesTypeList = function () {
    return request.get('/dishesType/getDishesTypeList', {
    })
}

//添加用户信息
export const addDishesType = function (dishesInfo) {
    return request({
        url: '/dishesType/addDishesType',
        method: 'POST',
        data: dishesInfo,
        header: { 'Content-Type': 'multipart/form-data; boundary=----WebKitFormBoundaryVCFSAonTuDbVCoAN' }
    })
}

//修改用户信息
export const updateDishesType = function (dishesInfo) {
    return request({
        url: '/dishesType/updateDishesType',
        method: 'POST',
        data: dishesInfo,
        header: { 'Content-Type': 'multipart/form-data; boundary=----WebKitFormBoundaryVCFSAonTuDbVCoAN' }
    })
}

//删除用户
export const deleteDishesType = function (dishesTypeId) {
    return request.get('/dishesType/deleteDishesType/' + dishesTypeId, {
    })
}

export const searchDishesType = function (dishesTypeName) {
    return request.get('/dishesType/searchDishesType/' + dishesTypeName, {

    })
}