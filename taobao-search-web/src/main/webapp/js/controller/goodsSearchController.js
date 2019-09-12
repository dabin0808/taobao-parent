app.controller('goodsSearchController',function ($scope,$location,goodsSearchService) {
    $scope.keywords = "";
    $scope.searchEntity = {pageNo:1};


    $scope.clcikSearch=function(){
       // $scope.searchEntity={};
        $scope.searchEntity.keywords=$scope.keywords;
        $scope.search();
    }
    //加载从别的页面传来的数据
    $scope.load = function(){
        var categoryId = $location.search()['categoryId'];
        var keywords = $location.search()['keywords'];
        if(categoryId!=null&&categoryId!=""){
            $scope.searchEntity.category = categoryId;
        }
        if(keywords!=null&&keywords!=""){
            $scope.searchEntity.keywords = keywords;
        }
        $scope.search();
    }

    $scope.search = function () {
        goodsSearchService.search($scope.searchEntity).success(
            function (response) {
                $scope.resultMap = response;
                var data = response.page.rows;
                console.log(data);
                $scope.resultEntity = data;
                for(var i=0;i<$scope.resultEntity.length;i++){

                    $scope.resultEntity[i].goods.image = JSON.parse($scope.resultEntity[i].goods.bigImg)[0];
                }

                buildPageLabel();
            }
        );
    }
    buildPageLabel = function () {
        //构建分页栏
        $scope.pageLabel = [];
        var firstPage = 1;//开始页码
        var lastPage = $scope.resultMap.totalPages;//截止页码
        $scope.firstDot = true;//前面有点
        $scope.lastDot = true;//后面有点

        if ($scope.resultMap.totalPages > 5) {//如果页码数量大于5

            if ($scope.resultMap.pageNo <= 3) {//如果当前页码小于等于3，显示前5页
                lastPage = 5;
                $scope.firstDot = false;//前面没点
            } else if ($scope.searchEntity.pageNo >= $scope.searchEntity.totalPages - 2) {//显示后5页
                firstPage = $scope.resultMap.totalPages - 4;
                $scope.lastDot = false;//后面没点
            } else {//显示以当前页为中心的5页
                firstPage = $scope.searchEntity.pageNo - 2;
                lastPage = $scope.searchEntity.pageNo + 2;
            }

        }

        for (var i = firstPage; i <= lastPage; i++) {
            $scope.pageLabel.push(i);
        }
    }
    
    //价格搜索
    $scope.priceSearch =function (priceStr) {
        $scope.searchEntity.price = priceStr;
        $scope.search();
        
    }

    //升降序
    $scope.orderSearch = function (orderStr) {
        $scope.searchEntity.order = orderStr;
        $scope.search();
    }

    //删除筛选条件
    $scope.deleCondition = function (key) {
        delete $scope.searchEntity[key];

    }

    $scope.goodsDetail =function (goodsId) {
        location.href="http://localhost:8081/item.html#?goodsId="+goodsId;
    }

    //分页查询
    $scope.queryByPage = function (pageNo) {
        $scope.searchEntity.pageNo = pageNo;

        $scope.search();
    }
    
    
});