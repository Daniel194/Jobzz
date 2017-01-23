(function () {
    'use strict';

    angular.module('jobzz').directive('userReview', function () {
        return {
            restrict: 'EA',
            templateUrl: 'scripts/directives/user-reviews/userReviews.html',
            scope: {
                reviews: '='
            }
        }
    })
})();