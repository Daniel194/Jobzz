angular.module('jobzz')
    .controller('ReviewEmployerEmployeeCtrl', ['$scope', '$rootScope', '$http', '$location', 'jobService',
        function ($scope, $rootScope, $http, $location, jobService) {

            var employeePosting = jobService.getJob();
            $scope.employer = employeePosting.employerPosting.employer;
            $scope.review = {};
            $scope.review.point = 5;
            $scope.review.employer = $scope.employer;

            $scope.closeDialog = function () {
                $rootScope.panelRef && $rootScope.panelRef.close().then(function () {
                    angular.element(document.querySelector('.dialog-button')).focus();
                    $rootScope.panelRef.destroy();
                });
            };

            $scope.addReview = function () {

                var req = {
                    method: 'POST',
                    dataType: 'json',
                    url: '/employee/review/employer/post',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    },
                    data: {review: $scope.review, employeePosting: employeePosting}
                };

                $http(req).then(function (response) {

                    if (response.data.isCreated) {
                        $location.path('/employee/home').replace();
                        $scope.closeDialog();
                    }

                }, function () {
                    //Empty
                });

            };

        }]);