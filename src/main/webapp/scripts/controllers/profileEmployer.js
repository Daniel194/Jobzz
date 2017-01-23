angular.module('jobzz')
    .controller('ProfileEmployerCtrl', ['$scope', 'profileService', function ($scope, profileService) {

        profileService.getFullAccount('/employer/account/full').then(function (response) {
            $scope.employer = response;
        });

        profileService.getAllReview('/employer/all/reviews').then(function (response) {
            $scope.responses = response;
        });

    }]);