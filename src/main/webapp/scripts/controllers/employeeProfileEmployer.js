angular.module('jobzz')
    .controller('EmployeeProfileEmployerCtrl', ['$scope', '$rootScope', '$http', 'employeeProfileService', 'userProfilePictureService',
        'dateToStringService',
        function ($scope, $rootScope, $http, employeeProfileService, userProfilePictureService, dateToStringService) {

            $scope.employee = employeeProfileService.getEmployee();
            $scope.employee.picture = userProfilePictureService.employeeProfilePicture($scope.employee.picture);
            $scope.responses = {};

            var calculateLvl = function () {

                var lvl = Math.floor($scope.employee.reputation / 10);
                var exp = ($scope.employee.reputation % 10) * 0.1;

                var bar = new ProgressBar.Circle(container, {
                    color: '#3063A5',
                    strokeWidth: 4,
                    trailWidth: 1,
                    easing: 'easeInOut',
                    duration: 1400,
                    text: {
                        autoStyleContainer: false
                    },
                    from: {color: '#3063A5', width: 1},
                    to: {color: '#3063A5', width: 4},

                    step: function (state, circle) {
                        circle.path.setAttribute('stroke', state.color);
                        circle.path.setAttribute('stroke-width', state.width);
                        circle.setText('lvl ' + lvl);

                    }
                });
                bar.text.style.fontFamily = '"Raleway", Helvetica, sans-serif';
                bar.text.style.fontSize = '2rem';

                bar.animate(exp);

                $($('#container').find('svg')[0]).hide();

            };

            var getAllEmployeeReviews = function () {

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

                }, function () {
                    //Empty
                });

            };

            getAllEmployeeReviews();
            calculateLvl();

        }]);