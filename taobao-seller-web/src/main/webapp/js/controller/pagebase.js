pageapp.constructor("pageController",function ($scope,$http) {



    $scope.paginationConf={
        currentPage:1,
        totalItems:10,
        itemsPerPage:10,
        perPageOptions:[10,20,30,50],
        onChange:function () {
            $scope.reload();
        }
    }

    $scope.reload=function () {
         $http.get('../seller/findGoodsPage.do?pageNum=' + $scope.paginationConf.currentPage+"&pageSize="+$scope.paginationConf.itemsPerPage).success(
            function (res) {
                $scope.goods=res.rows;
                $scope.totalItems=res.total;
        })
    }
})