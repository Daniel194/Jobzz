angular.module('jobzz')
    .controller('EmployerProfileEmployeeCtrl', ['$scope', '$rootScope', '$http', 'employerProfileService', 'userProfilePictureService',
        'dateToStringService',
        function ($scope, $rootScope, $http, employerProfileService, userProfilePictureService, dateToStringService) {

            $scope.employer = employerProfileService.getEmployer();
            $scope.employer.profilePicture = userProfilePictureService.employerProfilePicture($scope.employer.profilePicture);
            $scope.responses = {};

            (function () {

                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employee/employer/' + $scope.employer.employerId,
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

            })();

            (function () {

                var lvl = Math.floor($scope.employer.reputation / 10);
                var exp = ($scope.employer.reputation % 10) * 0.1;

                var bar = new ProgressBar.Circle(container1, {
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

                $($('#container1').find('svg')[0]).hide();

            })();

        }]);