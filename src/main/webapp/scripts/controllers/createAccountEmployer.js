angular.module('jobzz')
    .controller('CreateAccountEmployerCtrl', ['$scope', '$rootScope', '$http', '$location', function ($scope, $rootScope, $http, $location) {
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
                console.log($scope.employer);
            }
        };

    }]);
