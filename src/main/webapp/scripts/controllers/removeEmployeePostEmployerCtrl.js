angular.module('jobzz')
    .controller('RemoveEmployeePostEmployerCtrl', ['$scope', '$rootScope', '$http', 'removeEmployeePostService', function ($scope, $rootScope, $http, removeEmployeePostService) {
        $scope.review = {};
        $scope.review.rating = 5;
        $scope.employeePost = removeEmployeePostService.getPost();

        $scope.closeDialog = function () {
            $rootScope.panelRef && $rootScope.panelRef.close().then(function () {
                angular.element(document.querySelector('.dialog-button')).focus();
                $rootScope.panelRef.destroy();
            });
        };

        $scope.addReview = function () {
            removeEmployeePostService.setPostIsDeleted(true);
            $scope.closeDialog();
            //TODO
        }

    }]);