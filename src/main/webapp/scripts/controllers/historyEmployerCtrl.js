angular.module('jobzz')
    .controller('HistoryEmployerCtrl', ['$scope', function ($scope) {

        $scope.posts = [{
            name: 'Test',
            description: 'Test',
            startDate: new Date(),
            endDate: new Date,
            status: 3,
            employeePostings: []
        }];

    }]);