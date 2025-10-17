import request from '@/api/request.js'

export const getAllChat = function () {
    return request.get('/chat/getAllChat', {
    })
}

export const addChat = function (chatInfo) {
    return request({
        url: '/chat/addChat',
        method: 'POST',
        data: chatInfo,
        header: { 'Content-Type': 'multipart/form-data; boundary=----WebKitFormBoundaryVCFSAonTuDbVCoAN' }
    })
}