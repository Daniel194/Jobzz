angular.module('jobzz')
    .controller('PayEmployeePostEmployerCtrl', ['$scope', '$rootScope', '$http', 'payEmployeePostService', function ($scope, $rootScope, $http, payEmployeePostService) {
        $scope.review = {};
        $scope.review.point = 5;
        $scope.employeePost = payEmployeePostService.getPost();

        $scope.closeDialog = function () {
            $rootScope.panelRef && $rootScope.panelRef.close().then(function () {
                angular.element(document.querySelector('.dialog-button')).focus();
                $rootScope.panelRef.destroy();
            });
        };

        $scope.payEmployee = function () {
            payEmployeePostService.setPostIsPaid(true);
            $scope.closeDialog();
            //TODO
        }

    }]);