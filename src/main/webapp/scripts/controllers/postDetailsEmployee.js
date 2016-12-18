angular.module('jobzz')
    .controller('PostDetailsEmployeeCtrl', ['$scope', '$rootScope', '$http', '$mdPanel', '$location', 'jobService', 'dateToStringService',
        function ($scope, $rootScope, $http, $mdPanel, $location, jobService, dateToStringService) {

            $scope.job = jobService.getJob();
            $scope.job.date = dateToStringService.dateToString(new Date($scope.job.date));
            $scope.latlng = [$scope.job.employerPosting.latitude, $scope.job.employerPosting.longitude];

            $scope.$on('mapInitialized', function (event, map) {
                window.setTimeout(function () {
                    window.google.maps.event.trigger(map, 'resize');
                    map.setCenter(new google.maps.LatLng($scope.job.employerPosting.latitude, $scope.job.employerPosting.longitude));
                }, 100)
            });


            $scope.changePostEmployer = function (post) {
                //TODO
            };

            $scope.reviewEmployer = function () {
                //TODO
            }

        }]);