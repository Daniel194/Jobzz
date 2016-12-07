angular.module('jobzz')
    .controller('JobDetailsEmployeeCtrl', ['$scope', '$rootScope', '$http', 'jobService', function ($scope, $rootScope, $http, jobService) {
        $scope.job = jobService.getJob();
        $scope.latlng = [$scope.job.latitude, $scope.job.longitude];

        $scope.$on('mapInitialized', function (event, map) {
            window.setTimeout(function () {
                window.google.maps.event.trigger(map, 'resize');
                map.setCenter(new google.maps.LatLng($scope.job.latitude, $scope.job.longitude));
            }, 100)
        });

        $scope.apply = function () {

        };

    }]);