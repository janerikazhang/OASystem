define([], function () {
    'use strict';

    return function (module) {
        module.directive('fileModelDirective', ['$parse', function ($parse) {
            return {
                restrict: 'AEC',
                templateUrl: 'js/directives/templates/fileModelDirective.html',
                controller: 'file-model-controller',
                link: function (scope, element, attrs) {
                    var model = $parse(attrs.fileModel);
                    var modelSetter = model.assign;

                    element.bind('change', function () {
                        scope.$apply(function () {
                            modelSetter(scope, element[0].files[0]);
                        });
                    });
                }
            };
        }]);
    };
});
