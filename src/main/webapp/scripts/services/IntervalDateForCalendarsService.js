angular.module('jobzz').service('intervalDateForCalendarsService', function () {

    var currentDate = new Date();

    var minDate = new Date(
        currentDate.getFullYear() - 100,
        currentDate.getMonth(),
        currentDate.getDate());

    var maxDate = new Date(
        currentDate.getFullYear(),
        currentDate.getMonth(),
        currentDate.getDate());

    var minDateExp = new Date(
        currentDate.getFullYear(),
        currentDate.getMonth(),
        currentDate.getDate());

    var maxDateExp = new Date(
        currentDate.getFullYear() + 10,
        currentDate.getMonth(),
        currentDate.getDate());


    function getDates() {
        return {
            minDate: minDate,
            maxDate: maxDate,
            minDateExp: minDateExp,
            maxDateExp: maxDateExp
        };
    }

    return {
        getDates: getDates
    };

});
