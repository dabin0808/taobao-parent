//控制层
app.controller('userController', function ($scope, $controller, userService, tokenService) {

    $controller('baseController', {$scope: $scope});//继承

    $scope.user = {};

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        userService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        userService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }
    //查询实体
    $scope.findOne = function () {

        //获取UserID
        tokenService.getUserToken().success(function (data) {

            console.log("data : " + data.message);
            /*var u = ;*/
            $scope.user = JSON.parse(data.message);
            //$scope.user = findone($scope.user);
            /*            if ($scope.user.sex == 'n')
                            $scope.user.sex = '男';
                        else
                            $scope.user.sex = '女';*/
            var time = $scope.formatStr($scope.user.birthday)

            $scope.user.day = time.getDay();
            $scope.user.month = time.getMonth();
            $scope.user.year = time.getFullYear();
            console.log("user : " + $scope.user);
            console.log("u.birthday : " + $scope.user.birthday + " day  1:" + $scope.user.year);
            console.log("user  username : " + $scope.user.username);
            console.log("user  sex : " + $scope.user.sex);
            /*  $scope.user.select_year2 = u.birthday;*/

        });


    };

    //转化Long型为字符串
    $scope.formatStr = function (date) {
        var time = new Date(date);
        return time;
    }

    $scope.formatDate = function (strDate) {
        var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,
            function (a) {
                return parseInt(a, 10) - 1;
            }).match(/\d+/g) + ')');
        console.log(date);

        return date;
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        var serviceObject2;
        // select 元素
        var select_year2 = $("#select_year2").find("option:selected").text();
        var select_month2 = $("#select_month2").find("option:selected").text();
        var select_day2 = $("#select_day2").find("option:selected").text();

        var strDate = select_year2 + "-" + select_month2 + "-" + select_day2;

        $scope.user.birthday = $scope.formatDate(strDate);
        console.log("修改 $scope.user.birthday : " + $scope.user.birthday);

        var file = document.getElementById("up_img_WU_FILE_0").files[0];
        if (file != null) {
            serviceObject2 = userService.uploadImge();
            serviceObject2.success(function (data) {
                $scope.user.headImg = data.url;
                console.log("userl 修改 user  username : " + $scope.user.username + $scope.user.sex + $scope.user.headImg);
                serviceObject = userService.update($scope.user); //修改
                serviceObject.success(
                    function (response) {
                        if (response.success) {
                            //数据回显
                            //重新加载
                            alert(response.message);
                        } else {
                            alert(response.message);
                        }
                    }
                );
            });
        }

    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        userService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchUser = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        userService.search(page, rows, $scope.searchUser).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

});
