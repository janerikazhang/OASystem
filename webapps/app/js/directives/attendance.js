/* Directives */
define([], function () {
  'use strict';

  return function (module) {
    module.directive('attendanceTable',
        ['$parse', function ($parse) {
          return {
            restrict: 'AEC',
            replace: true,
            scope: {},
            templateUrl: 'js/directives/templates/attendanceDirective.html',
            controller: 'oa-controller',
            link: function (scope, element, attrs) {
              var model = $parse("myFile");
              var modelSetter = model.assign;

              element.bind('change', function () {
                scope.$apply(function () {
                  modelSetter(scope, element[0].childNodes[1].files[0]);
                });
              });
            }
          }
    }])
  }
});