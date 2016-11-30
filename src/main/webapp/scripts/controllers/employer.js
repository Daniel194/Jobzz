angular.module('jobzz')
    .controller('EmployerCtrl', ['$scope', '$rootScope', '$http', '$location', '$mdPanel', 'EmployerAuthSharedService', function ($scope, $rootScope, $http, $location, $mdPanel, EmployerAuthSharedService) {
        $scope.currentNavItem = 'home';

        $scope.newPost = function () {
            var position = $mdPanel.newPanelPosition()
                .absolute()
                .center();

            var config = {
                attachTo: angular.element(document.body),
                controller: 'NewPostEmployerCtrl',
                controllerAs: 'NewPostEmployerCtrl',
                templateUrl: '/views/employer/newpost.html',
                hasBackdrop: true,
                panelClass: 'new-post',
                position: position,
                clickOutsideToClose: true,
                escapeToClose: true
            };

            $mdPanel.open(config).then(function (result) {
                $rootScope.panelRef = result;
            });
        };


        $scope.logoutEmployer = function () {
            $http.post('/employer/logout', {}).finally(function () {
                EmployerAuthSharedService.logout();
            });
        };

    }])
    .controller('HomeEmployerCtrl', ['$scope', '$rootScope', '$http', '$location', function ($scope, $rootScope, $http, $location) {


        $rootScope.getPosts = function () {
            var req = {
                method: 'GET',
                dataType: 'json',
                url: '/employer/all/posts',
                headers: {
                    'Content-Type': 'application/json; charset=utf-8'
                }
            };

            $http(req).then(function (response) {
                $rootScope.posts = response.data;
            }, function () {
                console.log('Fail to load posts');
            });
        };

        $rootScope.getPosts();

    }])
    .controller('ProfileEmployerCtrl', ['$scope', '$rootScope', '$http', '$location', function ($scope, $rootScope, $http, $location) {


    }])
    .controller('SettingsEmployerCtrl', ['$scope', '$rootScope', '$http', '$location', function ($scope, $rootScope, $http, $location) {


    }])
    .controller('NewPostEmployerCtrl', ['$scope', '$rootScope', '$http', '$location', function ($scope, $rootScope, $http, $location) {
        $scope.currentDate = new Date();
        $scope.post = {};

        $scope.minDate = new Date(
            $scope.currentDate.getFullYear(),
            $scope.currentDate.getMonth(),
            $scope.currentDate.getDate());

        $scope.maxDate = new Date(
            $scope.currentDate.getFullYear() + 10,
            $scope.currentDate.getMonth(),
            $scope.currentDate.getDate());

        $scope.latlng = [44.4266978, 26.1024562];
        $scope.post.latitude = 44.4266978;
        $scope.post.longitude = 26.1024562;

        var getJobs = function () {
            var req = {
                method: 'GET',
                dataType: 'json',
                url: '/register/get/jobs',
                headers: {
                    'Content-Type': 'application/json; charset=utf-8'
                }
            };

            $http(req).then(function (response) {
                $scope.jobs = response.data.jobs;
            }, function () {
                console.log('Fail to load jobs');
            });

        };

        getJobs();

        $scope.getPos = function (event) {
            $scope.post.latitude = event.latLng.lat();
            $scope.post.longitude = event.latLng.lng();

            $scope.latlng = [event.latLng.lat(), event.latLng.lng()];
        };

        $scope.closeDialog = function () {
            $rootScope.panelRef && $rootScope.panelRef.close().then(function () {
                angular.element(document.querySelector('.dialog-button')).focus();
                $rootScope.panelRef.destroy();
            });
        };

        $scope.newPost = function () {

            if ($scope.post.startDate <= $scope.post.endDate) {

                var req = {
                    method: 'POST',
                    dataType: 'json',
                    url: '/employer/new/post',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    },
                    data: $scope.post
                };

                $http(req).then(function (response) {

                    if (response.data.isCreated) {
                        $rootScope.getPosts();
                        $scope.closeDialog();
                    }

                }, function (response) {
                    console.log('Fail to create new post');
                });
            }
        }
    }]);