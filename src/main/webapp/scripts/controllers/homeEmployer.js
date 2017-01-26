angular.module('jobzz')
    .controller('HomeEmployerCtrl', ['$scope', '$rootScope', '$http', '$location', 'postService',
        function ($scope, $rootScope, $http, $location, postService) {

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
                });
            };

            $rootScope.getPosts();

            $scope.details = function (post) {

                postService.setPost(post);
                $location.path('/employer/post/details').replace();

            };

        }]);
