angular.module('jobzz')
    .controller('RegisterEmployerCtrl', ['$scope', '$rootScope', '$http', '$location', 'intervalDateForCalendarsService',
        function ($scope, $rootScope, $http, $location, intervalDateForCalendarsService) {

            var dates = intervalDateForCalendarsService.getDates();

            $scope.minDate = dates.minDate;
            $scope.maxDate = dates.maxDate;
            $scope.minDateExp = dates.minDateExp;
            $scope.maxDateExp = dates.maxDateExp;

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

                    }, function () {
                        $scope.error = true;
                        $scope.errorMessage = "A problem has happened during recording. Please try again.";
                    });
                }
            };

        }]);
