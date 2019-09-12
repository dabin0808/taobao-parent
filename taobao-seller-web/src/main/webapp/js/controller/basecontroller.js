
app.controller('categoryController',function($scope,categoryService){


    $scope.reload=function(id){
        categoryService.findAll(id).success(
            function (res) {
                $scope.result = res;
            })
    }

        $scope.item=[{id:0,name:"顶级分类列表"}];
        $scope.findAll=function (id,name) {

            $scope.parentId=id;
            categoryService.findAll(id).success(
                function (res) {
                    $scope.result = res;
                })
            var  i;
            for ( i = 0; i < $scope.item.length; i++) {
                if($scope.item[i].id==id){
                        break;
                }
            }
            if (i>=$scope.item.length){
                    $scope.item.push({id:id,name:name});
            }else{
                var j = $scope.item.length-i-1;
                while(j--)  $scope.item.pop();

            }

        }

        $scope.findParent_new=function (id) {
            console.log(2);
            $scope.entity={};
            categoryService.findParent(id).success(
                function (res) {
                   $scope.parentCategory=res;
            })
        }


    $scope.findParent=function (pid,id) {
        console.log(2);
        categoryService.findParent(pid).success(
            function (res) {
                $scope.parentCategory=res;
            });

        categoryService.findOne(id).success(
            function (res) {
                $scope.entity=res;
            })
    }

    $scope.updateCategory=function () {
        console.log(3);
        if ($scope.entity.id!=null) {
            console.log($scope.entity);
            // 更新
            categoryService.updateCategory($scope.entity).success(
                function (res) {
                    $scope.reload($scope.parentId);
                })
        }else {
            $scope.entity.parentId=$scope.parentId;
            console.log($scope.entity);
            //添加
            categoryService.addCategory($scope.entity).success(
                function (res) {
                    $scope.reload($scope.parentId);
                })
        }
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

    $scope.delCategory=function () {
        categoryService.delCategory($scope.checkItem).success(function () {
            $scope.reload($scope.parentId);
            $scope.checkItem=[];
        })
    }

})