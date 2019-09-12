app.service('commentService',function ($http) {
    
    //得到商品的所有评论
    this.findByStar = function (goodsId,star) {
        return $http.get("comment/findByStar.do?goodsId="+goodsId+"&star="+star);
    }

    //添加一条评论
    this.addComment = function (entity) {
        return $http.post("comment/addComment.do",entity);
    }
});