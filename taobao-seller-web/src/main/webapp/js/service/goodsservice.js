
app.service("goodsService",function($http){
    this.findAll=function(pageNum,pageSize) {
        return $http.get('../seller/findGoodsPage.do?pageNum=' + pageNum+"&pageSize="+pageSize);
    }

})