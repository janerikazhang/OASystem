/*global requirejs*/
requirejs.config({
    baseUrl: '../app',
    paths: {
        'app': 'js/app',
        'ngGrid': 'lib/ng-grid/release/ui-grid.min',
        'angular': 'lib/angular/angular',
        'jquery': 'lib/jquery/jquery-2.1.1.min',
        'angular-file-upload': 'lib/angular-file-upload/angular-file-upload.min',
        'es5-shim': 'lib/es5-shim/es5-shim.min'
    },
    shim: {
        'ngGrid': {
            deps: ['angular', 'jquery'],
            exports: 'ui-grid'
        },
        'angular-file-upload': {
            deps: ['angular', 'es5-shim'],
            exports: 'angular-file-upload'
        },
        'angular': {
            deps: ['jquery'],
            exports: 'angular'
        },

        'jquery': {
            exports: '$'
        }
    }
});

// Start loading the main app file. Put all of
// your application logic in there.
requirejs(['angular', 'ngGrid'], function (angular, ngGrid) {

    'use strict';

    require(['app'], function (app) {
        angular.element(document).ready(function () {
            angular.bootstrap(window.document, [app.module.name]);
        });
    });
});