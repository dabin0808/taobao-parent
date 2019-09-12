app.service('tokenService', function ($http) {

    this.getUserToken = function () {
        return $http.get("getToken.do");

    }
});