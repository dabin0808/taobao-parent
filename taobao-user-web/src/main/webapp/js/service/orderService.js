app.service('orderService', function ($http) {
    //提交订单
    this.submitOrder = function (cartItemList) {
        return $http.post("order/add.do", cartItemList);
    }

    //根据UserID 查询对应订单
    this.findById = function (entity) {
        return $http.post('../userorder/findById.do', entity);
    }
    //分页
    this.findPage = function (page, rows) {
        return $http.get('../userorder/findPage.do?page=' + page + '&rows=' + rows);
    }
    //增加
    this.add = function (entity) {
        return $http.post('../userorder/add.do', entity);
    }
    //修改
    this.update = function (entity) {
        return $http.post('../userorder/update.do', entity);
    }
    //删除
    this.dele = function (ids) {
        return $http.get('../userorder/delete.do?ids=' + ids);
    }
    //搜索
    this.search = function (page, rows, searchEntity) {
        return $http.post('../userorder/search.do?page=' + page + "&rows=" + rows, searchEntity);
    }
});