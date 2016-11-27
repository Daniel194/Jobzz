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


    }])
    .controller('ProfileEmployerCtrl', ['$scope', '$rootScope', '$http', '$location', function ($scope, $rootScope, $http, $location) {


    }])
    .controller('SettingsEmployerCtrl', ['$scope', '$rootScope', '$http', '$location', function ($scope, $rootScope, $http, $location) {


    }])
    .controller('NewPostEmployerCtrl', ['$scope', '$rootScope', '$http', '$location', function ($scope, $rootScope, $http, $location) {
        $scope.currentDate = new Date();

        $scope.minDate = new Date(
            $scope.currentDate.getFullYear(),
            $scope.currentDate.getMonth(),
            $scope.currentDate.getDate());

        $scope.maxDate = new Date(
            $scope.currentDate.getFullYear() + 10,
            $scope.currentDate.getMonth(),
            $scope.currentDate.getDate());

        $scope.closeDialog = function () {
            $rootScope.panelRef && $rootScope.panelRef.close().then(function () {
                angular.element(document.querySelector('.dialog-button')).focus();
                $rootScope.panelRef.destroy();
            });
        };

        $scope.newPost = function () {
            console.log($scope.post);
        }

    }]);