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
                templateUrl: '/views/employer/newPost.html',
                hasBackdrop: true,
                panelClass: 'new-post',
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


        $scope.logoutEmployer = function () {
            $http.post('/employer/logout', {}).finally(function () {
                EmployerAuthSharedService.logout();
            });
        };

    }])
    .controller('ProfileEmployerCtrl', ['$scope', '$rootScope', '$http', '$location', function ($scope, $rootScope, $http, $location) {


    }])
    .controller('SettingsEmployerCtrl', ['$scope', '$rootScope', '$http', '$location', function ($scope, $rootScope, $http, $location) {


    }]);
