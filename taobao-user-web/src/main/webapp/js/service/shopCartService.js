app.service('shopCartService', function ($http) {

    this.addToCart = function (cartItem) {
        return $http.post("shopCart/addToCart.do",cartItem);
    }

    //得到购物车的最后一个购物项则为刚才添加的商品项
    this.getLast = function () {
        return $http.get("shopCart/getLast.do");
    }

    this.findAll = function () {
        return $http.get("shopCart/findAll.do");
    }
    //批量删除
    this.del = function (ids) {
        return $http.get("shopCart/delete.do?ids="+ids);

    }
});