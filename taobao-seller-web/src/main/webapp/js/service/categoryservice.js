
app.service("categoryService",function($http){
    this.findAll=function(id){
        return $http.get('../seller/findAll.do?id='+id);
    }
    this.findParent=function (id) {
        return $http.post('../seller/findParent.do?id='+id);
    }
    this.findOne=function (id) {
        return $http.post('../seller/findOne.do?id='+id);
    }
    this.updateCategory=function (entity) {
        return $http.post('../seller/updateCategory.do',entity);
    }
    this.delCategory=function (ids) {
        return $http.get('../seller/delCategory.do?ids='+ids);
    }

    this.addCategory=function (entity) {
        return $http.post('../seller/addCategory.do',entity);
    }
    this.addGoods=function (entity) {
        return $http.post('../seller/addGoods.do',entity);
    }

})