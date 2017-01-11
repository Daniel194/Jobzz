angular.module('jobzz')
    .controller('PostDetailsEmployeeCtrl', ['$scope', '$rootScope', '$http', '$mdPanel', '$location', 'jobService', 'dateToStringService',
        'employerProfileService', 'userProfilePictureService',
        function ($scope, $rootScope, $http, $mdPanel, $location, jobService, dateToStringService, employerProfileService
            , userProfilePictureService) {

            var reviewEmployerPopUp = function () {
                var position = $mdPanel.newPanelPosition()
                    .absolute()
                    .center();

                var config = {
                    attachTo: angular.element(document.body),
                    controller: 'ReviewEmployerEmployeeCtrl',
                    controllerAs: 'ReviewEmployerEmployeeCtrl',
                    templateUrl: '/views/employee/reviewEmployer.html',
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

            var warningNewReview = function () {
                var position = $mdPanel.newPanelPosition()
                    .absolute()
                    .center();

                var config = {
                    attachTo: angular.element(document.body),
                    controller: 'WarningNewReviewEmployeeCtrl',
                    controllerAs: 'WarningNewReviewEmployeeCtrl',
                    templateUrl: '/views/employee/warningNewReview.html',
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

            $scope.job = jobService.getJob();
            $scope.job.date = dateToStringService.dateToString(new Date($scope.job.date));
            $scope.latlng = [$scope.job.employerPosting.latitude, $scope.job.employerPosting.longitude];

            $scope.job.employerPosting.employer.profilePicture = userProfilePictureService.employerProfilePicture(
                $scope.job.employerPosting.employer.profilePicture);

            $scope.$on('mapInitialized', function (event, map) {
                window.setTimeout(function () {
                    window.google.maps.event.trigger(map, 'resize');
                    map.setCenter(new google.maps.LatLng($scope.job.employerPosting.latitude, $scope.job.employerPosting.longitude));
                }, 100)
            });


            $scope.changePostEmployer = function (post) {
                var position = $mdPanel.newPanelPosition()
                    .absolute()
                    .center();

                var config = {
                    attachTo: angular.element(document.body),
                    controller: 'ChangePostEmployeeCtrl',
                    controllerAs: 'ChangePostEmployeeCtrl',
                    templateUrl: '/views/employee/changePost.html',
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

            $scope.reviewEmployer = function () {

                if ($scope.job.status == 5) {

                    var req = {
                        method: 'GET',
                        dataType: 'json',
                        url: '/employee/allow/new/review/' + $scope.job.employerPosting.employer.employerId,
                        headers: {
                            'Content-Type': 'application/json; charset=utf-8'
                        }
                    };

                    $http(req).then(function (response) {

                        if (response.data.isAllow) {
                            reviewEmployerPopUp();
                        } else {
                            warningNewReview();
                        }

                    }, function () {
                        //Empty
                    });

                } else {
                    reviewEmployerPopUp();
                }

            };

            $scope.employerProfile = function (employer) {
                employerProfileService.setEmployer(employer);
                $location.path('/employee/employer/profile').replace();

            };

            (function () {

                var lvl = Math.floor($scope.job.employerPosting.employer.reputation / 10);
                var exp = ($scope.job.employerPosting.employer.reputation % 10) * 0.1;

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