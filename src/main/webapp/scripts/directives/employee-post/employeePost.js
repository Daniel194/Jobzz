(function () {
    'use strict';

    angular.module('jobzz').directive('employeePost', function () {
        return {
            restrict: 'E',
            templateUrl: 'scripts/directives/employee-post/employeePost.html',
            scope: {
                job: '='
            }
        }
    })
})();