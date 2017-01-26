angular.module('jobzz')
    .controller('HistoryEmployeeCtrl', ['$scope', function ($scope) {

        $scope.jobs = [{
            status: 7,
            employerPosting: {
                name: "test",
                startDate: new Date(),
                endDate: new Date
            },
            price: 10,
            currency: 'USD'
        }];

    }]);