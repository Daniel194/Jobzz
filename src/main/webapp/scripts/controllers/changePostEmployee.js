angular.module('jobzz')
    .controller('ChangePostEmployeeCtrl', ['$scope', '$rootScope', '$http', 'jobService', 'CURRENCIES',
        function ($scope, $rootScope, $http, jobService, CURRENCIES) {

            $scope.post = jobService.getJob();
            $scope.currencies = CURRENCIES;

            $scope.closeDialog = function () {
                $rootScope.panelRef && $rootScope.panelRef.close().then(function () {
                    angular.element(document.querySelector('.dialog-button')).focus();
                    $rootScope.panelRef.destroy();
                });
            };

            $scope.changePost = function () {
                console.log($scope.post);
            }

        }]);