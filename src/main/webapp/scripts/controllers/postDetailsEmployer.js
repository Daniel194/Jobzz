angular.module('jobzz')
    .controller('PostDetailsEmployerCtrl', ['$scope', '$rootScope', '$http', 'postService', function ($scope, $rootScope, $http, postService) {

        $scope.post = postService.getPost();

    }]);