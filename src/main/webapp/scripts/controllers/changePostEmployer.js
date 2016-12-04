angular.module('jobzz')
    .controller('ChangePostEmployerCtrl', ['$scope', '$rootScope', '$http', 'postService', function ($scope, $rootScope, $http, postService) {
        $scope.currentDate = new Date();

        $scope.minDate = new Date(
            $scope.currentDate.getFullYear(),
            $scope.currentDate.getMonth(),
            $scope.currentDate.getDate());

        $scope.maxDate = new Date(
            $scope.currentDate.getFullYear() + 10,
            $scope.currentDate.getMonth(),
            $scope.currentDate.getDate());

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

        $scope.post = $.extend(true, {}, postService.getPost());
        $scope.post.endDate = new Date($scope.post.endDate);
        $scope.post.startDate = new Date($scope.post.startDate);
        $scope.latlng = [$scope.post.latitude, $scope.post.longitude];

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


        $scope.changePost = function () {

            if ($scope.post.startDate <= $scope.post.endDate) {

                // var req = {
                //     method: 'POST',
                //     dataType: 'json',
                //     url: '/employer/new/post',
                //     headers: {
                //         'Content-Type': 'application/json; charset=utf-8'
                //     },
                //     data: $scope.post
                // };
                //
                // $http(req).then(function (response) {
                //
                //     if (response.data.isCreated) {
                //         $rootScope.getPosts();
                //         $scope.closeDialog();
                //     }
                //
                // }, function () {
                //     console.log('Fail to create new post');
                // });
            }
        }

    }]);