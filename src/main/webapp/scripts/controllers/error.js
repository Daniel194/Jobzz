angular.module('jobzz').controller('ErrorCtrl', function ($scope, $routeParams) {
    $scope.code = $routeParams.code;

    switch ($scope.code) {
        case "403" :
            $scope.message = "Oops! you have come to unauthorised page.";
            break;
        case "404" :
            $scope.message = "Page not found.";
            break;
        default:
            $scope.code = 500;
            $scope.message = "Oops! unexpected error";
    }

});