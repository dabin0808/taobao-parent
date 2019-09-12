//控制层
app.controller('userInfoController', function ($scope, $controller, $http, userInfoService, tokenService) {

    $controller('baseController', {$scope: $scope});//继承



    $scope.user = {};
    //读取列表数据绑定到表单中
    $scope.findAll = function () {
        userService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    };

    //分页
    $scope.findPage = function (page, rows) {
        userService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    };
    //查询实体
    $scope.findOne = function () {

        //获取UserID
        tokenService.getUserToken().success(function (data) {
            if (data.success == false) {
                //alert(data.success);
                window.location.href = "login.html"
            }
            $scope.user = JSON.parse(data.message);
            console.log("u.birthday : " + $scope.user.birthday + " day  1:" + $scope.user.year);
        });


    };

    //保存
    $scope.save = function () {
        if ($("#confirm_password").val() == $("#password").val()) {
            $scope.user.password = $("#confirm_password").val();
            userInfoService.update($scope.user).success(function (response) {
                console.log("re : " + $scope.user.password);
                if (response.success) {
                    alert("成功");
                } else {
                    alert(response.message());
                }
            });
        } else
            alert("密码不一样");
    };

    $scope.SendSMSVerificationCode = function (i) {
        console.log("funcaiton : ( " + i);
        if (i != 1) {
            var user = {};
            user.telphone = $("#inputphone2").val().trim();
            userInfoService.existTelphone(user).success(function (data) {
                //存在true
                if (!data.success) {

                    $scope.user.telphone = user.telphone;
                    $scope.user.code = 123;
                    alert("可以2222,获取验证，" + $scope.user.code);
                    //sendT(user.telphone);
                } else {
                    alert("手机已存在");
                }
            })
        } else {
            alert("可以1111,获取验证，");
            sendT($scope.user.telphone);

        }

    };

    function sendT(telphone) {
        var tel_regexp = /^1([38]\d|5[0-35-9]|7[3678])\d{8}$/;
        var telphone = telphone;
        console.log("telphone --: " + telphone);
        if (tel_regexp.test(telphone)) {
            $http.get("user/sendMsmVerificationCode.do?telphone=" + telphone).success(function (response) {
                $scope.user.telphone = telphone;
                $scope.codeJson = JSON.parse(response);
                $scope.user.code = parseInt($scope.codeJson);
                console.log("code: " + $scope.user.code);
                alert("短信已经发送，请查看手机。如果没有收到，请从新发送。");
            });
        } else {
            alert("请输入正确格式手机号码");
        }
    }

    //自己手机验证1
    $scope.nextStep = function () {
        console.log("msg 1: " + $scope.codeT);
        console.log("msg 2: " + $scope.user.code);
        if ($scope.codeT != null && $scope.codeT == $scope.user.code) {
            window.location.href = "home-setting-address-phone.html";
        } else {
            alert("验证码不正确");
        }
    };

    //新手机验证2
    $scope.nextStep2 = function () {
        alert("msg 1: " + $scope.codeT);
        alert("msg 2: " + $scope.user.code);
        if ($scope.codeT != null && $scope.codeT == $scope.user.code) {
            userInfoService.update($scope.user).success(function (response) {
                console.log("re : " + $scope.user.password);
                if (response.success) {
                    alert("sc成功");
                } else {
                    alert(response.message());
                }
            });
            alert("ns: " + $scope.user.telphone);
            window.location.href = "home-setting-address-complete.html";
        } else {
            alert("验证码不正确")
        }
    }

});

