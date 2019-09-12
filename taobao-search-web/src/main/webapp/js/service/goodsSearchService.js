app.service('goodsSearchService',function ($http) {

    this.search = function (searchEntity) {
       return $http.post("goods/search.do",searchEntity);
    }
});