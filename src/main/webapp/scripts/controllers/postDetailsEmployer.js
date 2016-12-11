angular.module('jobzz')
    .controller('PostDetailsEmployerCtrl', ['$scope', '$rootScope', '$http', '$mdPanel', 'postService', 'dateToStringService', function ($scope, $rootScope, $http, $mdPanel, postService, dateToStringService) {
        $scope.post = postService.getPost();
        $scope.latlng = [$scope.post.latitude, $scope.post.longitude];

        $scope.post.employeePostings.forEach(function (post) {
            post.date = dateToStringService.dateToString(new Date(post.date));
        });

        $scope.$on('mapInitialized', function (event, map) {
            window.setTimeout(function () {
                window.google.maps.event.trigger(map, 'resize');
                map.setCenter(new google.maps.LatLng($scope.post.latitude, $scope.post.longitude));
            }, 100)
        });

        $scope.change = function () {
            var position = $mdPanel.newPanelPosition()
                .absolute()
                .center();

            var config = {
                attachTo: angular.element(document.body),
                controller: 'ChangePostEmployerCtrl',
                controllerAs: 'ChangePostEmployerCtrl',
                templateUrl: '/views/employer/changePost.html',
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


        $scope.delete = function () {
            var position = $mdPanel.newPanelPosition()
                .absolute()
                .center();

            var config = {
                attachTo: angular.element(document.body),
                controller: 'DeletePostEmployerCtrl',
                controllerAs: 'DeletePostEmployerCtrl',
                templateUrl: '/views/employer/deletePost.html',
                hasBackdrop: true,
                panelClass: 'delete-post',
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

        $scope.approved = function (employeePost) {
            employeePost.status = 1;
            updateEmployeePost(employeePost);
        };

        $scope.refused = function (employeePost) {
            employeePost.status = 2;
            updateEmployeePost(employeePost);
        };

        function updateEmployeePost(employeePost) {
            var req = {
                method: 'PUT',
                dataType: 'json',
                url: '/employer/update/employee/post',
                headers: {
                    'Content-Type': 'application/json; charset=utf-8'
                },
                data: employeePost
            };

            $http(req).then(function (response) {

            }, function () {
                console.log('Fail to update');
            });
        }

    }]);