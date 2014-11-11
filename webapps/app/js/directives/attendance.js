/* Directives */
define('attendance', function () {
  'use strict';

  return function (module) {
    module.directive('attendanceTable',
        function () {
          return {
            restrict: 'E',
            replace: true,
            scope: {},
            templateUrl: 'app/js/directives/templates/attendanceDirective.html',
            controller: 'oa-controller'
          }
    })
  }
});