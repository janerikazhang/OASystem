/* Directives */
define([], function () {
  'use strict';

  return function (module) {
    module.directive('attendanceTable',
        function () {
          return {
            restrict: 'E',
            replace: true,
            scope: {},
            templateUrl: 'js/directives/templates/attendanceDirective.html',
            controller: 'oa-controller'
          }
    })
  }
});