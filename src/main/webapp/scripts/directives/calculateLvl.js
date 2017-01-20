(function () {
    'use strict';

    angular.module('jobzz').directive('calculateLvl', function () {
        return {
            restrict: 'E',
            template: '<div id="container"></div>',
            scope: {
                reputation: '@',
                color: "@"
            },
            link: function ($scope) {

                $scope.$watch("reputation", function () {
                    var lvl = Math.floor($scope.reputation / 10);
                    var exp = ($scope.reputation % 10) * 0.1;

                    var bar = new ProgressBar.Circle(container, {
                        color: $scope.color,
                        strokeWidth: 4,
                        trailWidth: 1,
                        easing: 'easeInOut',
                        duration: 1400,
                        text: {
                            autoStyleContainer: false
                        },
                        from: {color: $scope.color, width: 1},
                        to: {color: $scope.color, width: 4},

                        step: function (state, circle) {
                            circle.path.setAttribute('stroke', state.color);
                            circle.path.setAttribute('stroke-width', state.width);
                            circle.setText('lvl ' + lvl);

                        }
                    });
                    bar.text.style.fontFamily = '"Raleway", Helvetica, sans-serif';
                    bar.text.style.fontSize = '2rem';

                    bar.animate(exp);

                    $(document).ready(function () {
                        var container = $('#container');
                        var progressbar_text = container.find('.progressbar-text');

                        $(container.find('svg')[0]).hide();

                        if (progressbar_text.length > 1) {
                            $(progressbar_text[0]).hide();
                        }
                    });

                });
            }
        };
    })
})();