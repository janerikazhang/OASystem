/* Controllers */
/**
 * @constructor
 */

define(['../models/attendanceList',
    'js/services/attendanceService',
    'js/services/uploadFileService'],
    function (attendanceList, attendanceService, uploadFileService) {
    'use strict';
    return function (module) {
        module.controller('oa-controller',
            ['$scope', '$http',
                function ($scope, $http) {
                    //init
                    $scope.myData = [];
                    $scope.list = { data: 'myData' ,
                        rowHeight: 50,
                        columnDefs: attendanceList.colDefs,
                        enableColumnResize : true
                    };
                    //upload file
                    $scope.uploadFile = function(){
                        var file = $scope.myFile;
                        console.log('file is ' + JSON.stringify(file));
                        uploadFileService.uploadFile(file, $http);
                    };
                    //get data from service
                    attendanceService.getAttendanceInfo(1, $http)
                        .success(function (result) {
                            console.log('get result ', result)
                            result = attendanceService.parseJason(result);
                            $scope.myData = result.tableData;
                        });
        }]);
    }
});
