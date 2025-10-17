import request from '@/api/request.js'

export const getOrdersList = function (currentPage, pageSize) {
    return request.get('/orders/getOrdersList/' + currentPage + '/' + pageSize, {
    })
}

export const updateOrderFlag = function (ordersId, flag) {
    return request.get('/orders/updateOrderFlag/' + ordersId + '/' + flag, {
    })
}

export const searchOrder = function (flag) {
    return request.get('/orders/searchOrder/' + flag, {
    })
}

export const addOrder = function (userId) {
    return request.get('/orders/addOrder/' + userId, {
    })
}

export const getMyOrders = function (userId) {
    return request.get('/orders/getMyOrders/' + userId, {
    })
}

export const addAppraise = function (ordersId, star, appraise) {
    return request.get('/orders/addAppraise/' + ordersId + '/' + star + '/' + appraise, {
    })
}

export const getIndexAppraise = function () {
    return request.get('/orders/getIndexAppraise', {
    })
}