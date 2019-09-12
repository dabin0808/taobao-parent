
app.service("uploadService",function($http){

    this.uploadImage=function(){
        var formdata = new FormData();
        formdata.append('file',file.files[0]);
        return $http({
            url:"../seller/uploadImage.do",
            method:"post",
            data:formdata,
            headers:{'Content-Type':undefined},
            transformRequest:angular.identity

        })
    }

})