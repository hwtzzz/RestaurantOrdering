import request from '@/api/request.js'

export const getDishesList = function (currentPage, pageSize) {
    return request.get('/dishes/getDishesList/' + currentPage + '/' + pageSize, {
    })
}


export const addDishes = function (dishesInfo) {
    return request({
        url: '/dishes/addDishes',
        method: 'POST',
        data: dishesInfo,
        header: { 'Content-Type': 'multipart/form-data; boundary=----WebKitFormBoundaryVCFSAonTuDbVCoAN' }
    })
}


export const updateDishes = function (dishesInfo) {
    return request({
        url: '/dishes/updateDishes',
        method: 'POST',
        data: dishesInfo,
        header: { 'Content-Type': 'multipart/form-data; boundary=----WebKitFormBoundaryVCFSAonTuDbVCoAN' }
    })
}


export const deleteDishes = function (dishesId) {
    return request.get('/dishes/deleteDishes/' + dishesId, {
    })
}

export const searchDishes = function (dishesName, dishesType) {
    return request.post('/dishes/searchDishes', {
        dishesName, dishesType
    })
}

export const getIndexDishes = function () {
    return request.get('/dishes/getIndexDishes', {
    })
}

export const getAllDishes = function () {
    return request.get('/dishes/getAllDishes', {
    })
}