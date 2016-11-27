angular.module('jobzz')
    .controller('RegisterEmployerCtrl', ['$scope', '$rootScope', '$http', '$location', function ($scope, $rootScope, $http, $location) {
        $scope.currentDate = new Date();

        $scope.minDate = new Date(
            $scope.currentDate.getFullYear() - 100,
            $scope.currentDate.getMonth(),
            $scope.currentDate.getDate());

        $scope.maxDate = new Date(
            $scope.currentDate.getFullYear(),
            $scope.currentDate.getMonth(),
            $scope.currentDate.getDate());

        $scope.minDateExp = new Date(
            $scope.currentDate.getFullYear(),
            $scope.currentDate.getMonth(),
            $scope.currentDate.getDate());

        $scope.maxDateExp = new Date(
            $scope.currentDate.getFullYear() + 10,
            $scope.currentDate.getMonth(),
            $scope.currentDate.getDate());

        $scope.newAccount = function () {
            if ($scope.employer.password === $scope.employer.repeatPassword) {

                var req = {
                    method: 'POST',
                    dataType: 'json',
                    url: '/register/employer',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    },
                    data: $scope.employer
                };

                $http(req).then(function (response) {

                    if (response.data.isCreated) {
                        $rootScope.isCreated = true;
                        $location.path('/login').replace();
                    } else {
                        $scope.error = true;
                        $scope.errorMessage = $scope.employer.email + " already exist !";
                    }

                }, function (response) {
                    $scope.error = true;
                    $scope.errorMessage = "A problem has happened during recording. Please try again.";
                });
            }
        };

    }]);
