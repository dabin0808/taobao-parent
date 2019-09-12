//控制层
app.controller('addressController', function ($scope, $controller, tokenService, addressService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中
    $scope.findAll = function () {
        //获取UserID
        tokenService.getUserToken().success(function (data) {
            $scope.entity = JSON.parse(data.message);
            addressService.findUserId($scope.entity).success(
                function (response) {
                    console.log("findUserId " + $scope.entity.userId);
                    $scope.list = response;
                }
            );
        })
    }
    var userId;
    $scope.loginUser = function () {
        //获取UserID
        tokenService.getUserToken().success(function (data) {
            $scope.user = JSON.parse(data.message);
            userId = $scope.user.id;
            console.log("data.user : " + $scope.user.id);
        });
    }

    //分页
    $scope.findPage = function (page, rows) {
        addressService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        addressService.findOne(id).success(
            function (response) {
                $scope.entity = response;
                var n = $scope.entity.receiverAddress.split(" ");
                /*                $scope.entity.province = n[0];
                                $scope.entity.city = n[1];
                                $scope.entity.district = n[2];
                                $scope.entity.detailed = n[3];*/

                //数据
                console.log("修改数据：" + $scope.entity.city + " " + $scope.entity.district + " " + $scope.entity.detailed);
            }
        );
    }

    $scope.setDefault = function (id) {
        console.log("id : mm" + id);
        for (var i = 0; i < $scope.list.length; i++) {
            if ($scope.list[i].id != id) {
                $scope.list[i].isDefault = 0;
            } else {
                $scope.list[i].isDefault = 1;
            }
            addressService.update($scope.list[i]); //修改
        }


    }

    //保存
    $scope.save = function () {
        // select 元素
        var province = $("#province").find("option:selected").text();
        var city = $("#city").find("option:selected").text();
        var district = $("#district").find("option:selected").text();
        console.log(province);
        console.log(city);
        console.log(district);
        //拼接地址
        $scope.entity.receiverAddress = province + " "
            + city + " " + district + " " + $scope.entity.detailed;

        $scope.entity.userId = $scope.user.id;
        if ($scope.entity.isDefault == null) {
            $scope.entity.isDefault = 0;
        }
        console.log("$scope.entity.userId: " + $scope.entity.userId);
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = addressService.update($scope.entity); //修改
        } else {
            serviceObject = addressService.save($scope.entity);//增加
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
    }
    //删除一个
    $scope.deleOne = function (id) {
        //获取选中的复选框
        addressService.dele(id).success(
            function (response) {
                console.log("re : " + response.success);
                if (response.success) {
                    alert("sc");
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        addressService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象
    /*  $scope.searchEntity.userId = userId;*/
    /* console.log("$scope.searchEntity " + $scope.searchEntity.userId);*/
    //搜索
    $scope.search = function (page, rows) {
        tokenService.getUserToken().success(function (data) {
            $scope.user = JSON.parse(data.message);
            $scope.searchEntity.userId = $scope.user.id;
            console.log(" $scope.searchEntity.userId : " + $scope.searchEntity.userId);
            addressService.search(page, rows, $scope.searchEntity).success(
                function (response) {
                    $scope.list = response.rows;
                    $scope.paginationConf.totalItems = response.total;//更新总记录数
                }
            );
        });

    }

    //分页，下一页
    /* $scope.pageDown = function () {
         /!*var  n = paginationConf.totalItems%paginationConf.itemsPerPage
             ?(paginationConf.totalItems/paginationConf.itemsPerPage)+1:paginationConf.totalItems%paginationConf.itemsPerPage;
       *!/
         console.log("count : " + $scope.paginationConf.currentPage);
         console.log("sum : " + $scope.paginationConf.currentPage * $scope.paginationConf.itemsPerPage);
         if ($scope.paginationConf.currentPage * $scope.paginationConf.itemsPerPage < $scope.paginationConf.totalItems) {
             $scope.paginationConf.currentPage += 1;
             $scope.reloadList();//刷新列表
         }
         if ($scope.paginationConf.currentPage > $scope.paginationConf.totalItems)
             $scope.paginationConf.currentPage = $scope.paginationConf.totalItems;
     }
     //分页，上一页
     $scope.pageUp = function () {
         console.log("sub : " + $scope.paginationConf.currentPage * $scope.paginationConf.itemsPerPage);
         if ($scope.paginationConf.currentPage > 1) {
             $scope.paginationConf.currentPage -= 1;
             $scope.reloadList();//刷新列表
         }
         if ($scope.paginationConf.currentPage < 1)
             $scope.paginationConf.currentPage = 1;
     }
     //分页，上一页
     $scope.pageNum = function () {
         var n = $scope.page_Num;
         console.log("page_Num : " + n);
         if (n > 0 && n * $scope.paginationConf.itemsPerPage < $scope.paginationConf.totalItems) {
             $scope.paginationConf.currentPage = n;
             $scope.reloadList();//刷新列表
         }
     }*/

});
