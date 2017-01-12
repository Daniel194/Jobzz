angular.module('jobzz')
    .controller('EmployerCtrl', ['$scope', '$rootScope', '$http', '$location', '$mdPanel', 'EmployerAuthSharedService',
        'userProfilePictureService',
        function ($scope, $rootScope, $http, $location, $mdPanel, EmployerAuthSharedService, userProfilePictureService) {
            $scope.currentNavItem = 'home';

            $scope.newPost = function () {

                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employer/allow/new/post',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    }
                };

                $http(req).then(function (response) {

                    var position = $mdPanel.newPanelPosition()
                        .absolute()
                        .center();

                    var config;

                    if (response.data.isAllow) {

                        config = {
                            attachTo: angular.element(document.body),
                            controller: 'NewPostEmployerCtrl',
                            controllerAs: 'NewPostEmployerCtrl',
                            templateUrl: '/views/employer/newPost.html',
                            hasBackdrop: true,
                            panelClass: 'new-post',
                            position: position,
                            clickOutsideToClose: true,
                            escapeToClose: true,
                            disableParentScroll: true,
                            trapFocus: true
                        };

                    } else {

                        config = {
                            attachTo: angular.element(document.body),
                            controller: 'WarningNewPostEmployerCtrl',
                            controllerAs: 'WarningNewPostEmployerCtrl',
                            templateUrl: '/views/employer/warningNewPost.html',
                            hasBackdrop: true,
                            panelClass: 'new-post',
                            position: position,
                            clickOutsideToClose: true,
                            escapeToClose: true,
                            disableParentScroll: true,
                            trapFocus: true
                        };

                    }

                    $mdPanel.open(config).then(function (result) {
                        $rootScope.panelRef = result;
                    });

                }, function () {
                    //Empty
                });

            };


            $scope.logoutEmployer = function () {
                $http.post('/employer/logout', {}).finally(function () {
                    EmployerAuthSharedService.logout();
                });
            };

            $scope.navigateTo = function (newPath) {
                $location.path(newPath).replace();
            };

            $scope.openMenu = function ($mdOpenMenu, ev) {
                $mdOpenMenu(ev);
            };

            (function () {
                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employer/account',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    }
                };

                $http(req).then(function (response) {
                    $scope.employer = response.data;
                    $scope.employer.profilePicture = userProfilePictureService.employerProfilePicture($scope.employer.profilePicture);
                }, function () {
                    //Empty
                });
            })();

        }]);
