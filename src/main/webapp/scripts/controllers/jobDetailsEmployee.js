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

            (function () {

                var lvl = Math.floor($scope.job.employer.reputation / 10);
                var exp = ($scope.job.employer.reputation % 10) * 0.1;

                var bar = new ProgressBar.Circle(container, {
                    color: '#61B329',
                    strokeWidth: 4,
                    trailWidth: 1,
                    easing: 'easeInOut',
                    duration: 1400,
                    text: {
                        autoStyleContainer: false
                    },
                    from: {color: '#61B329', width: 1},
                    to: {color: '#61B329', width: 4},

                    step: function (state, circle) {
                        circle.path.setAttribute('stroke', state.color);
                        circle.path.setAttribute('stroke-width', state.width);
                        circle.setText('lvl ' + lvl);

                    }
                });
                bar.text.style.fontFamily = '"Raleway", Helvetica, sans-serif';
                bar.text.style.fontSize = '2rem';

                bar.animate(exp);

                $($('#container').find('svg')[0]).hide();
            })();

        }]);