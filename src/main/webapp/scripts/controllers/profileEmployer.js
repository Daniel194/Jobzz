angular.module('jobzz')
    .controller('ProfileEmployerCtrl', ['$scope', '$rootScope', '$http', 'dateToStringService', 'userProfilePictureService',
        function ($scope, $rootScope, $http, dateToStringService, userProfilePictureService) {

            $scope.employer = {};
            $scope.responses = {};

            var getAllEmployerDetails = function () {

                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employer/account/full',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    }
                };

                $http(req).then(function (response) {
                    $scope.employer = response.data;
                    $scope.employer.profilePicture = userProfilePictureService.employerProfilePicture(response.data.profilePicture);

                }, function () {
                    //Empty
                });

            };

            var getAllEmployerReviews = function () {

                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employer/all/reviews',
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

            getAllEmployerDetails();

            getAllEmployerReviews();

            (function () {

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
                    // Set default step function for all animate calls
                    step: function (state, circle) {
                        circle.path.setAttribute('stroke', state.color);
                        circle.path.setAttribute('stroke-width', state.width);
                        circle.setText('lvl 2');

                    }
                });
                bar.text.style.fontFamily = '"Raleway", Helvetica, sans-serif';
                bar.text.style.fontSize = '2rem';

                bar.animate(0.8);  // Number from 0.0 to 1.0

                $($('#container svg')[0]).hide();

            })();


        }]);