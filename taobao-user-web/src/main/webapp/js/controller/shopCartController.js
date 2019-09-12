app.controller('shopCartController', function ($scope, shopCartService, tokenService, cartItemService) {

    $scope.selectIds = [];//用来标记选中的购物项
    $scope.totalPrice = 0;//购物车的总计
    //加载购物车中的所有购物项
    $scope.loadShopCart = function () {

        shopCartService.findAll().success(
            function (reponse) {
                $scope.cartItemList = reponse;
                for (var i = 0; i < $scope.cartItemList.length; i++) {
                    $scope.cartItemList[i].titleArr = spliteTitle($scope.cartItemList[i].title);
                    $scope.cartItemList[i].checked = false;
                }


            }
        );
    }

    //将标题进行分隔
    function spliteTitle(title) {
        var arr = title.split(",");
        return arr;
    }

    //增加数量
    $scope.addNum = function (index, num) {
        $scope.cartItemList[index].num += num;
        if ($scope.cartItemList[index].num < 1) {
            $scope.cartItemList[index].num = 1;
        } else if ($scope.cartItemList[index].checked) {
            $scope.totalPrice += $scope.cartItemList[index].price * num;
        }
        $scope.cartItemList[index].totalPrice = $scope.cartItemList[index].num * $scope.cartItemList[index].price;
        //如果为选中状态总计金额也要改变
    }

    //多选
    $scope.updateSelection = function ($event, id) {


        if ($event.target.checked) {

            $scope.selectIds.push(id);
        } else {
            var index = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(index, 1);
        }

        for (var i = 0; i < $scope.cartItemList.length; i++) {
            if ($scope.cartItemList[i].id == id) {
                $scope.cartItemList[i].checked = !$scope.cartItemList[i].checked;
                break;
            }
        }
    }
    //全选
    $scope.selectAll = function ($event) {

        if ($event.target.checked) {
            //选中
            for (var i = 0; i < $scope.cartItemList.length; i++) {
                $scope.cartItemList[i].checked = true;
                $scope.selectIds.push($scope.cartItemList[i].id);
            }

        } else {
            for (var i = 0; i < $scope.cartItemList.length; i++) {
                $scope.cartItemList[i].checked = false;
                $scope.selectIds = [];
            }
        }


    }
    //监听选中的购物项得出总计
    $scope.$watch('cartItemList', function (newValue, oldValue) {

        for (var i in $scope.cartItemList) {
            if (newValue[i].checked != oldValue[i].checked) {
                if ($scope.cartItemList[i].checked) {
                    $scope.totalPrice += $scope.cartItemList[i].totalPrice;
                } else {
                    $scope.totalPrice -= $scope.cartItemList[i].totalPrice;

                }
            }
        }
        if ($scope.totalPrice < 0) {
            $scope.totalPrice = 0;
        }
        /* for (var i = 0; i < $scope.cartItemList.length; i++) {
             $scope.totalPrice += $scope.cartItemList[i][newValue].totalPrice;
         }*/

    }, true);

    //删除选中的商品
    $scope.del = function () {
        shopCartService.del($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    for (var i = 0; i < $scope.cartItemList.length; i++) {
                        if ($scope.selectIds.indexOf($scope.cartItemList[i].id) != -1) {
                            $scope.cartItemList.splice(i, 1);
                        }
                    }
                }
            }
        );
    }

    //结算
    $scope.checkOut = function () {
        //判断是否有用户token
        tokenService.getUserToken().success(
            function (response) {
                if (response.success) {
                    //得到cookie中的用户信息
                    $scope.userEntity = JSON.parse(response.message);
                    //alert(JSON.stringify($scope.cartItemList));
                    //再将购物车项添加到redis中
                    if ($scope.selectIds.length > 0) {

                        var cartItemList = [];

                        for(var i=0;i<$scope.cartItemList.length;i++){

                            if($scope.selectIds.indexOf($scope.cartItemList[i].id)!=-1){
                                var item = JSON.parse(JSON.stringify($scope.cartItemList[i]));
                                delete item.titleArr;
                                delete item.checked;
                                delete item.$$hashKey;
                                cartItemList.push(item);
                            }


                          //  alert($scope.cartItemList[i].id);
                        }
                        console.log(cartItemList);
                        var data={"cartItemList":cartItemList};
                        cartItemService.addCartItem(cartItemList).success(
                            function (response) {
                                if (response.success) {

                                    location.href = "http://localhost:8081/getOrderInfo.html";
                                } else {
                                    // alert(response.message);
                                }
                            });
                    }else{
                        alert("请先选中购物项");
                    }


                } else {
                    var url = "http://localhost:8081/cart.html";
                    //跳转到登录
                    location.href = "http://localhost:8081/login.html#?url=" + url;
                }
            }
        );
    }


});