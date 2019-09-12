//服务层
app.service('userService', function ($http) {

    //读取列表数据绑定到表单中
    this.findAll = function () {
        return $http.get('../user/findAll.do');
    }
    //分页
    this.findPage = function (page, rows) {
        return $http.get('../user/findPage.do?page=' + page + '&rows=' + rows);
    }
    //查询实体
    this.findOne = function (id) {
        return $http.get('../user/findOne.do?id=' + id);
    }
    //增加
    this.add = function (entity) {
        return $http.post('../user/add.do', entity);
    }
    //修改
    this.update = function (entity) {
        return $http.post('../user/update.do', entity);
    }
    //删除
    this.dele = function (ids) {
        return $http.get('../user/delete.do?ids=' + ids);
    }
    //搜索
    this.search = function (page, rows, searchEntity) {
        return $http.post('../user/search.do?page=' + page + "&rows=" + rows, searchEntity);
    }

    //文件上传
    this.uploadImge = function () {
        var file = document.getElementById("up_img_WU_FILE_0").files[0];
        var fromdata = new FormData();
        fromdata.append('file', file);
        return $http({
            url: '../user/uploadImage.do',
            method: "post",
            data: fromdata,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        })
    }
});
