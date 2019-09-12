app.service('cartItemService',function ($http) {

    //将用户选择的购物项添加到redis用于在getOrderInfo.html界面显示
    this.addCartItem = function (cartItemList) {
        alert(cartItemList);
        return $http.post("cartItem/add.do",cartItemList);
    }
    /*location.href="http://localhost:8081/pay.html#?orderId = "+orderId;*/
    //将redis中取出用户的购物项列表用于在getOrderInfo.html界面显示
    this.getCartItemList = function (userId) {
        return $http.get("cartItem/get.do?userId="+userId);
    }
});