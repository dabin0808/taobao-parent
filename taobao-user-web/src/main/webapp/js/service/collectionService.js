app.service('collectionService', function ($http) {


    //添加商品到收藏夹
    this.addToCollect = function (goodsId, userId) {
        return $http.get("collection/add.do?goodsId=" + goodsId + "&userId=" + userId);
    }

    //得到用户收藏夹
    this.getCollection = function (userId) {
        return $http.get("collection/list.do?userId=" + userId);

    }

    //删除收藏
    this.dele = function (id) {
        return $http.get("collection/delete.do?id=" + id);
    }
})