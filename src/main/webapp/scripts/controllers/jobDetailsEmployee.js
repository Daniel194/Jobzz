angular.module('jobzz')
    .controller('JobDetailsEmployeeCtrl', ['$scope', '$rootScope', '$http', 'jobService', function ($scope, $rootScope, $http, jobService) {
        $scope.job = jobService.getJob();

    }]);