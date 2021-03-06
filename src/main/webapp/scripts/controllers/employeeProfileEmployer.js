angular.module('jobzz')
    .controller('EmployeeProfileEmployerCtrl', ['$scope', '$rootScope', '$http', 'employeeProfileService', 'userProfilePictureService',
        'dateToStringService',
        function ($scope, $rootScope, $http, employeeProfileService, userProfilePictureService, dateToStringService) {

            $scope.employee = employeeProfileService.getEmployee();
            $scope.employee.picture = userProfilePictureService.employeeProfilePicture($scope.employee.picture);
            $scope.responses = {};

            (function () {

                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employer/employee/' + $scope.employee.employeeId,
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    }
                };

                $http(req).then(function (response) {
                    $scope.responses = response.data;

                    $scope.responses.forEach(function (response) {
                        response.review.date = dateToStringService.dateToString(new Date(response.review.date));
                    });

                });

            })();

        }]);