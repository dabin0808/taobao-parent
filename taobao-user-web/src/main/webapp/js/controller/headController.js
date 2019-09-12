app.controller('headController', function ($scope, $http, tokenService) {
    $scope.loadUser = function () {
        tokenService.getUserToken().success(function (response) {
            if (response.success) {
                //得到cookie中的用户信息
                $scope.userEntity = JSON.parse(response.message);

            }
        });
    }

    //进入用户主页
    $scope.enterHome = function () {
        alert(55);
        var url = "http://localhost:8081/home-index.html";
        if ($scope.userEntity == null) {
            location.href = "http://localhost:8081/login.html#?url=" + url;
        } else {
            location.href = url;
        }
    }
    //搜索
    $scope.search = function () {
        alert($scope.keywords);
        if($scope.keywords!=""){

            window.location.href="http://localhost:8085/search.html#?keywords="+$scope.keywords;
        }
    }
});