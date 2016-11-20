(function () {
    'use strict';
    var directiveId = 'ngMatch';
    angular.module('jobzz').directive(directiveId, ['$parse', function ($parse) {

        return {
            link: link,
            restrict: 'A',
            require: '?ngModel'
        };

        function link(scope, elem, attrs, ctrl) {
            if (!ctrl) return;
            if (!attrs[directiveId]) return;

            var firstPassword = $parse(attrs[directiveId]);

            var validator = function (value) {
                var temp = firstPassword(scope),
                    v = value === temp;
                ctrl.$setValidity('match', v);
                return value;
            };

            ctrl.$parsers.unshift(validator);
            ctrl.$formatters.push(validator);
            attrs.$observe(directiveId, function () {
                validator(ctrl.$viewValue);
            });

        }
    }]);
})();