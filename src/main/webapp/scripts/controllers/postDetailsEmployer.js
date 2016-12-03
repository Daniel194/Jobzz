angular.module('jobzz')
    .controller('PostDetailsEmployerCtrl', ['$scope', '$rootScope', '$http', 'postService', function ($scope, $rootScope, $http, postService) {
        $scope.post = postService.getPost();
        $scope.latlng = [$scope.post.latitude, $scope.post.longitude];

        $scope.post.employeePostings = [{
            price: 10,
            currency: '$',
            date: '2016-11-30',
            comment: 'Ma intereseaza acest anunt',
            employee: {
                firstName: 'Matei',
                lastName: 'Popescu',
                reputation: 10
            }

        }, {
            price: 13,
            currency: '$',
            date: '2016-11-29',
            comment: 'Blabla blabla blabla',
            employee: {
                firstName: 'Tudor',
                lastName: 'Parvu',
                reputation: 12
            }
        }, {
            price: 11,
            currency: '$',
            date: '2016-11-28',
            comment: 'Test tes tes',
            employee: {
                firstName: 'Cristi',
                lastName: 'Voicu',
                reputation: 10
            }
        }]

    }]);