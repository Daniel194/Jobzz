angular.module('jobzz')
    .controller('ApplyToJobEmployeeCtrl', ['$scope', '$rootScope', '$http', 'jobService', 'CURRENCIES', function ($scope, $rootScope, $http, jobService, CURRENCIES) {
        $scope.job = jobService.getJob();
        $scope.currencies = CURRENCIES;

        $scope.apply = function () {
            //TODO
        };

        $scope.closeDialog = function () {
            $rootScope.panelRef && $rootScope.panelRef.close().then(function () {
                angular.element(document.querySelector('.dialog-button')).focus();
                $rootScope.panelRef.destroy();
            });
        };
    }]);