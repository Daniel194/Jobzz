angular.module('jobzz')
    .controller('HistoryEmployeeCtrl', ['$scope', '$http', function ($scope, $http) {

        (function () {
            var req = {
                method: 'GET',
                dataType: 'json',
                url: '/employee/history/post',
                headers: {
                    'Content-Type': 'application/json; charset=utf-8'
                }
            };

            $http(req).then(function (response) {
                $scope.jobs = response.data;
            });
        })();

    }]);