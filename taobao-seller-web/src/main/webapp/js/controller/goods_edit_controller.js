
app.controller('GoodsEditController',function($http,$scope,categoryService,uploadService){


    $scope.itemcat=0;
    $scope.findCat1List=function(){
        // console.log($scope.entity.introduction)
        console.log(0);
        categoryService.findAll(0).success(
            function (res) {
                $scope.itemCat1List = res;
            })
    }

    $scope.$watch('categoryId1',function (newValue,oldValue ) {

        categoryService.findAll(newValue).success(
            function (res) {
                $scope.itemCat2List = res;
            })
    })
    $scope.$watch('categoryId2',function (newValue,oldValue ) {

        categoryService.findAll(newValue).success(
            function (res) {
                $scope.itemCat3List = res;
            })
    })

    $scope.addGoods=function () {
        console.log($scope.entity);
        categoryService.addGoods($scope.entity).success(
            function (res) {
                   $scope.categoryId2=[];
                   $scope.categoryId1=[];
                   $scope.entity={};
                   $scope.entity.bigImg=[];
        })
    }

    $scope.uploadImage=function () {
        uploadService.uploadImage().success(function (res) {
            // alert(res.msg);
            $scope.imgage_url=res.url;
        })
    }


    $scope.entity = {};
    $scope.entity.bigImg=[];
    $scope.saveImage = function () {

        console.log($scope.imgage_url);
        $scope.entity.bigImg.push($scope.imgage_url);
        $scope.imgage_url=[];
        console.log($scope.entity);
    }

    $scope.delImageItem=function (id) {
        let index = $scope.entity.bigImg.indexOf(id);
        $scope.entity.bigImg.splice(index,1);

    }


    $scope.entity.specification={};


    $scope.findother=function(){
        console.log("findother");
        $scope.entity={};
        $http.get("../seller/findSpecification.do?id=1").success(function (res) {
            $scope.entity.specification=JSON.parse(res.specification);
            console.log(typeof $scope.entity.specification);
        })
    }
    $scope.name=[];
    $scope.value=[];
    $scope.addItem=function () {
        $scope.value.push({});

    }


    // $scope.specification={};
    $scope.saves=function () {
        // $scope.entity.specification.push($scope.s);

        if($scope.flag=="true"){

            let x=$scope.name;
            // $scope.specification[x]=[];
            $scope.entity.specification[x]=[];
            // $scope.specification.push(x);
            console.log($scope.entity.specification);

            for (let i = 0; i < $scope.value.length; i++) {

                $scope.entity.specification[x].push($scope.value[i].text);

            }
        }else {
            //这是更新
            let x=$scope.name;

            $scope.entity.specification[$scope.flag]=[];

            console.log($scope.entity.specification);

            for (let i = 0; i < $scope.value.length; i++) {

                $scope.entity.specification[$scope.flag].push($scope.value[i].text);

            }
        }
        $scope.name=[];
        $scope.value=[];

    }
    //新建一个规格
    $scope.new=function(){
        $scope.flag="true";
    }
    
    $scope.formatValue=function (str) {
        var res ="";

        for (let i = 0; i < str.length; i++) {
            if (i!=str.length-1)
                res+=str[i]+",";
            else
                res+=str[i];
        }

        return res;
    }

    $scope.shanchuitem=function(index){
        $scope.value.splice(index,1);
    }




    $scope.checkItem=[];
    $scope.check=function ($event) {
        console.log($event.target);
        if($event.target.checked){
            $scope.checkItem.push($event.target.value)
            console.log( $scope.checkItem);
        }else {
            var index = $scope.checkItem.indexOf($event.target.value);
            $scope.checkItem.splice(index,1);
            console.log( $scope.checkItem);
        }
    }

    $scope.delguige=function () {

        for(let key  in $scope.entity.specification){
            delete $scope.entity.specification[key];
        }

        $scope.checkItem=[];
    }

    //1就是新建 0是更新
    $scope.flag="true";
    $scope.updateItem=function (val) {
        $scope.flag=val;
        $scope.name=val;
        for( let i = 0 ; i <$scope.entity.specification[val].length;i++){

            console.log("res:"+$scope.entity.specification[val][i]);
            let s=$scope.entity.specification[val][i];
            $scope.value.push({text:s});
        }

    }



})