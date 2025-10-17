import request from '@/api/request.js'

export const addCar = function (userId, dishesId) {
    return request.get('/car/addCar/' + userId + '/' + dishesId, {
    })
}

export const getMyCarCount = function (userId) {
    return request.get('/car/getMyCarCount/' + userId, {
    })
}

export const getMyCarList = function (userId) {
    return request.get('/car/getMyCarList/' + userId, {
    })
}

export const deleteCar = function (carId) {
    return request.get('/car/deleteCar/' + carId, {
    })
}
