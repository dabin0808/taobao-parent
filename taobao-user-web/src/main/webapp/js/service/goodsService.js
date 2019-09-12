app.service('goodsService',function ($http) {


    this.findOne = function (id) {
        return $http.get("goodsDetail/findOne.do?id="+id);
    }




});