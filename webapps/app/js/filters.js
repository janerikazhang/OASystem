'use strict';

/* Filters */
define([], function () {
  return function (module) {
    module.filter('interpolate', ['version', function(version) {
      return function (text) {
        return String(text).replace(/\%VERSION\%/mg, version);
      }
    }]);
  }
});

//angular.module('myApp.filters', []).
//  filter('interpolate', ['version', function(version) {
//    return function(text) {
//      return String(text).replace(/\%VERSION\%/mg, version);
//    }
//  }]);
