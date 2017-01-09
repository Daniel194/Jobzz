angular.module('jobzz')
    .controller('JobDetailsEmployeeCtrl', ['$scope', '$rootScope', '$http', '$mdPanel', 'jobService', 'employerProfileService', '$location',
        'userProfilePictureService',
        function ($scope, $rootScope, $http, $mdPanel, jobService, employerProfileService, $location, userProfilePictureService) {

            $scope.job = jobService.getJob();
            $scope.latlng = [$scope.job.latitude, $scope.job.longitude];
            $scope.job.employer.profilePicture = userProfilePictureService.employerProfilePicture($scope.job.employer.profilePicture);

            $scope.$on('mapInitialized', function (event, map) {
                window.setTimeout(function () {
                    window.google.maps.event.trigger(map, 'resize');
                    map.setCenter(new google.maps.LatLng($scope.job.latitude, $scope.job.longitude));
                }, 100)
            });

            $scope.apply = function () {
                var position = $mdPanel.newPanelPosition()
                    .absolute()
                    .center();

                var config = {
                    attachTo: angular.element(document.body),
                    controller: 'ApplyToJobEmployeeCtrl',
                    controllerAs: 'ApplyToJobEmployeeCtrl',
                    templateUrl: '/views/employee/applyToJob.html',
                    hasBackdrop: true,
                    panelClass: 'change-post',
                    position: position,
                    clickOutsideToClose: true,
                    escapeToClose: true,
                    disableParentScroll: true,
                    trapFocus: true
                };

                $mdPanel.open(config).then(function (result) {
                    $rootScope.panelRef = result;
                });
            };

            $scope.employerProfile = function (employer) {
                employerProfileService.setEmployer(employer);
                $location.path('/employee/employer/profile').replace();

            };

        }]);