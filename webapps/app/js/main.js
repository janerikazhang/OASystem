/*global requirejs*/
requirejs.config({
    baseUrl: '../app',
    paths: {
        'app': 'js/app',
        'ngGrid': 'lib/ng-grid/ng-grid-2.0.14.min',
        'angular': 'lib/angular/angular',
        'jquery': 'lib/jquery/jquery-2.1.1.min'
    },
    shim: {
        'ngGrid': {
            deps: ['angular', 'jquery'],
            exports: 'ng-grid'
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