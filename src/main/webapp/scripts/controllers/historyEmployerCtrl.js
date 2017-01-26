angular.module('jobzz')
    .controller('HistoryEmployerCtrl', ['$scope', '$http', function ($scope, $http) {

        (function () {
            var req = {
                method: 'GET',
                dataType: 'json',
                url: '/employer/history/posts',
                headers: {
                    'Content-Type': 'application/json; charset=utf-8'
                }
            };

            $http(req).then(function (response) {
                $scope.posts = response.data;
            });
        })();

    }]);