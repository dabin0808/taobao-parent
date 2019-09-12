//地址服务层
app.service('addressService', function ($http) {

    //读取用户ID列表数据绑定到表单中
    this.findUserId = function (entity) {
        return $http.post('../address/findAll.do', entity);
    }
    //分页
    this.findPage = function (page, rows) {
        return $http.get('../address/findPage.do?page=' + page + '&rows=' + rows);
    }
    //查询实体
    this.findOne = function (id) {
        return $http.get('../address/findOne.do?id=' + id);
    }
    //增加
    this.save = function (entity) {
        return $http.post("../address/addAddress.do", entity);
    }
    //修改
    this.update = function (entity) {
        return $http.post('../address/update.do', entity);
    }
    //删除
    this.dele = function (ids) {
        return $http.get('../address/delete.do?ids=' + ids);
    }
    //搜索
    this.search = function (page, rows, searchEntity) {
        return $http.post('../address/search.do?page=' + page + "&rows=" + rows, searchEntity);
    }

    //加载用户地址
    this.findByUserId = function (userId) {
        return $http.get("address/findById.do?userId=" + userId);
    }
});
