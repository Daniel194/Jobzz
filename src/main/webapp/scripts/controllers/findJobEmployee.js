angular.module('jobzz')
    .controller('FindJobEmployeeCtrl', ['$scope', '$rootScope', '$http', '$location', 'jobService', '$mdPanel',
        function ($scope, $rootScope, $http, $location, jobService, $mdPanel) {
            var currentDate = new Date();
            $scope.job = {};

            $scope.minDate = new Date(
                currentDate.getFullYear(),
                currentDate.getMonth(),
                currentDate.getDate());

            $scope.maxDate = new Date(
                currentDate.getFullYear() + 10,
                currentDate.getMonth(),
                currentDate.getDate());

            $scope.job.startDate = $scope.minDate;
            $scope.job.endDate = $scope.maxDate;

            (function () {
                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employee/all/available/jobs',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    }
                };

                $http(req).then(function (response) {

                    $scope.jobs = response.data;

                });

            })();

            $scope.findJob = function () {

                var req = {
                    method: 'POST',
                    dataType: 'json',
                    url: '/employee/find/available/jobs',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    },
                    data: $scope.job
                };

                $http(req).then(function (response) {

                    $scope.jobs = response.data;

                }, function () {
                    // Empty
                });

            };

            $scope.jobDetails = function (job) {

                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employee/allow/new/post',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    }
                };

                $http(req).then(function (response) {

                    if (response.data.isAllow) {

                        jobService.setJob(job);
                        $location.path('/employee/job/details').replace();

                    } else {

                        var position = $mdPanel.newPanelPosition()
                            .absolute()
                            .center();

                        var config = {
                            attachTo: angular.element(document.body),
                            controller: 'WarningNewPostEmployeeCtrl',
                            controllerAs: 'WarningNewPostEmployeeCtrl',
                            templateUrl: '/views/employee/warningNewPost.html',
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

                    }

                });

            };

        }]);
