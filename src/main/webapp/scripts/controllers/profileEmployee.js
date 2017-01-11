angular.module('jobzz')
    .controller('ProfileEmployeeCtrl', ['$scope', '$rootScope', '$http', 'dateToStringService', 'userProfilePictureService',
        function ($scope, $rootScope, $http, dateToStringService, userProfilePictureService) {

            $scope.employee = {};
            $scope.responses = {};

            var calculateLvl = function () {

                var lvl = Math.floor($scope.employee.reputation / 10);
                var exp = ($scope.employee.reputation % 10) * 0.1;

                var bar = new ProgressBar.Circle(container, {
                    color: '#61B329',
                    strokeWidth: 4,
                    trailWidth: 1,
                    easing: 'easeInOut',
                    duration: 1400,
                    text: {
                        autoStyleContainer: false
                    },
                    from: {color: '#61B329', width: 1},
                    to: {color: '#61B329', width: 4},

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


            var getAllEmployeeDetails = function () {

                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employee/account/full',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    }
                };

                $http(req).then(function (response) {
                    $scope.employee = response.data;
                    $scope.employee.picture = userProfilePictureService.employeeProfilePicture(response.data.picture);
                    calculateLvl();

                }, function () {
                    // Empty
                });

            };

            var getAllEmployeeReviews = function () {

                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employee/all/reviews',
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
                    // Empty
                });

            };

            getAllEmployeeDetails();

            getAllEmployeeReviews();

        }]);