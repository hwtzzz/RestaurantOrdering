import request from '@/api/request.js'

export const getIndexBanner = function () {
    return request.get('/banner/getIndexBanner', {
    })
}

export const addBanner = function (bannerInfo) {
    return request({
      url: '/banner/addBanner',
      method: 'POST',
      data: bannerInfo,
      header: { 'Content-Type': 'multipart/form-data; boundary=----WebKitFormBoundaryVCFSAonTuDbVCoAN' }
    })
  }
  
  //修改用户信息
  export const updateBanner = function (bannerInfo) {
    return request({
      url: '/banner/updateBanner',
      method: 'POST',
      data: bannerInfo,
      header: { 'Content-Type': 'multipart/form-data; boundary=----WebKitFormBoundaryVCFSAonTuDbVCoAN' }
    })
  }
  
  //删除用户
  export const deleteBanner = function (bannerId) {
    return request.get('/banner/deleteBanner/' + bannerId, {
    })
  }