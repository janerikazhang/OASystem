/*global define, document*/

define(['require',
    'js/directives/attendance',
    'js/controllers/attendanceCtrl',
    'js/directives/fileModelDirective',
    'js/controllers/fileModelCtrl'
], function (require, attendanceDirective, attendanceController, fileModelDirective, fileModelController) {
  'use strict';

  var angular = require('angular');
  //var ngGrid = require('ng-grid');
  //var filters = require('filters');
  //var services = require('services');
  //var directives = require('directives/attendance');
  //var controllers = require('controllers');
  // Declare app level module which depends on filters, and services
  var module = angular.module('myapp', ['ngGrid']);
  //filters(module);
  //services(module);
    attendanceDirective(module);
    attendanceController(module);
    //fileModelDirective(module);
    //fileModelController(module);
  console.log(module);

  //module.config(['$routeProvider', function($routeProvider) {
  //      $routeProvider.when('/view1', {templateUrl: 'partials/partial1.html', controller: MyCtrl1});
  //      $routeProvider.when('/view2', {templateUrl: 'partials/partial2.html', controller: MyCtrl2});
  //      $routeProvider.otherwise({redirectTo: '/view1'});
  //    }]);

  return {
    module: module
  }
});



