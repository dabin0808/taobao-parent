app.controller('orderInfoController', function ($scope, tokenService, cartItemService, orderService, addressService) {
    $scope.userEntity = {};
    $scope.totalPrice = 0;
    $scope.userAddress = {};//用户选择的收货地址

    $scope.orderList = {}; //用户订单list
    $scope.orderInfoList = {}; //分页用户订单list
    $scope.page = 1;
    $scope.rows = 1;

    //从数据库中查询用户ID的订单  YHB
    $scope.loadOrderInfo = function (flag) {
        tokenService.getUserToken().success(
            function (response) {
                $scope.userEntity = JSON.parse(response.message);
                //查询返回订单组合类（order +orderItems）
                orderService.findById($scope.userEntity).success(function (data) {
                    console.log("order ::" + data.toString());
                    if (flag == 0) {
                        for (var i = 0; i < data.length; i++) {
                            $scope.orderList[i] = data[i];

                        }
                    } else if (flag == 1) { //评论

                        for (var i = 0; i < data.length; i++) {
                            $scope.orderList[i] = data[i];
                            var timeOrder = data[i].order;
                            var timeItems = data[i].orderItems;
                            var listItems = {}; //对应订单项列表
                            for (var i = 0; i < timeItems.size; i++) {
                                if ($scope.evaluateIf(timeOrder.goodsId, $scope.user.user.id)) {
                                    //存在
                                } else {
                                    listItems[i] = timeItems[i];    //赋值入
                                }
                            }
                            $scope.orderList[i].orderItems = listItems;

                        }
                    }
                    //分页数据
                    $scope.orderInfoList = $scope.reloadList($scope.orderList, $scope.page, $scope.rows);
                    console.log("$scope.orderInfoListId ::" + $scope.orderList[0].order.id);
                })
            })
    };

    //去付款，orderId
    $scope.goPay = function (orderId) {
        alert("orderId :" + orderId);
        window.location.href = "http://localhost:8081/pay.html#?orderId=" + orderId;
    };

    //更新
    $scope.updateOrderItem = function (entity, i) {
        console.log("entity: " + entity.id + "  " + " i= " + i);
        entity.paymentType = i;

        orderService.update(entity);
    };
    //去详情
    $scope.goDetails = function (goodsId) {
        alert("goodsId :" + goodsId);
        window.location.href = "http://localhost:8081/item.html#?goodsId=" + goodsId;
    };

    //根据商品id 和 用户名查询 是否评论，true 是以评论
    $scope.evaluateIf = function (goodsId) {
        commentService.findByStar(goodsId, 0).success(function (resp) {
            $scope.comment = JSON.parse(resp);
            if ($scope.comment.goodsId == goodsId && $scope.comment.username == $scope.user.username) {
                return true;
            }
            return false;
        })


    };
    //根据参数返回数据
    $scope.reloadList = function (list, page, rows) {
        var length = Object.keys(list);
        var l = {};
        var j = 0;
        for (var i = (page - 1) * rows; i < length.length; i++, j++) {
            if (j == rows) {
                break;
            }
            l[i] = list[i];
        }
        return l;
    };
    //分页，上一页
    $scope.pageUp = function () {
        $scope.page = $scope.page - 1;
        //if ()
        if ($scope.page < 1) {
            $scope.page = 1;
        }
        console.log("page : " + $scope.page);
        $scope.orderInfoList = $scope.reloadList($scope.orderList, $scope.page, $scope.rows);
    };
    //分页，下一页
    $scope.pageDown = function () {
        $scope.page = $scope.page + 1;
        //if
        var length = Object.keys($scope.orderList);
        if ($scope.page > length.length) {
            $scope.page = length.length;
        }
        console.log("page : " + $scope.page);
        $scope.orderInfoList = $scope.reloadList($scope.orderList, $scope.page, $scope.rows);
    };

    $scope.pageNum = function () {
        var n = $scope.page_Num;
        console.log("page_Num : " + n);
        if (n > 0 && n * $scope.paginationConf.itemsPerPage < $scope.paginationConf.totalItems) {
            $scope.paginationConf.currentPage = n;
            $scope.reloadList();//刷新列表
        }
    };
    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = orderService.update($scope.entity); //修改
        } else {
            serviceObject = orderService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //重新查询
                    $scope.reloadList();//重新加载
                } else {
                    alert(response.message);
                }
            }
        );
    };

    /* $scope.loadUserAddress($scope.userEntity.id);*/
    $scope.loadCartItem = function () {


        cartItemService.getCartItemList($scope.userEntity.id).success(
            function (response) {

                $scope.cartItemList = response;
                for (var i = 0; i < $scope.cartItemList.length; i++) {
                    $scope.cartItemList[i].goodsImages = $scope.cartItemList[i].goodsImage;
                    $scope.totalPrice += $scope.cartItemList[i].totalPrice;
                    console.log("msg cart: " + $scope.cartItemList[i]);
                }

            });
    };

    $scope.loadUser = function () {
        tokenService.getUserToken().success(
            function (response) {
                $scope.userEntity = JSON.parse(response.message);
                if ($scope.userEntity.id != null) {

                    //加载用户地址
                    $scope.loadUserAddress($scope.userEntity.id);

                    //加载用户购物项
                    $scope.loadCartItem();


                }
            }
        );
    };

    //提交订单
    $scope.submitOrder = function () {


        var data = {
            "cartItemList": $scope.cartItemList,
            "addressId": $scope.userAddress.id,
            "userId": $scope.userEntity.id
        };

        orderService.submitOrder(data).success(
            function (response) {

                if (response.success) {
                    var order = response.data;
                    alert(order);
                    //跳转到结算页面
                    location.href = "http://localhost:8081/pay.html#?orderId=" + order.id;

                } else {
                    alert("提交失败");
                }
            }
        );
    };
    $scope.loadUserAddress = function (userId) {
        //加载用户地址
        addressService.findByUserId($scope.userEntity.id).success(
            function (response) {
                $scope.userAddressList = response;
                for (var i = 0; i < $scope.userAddressList.length; i++) {
                    if ($scope.userAddressList[i].isDefault == 1) {
                        $scope.userAddress = $scope.userAddressList[i];
                        break;
                    }
                }
            }
        );
    };

    //切换地址
    $scope.changeAddr = function ($event, addr) {
        console.log(addr);
        var targetDiv = $event.target.parentElement;
        var li = $(".addr-item");
        var divs = li.children();
        for (var i = 0; i < divs.length; i++) {
            var divEle = $(divs[i]).children();

            divEle.removeClass("selected");

        }
        targetDiv.setAttribute("class", "con name selected");
        $scope.userAddress = addr;
    }
});