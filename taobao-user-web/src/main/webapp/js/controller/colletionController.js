app.controller('collectionController',function ($scope,tokenService,collectionService) {
    $scope.userEntity={};
    //得到用户收藏夹
    $scope.loadUser = function(){
        tokenService.getUserToken().success(
            function (response) {
            if(response.success){
                $scope.userEntity = JSON.parse(response.message);
                $scope.getCollection($scope.userEntity.id);
            }else{
                alert("请先登录");
                var url = "http://localhost:8081/home-person-collect.html";
                url = encodeURI(url);
                location.href="http://localhost:8081/login.html#?url="+url+"&id="+new Date().getTime();
            }
        });
    }
    //得到用户收藏夹

    $scope.collectionIds = [];
    $scope.getCollection=function (userId) {
        collectionService.getCollection(userId).success(
            function (response) {
                $scope.collectionEntity = response;

               // alert($scope.collectionEntity);
            }
        );
    }

    //删除收藏
    $scope.dele = function (id) {
        collectionService.dele(id).success(
            function (response) {
                if(response.success){
                    var index = $scope.collectionEntity.indexOf(id);
                    $scope.collectionEntity.splice(index,1);
                }else{

                }
            }
        );
    }
})