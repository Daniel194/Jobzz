angular.module('jobzz')
    .controller('ProfileEmployeeCtrl', ['$scope', 'profileService', function ($scope, profileService) {

        profileService.getFullAccount('/employee/account/full').then(function (response) {
            $scope.employee = response;
        });

        profileService.getAllReview('/employee/all/reviews').then(function (response) {
            $scope.responses = response;
        });

    }]);