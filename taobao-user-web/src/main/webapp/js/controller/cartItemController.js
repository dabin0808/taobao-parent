app.controller('cartItemController', function ($scope, shopCartService) {


    //从cookie中找到该购物车项
    $scope.loadCartItem = function () {
        shopCartService.getLast().success(
            function (resposne) {
                $scope.entity = resposne;

                //将商品标题分隔
                var title = JSON.stringify(resposne.title);
                 $scope.itemTitle = title.split(",");


        });
    }
});