/* Controllers */
/**
 * @constructor
 */

define(['js/models/attendanceList', 'js/services/attendanceService'],function (attendanceList, attendanceService) {
    'use strict';
    return function (module) {
        module.controller('oa-controller',
            ['$scope', '$http',
                function ($scope, $http) {
                    //get data from service
                    $scope.myData = [];
                    $scope.list = { data: 'myData' ,
                        rowHeight: 60,
                        columnDefs: attendanceList.colDefs};
                    attendanceService.getAttendanceInfo(1, $http)
                        .success(function (result) {
                            result = attendanceService.parseJason(result);
                            $scope.myData = result.tableData;
                            //$scope.$apply();
                        });
        }]);
    }
});
