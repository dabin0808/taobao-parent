app.controller('goodsController', function ($scope, $location, goodsService,shopCartService,tokenService,collectionService,commentService) {

    $scope.specList = {};
    $scope.goodsItem = {num: 1};//购物车项对象
    //接收从其它页面传来的goodsId
    $scope.loadGoodsId = function () {
        $scope.goodsId = $location.search()['goodsId'];

        $scope.findOne($scope.goodsId);

    }
    //查询商品
    $scope.findOne = function (id) {
        goodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;
                $scope.entity.goods.specification = JSON.parse($scope.entity.goods.specification);
                $scope.goodsItem.price = $scope.entity.goods.price;
                $scope.goodsItem.goodsId = $scope.entity.goods.id;
                $scope.goodsItem.name = $scope.entity.goods.name;
                $scope.entity.goods.bigImg = JSON.parse($scope.entity.goods.bigImg);
                $scope.goodsItem.goodsImage = $scope.entity.goods.bigImg[0]

                console.log($scope.entity.goods.specification);
                var specKeys = Object.keys($scope.entity.goods.specification);
                console.log(specKeys);
                for(var i=0;i<specKeys.length;i++){
                    $scope.specList[specKeys[i]]=null;
                }
            });
    }
    //判断规格是否选中
    $scope.isSelected = function (key, value) {
        if ($scope.specList[key] == value) {
            return true;
        } else {
            return false;
        }

    }
    //选择规格
    $scope.selectSpecification = function (key, value) {
        $scope.specList[key] = value;
    }

    //增加商品数量
    $scope.addNum = function (num) {
        $scope.goodsItem.num += num;
        if ($scope.goodsItem.num < 1) {
            $scope.goodsItem.num = 1;
        }
    }

    //添加到购物车
    $scope.addToCart = function () {
        //将用户选择的商品规格保存到goodsItem存放到购物车
        //$scope.goodsItem.specList = $scope.specList;



        //判断用户是否选择了商品规格
        if(!$scope.isSelectedSpecList()){
            alert("请先选择商品规格");
            return ;
        }
        var specs = "";
        var i =0;

        for (key in $scope.specList) {

            specs += key+":"+$scope.specList[key];
            specs += ",";


        }
        specs = specs.substring(0,specs.length-1);
        console.log(specs);
        //完善购物车项属性
        $scope.goodsItem.totalPrice = $scope.goodsItem.price*$scope.goodsItem.num;
        $scope.goodsItem.title = $scope.entity.goods.title + "," + specs;

        //添加到购物车
        shopCartService.addToCart($scope.goodsItem).success(
            function (response){
                if(response.success){
                    alert("添加到购物车成功");
                    //跳转到成功页面
                    location.href="http://localhost:8081/success-cart.html#?goodsId="+$scope.goodsItem.goodsId;
                }
            }
        );



    }
    //判断是否有选中规格
    $scope.isSelectedSpecList = function () {
        for (key in $scope.entity.goods.specification) {
            if ($scope.specList[key] == null ) {
                return false;
            }
        }
        return true;
    }
    
    //添加到收藏夹
    $scope.addToCollect = function (id) {
        //判断是否登录
        tokenService.getUserToken().success(
            function (response) {
                if(response.success){
                    var user = JSON.parse(response.message);

                    collectionService.addToCollect($scope.goodsItem.goodsId,user.id).success(
                      function (response) {
                      if(response.success){
                          alert(response.message);
                      }else{
                          alert(response.message);
                      }
                  });
                }else{
                    var url = "http://localhost:8081/item.html";
                    url = encodeURI(url);
                    alert("对不起，请先登录");
                    location.href="http://localhost:8081/login.html#?url="+url+"&goodsId="+$scope.goodsItem.goodsId;
                }
            }
        );
    }

    //加载评论
    $scope.loadComment = function (star) {
        //var goodsId = $location.search()['goodsId'];
        //var star = 0;
        commentService.findByStar($scope.goodsId,star).success(
            function (response) {
                $scope.commentEntity = response;
            }
        );
       // alert(goodsId);
    }

    //筛选评价
    $scope.filterComment =function (star) {
        $scope.loadComment(star);
    }


});