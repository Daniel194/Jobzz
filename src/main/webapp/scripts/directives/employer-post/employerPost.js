(function () {
    'use strict';

    angular.module('jobzz').directive('employerPost', function () {
        return {
            restrict: 'E',
            templateUrl: 'scripts/directives/employer-post/employerPost.html',
            scope: {
                post: '=',
                showApplications: '=?'
            },
            controller: function ($scope) {
                $scope.showApplications = angular.isDefined($scope.showApplications) ? $scope.showApplications : true;
            }
        }
    })
})();