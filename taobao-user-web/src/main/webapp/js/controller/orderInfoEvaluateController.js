/*
app.controller('orderInfoController', function ($scope, goodsService, commentService, tokenService, cartItemService, orderService, addressService, commentService) {

    $controller('orderInfoController', {$scope: $scope});//继承

    $scope.evaluateIf = function (goodsId) {
        commentService.findByStar(goodsId, 0).success(function (resp) {
            $scope.comment = JSON.parse(resp);
            if ($scope.comment.goodsId == goodsId && $scope.comment.username == $scope.user.username) {
                return true;
            }
            return true;
        })


    }

})*/
