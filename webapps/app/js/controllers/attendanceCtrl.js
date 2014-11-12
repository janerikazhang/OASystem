/* Controllers */
/**
 * @constructor
 */

define(['../models/attendanceList', 'js/services/attendanceService'],function (attendanceList, attendanceService) {
    'use strict';
    return function (module) {
        module.controller('oa-controller',
            ['$scope', '$http',
                function ($scope, $http) {
                    //init
                    $scope.myData = [];
                    $scope.list = { data: 'myData' ,
                        rowHeight: 60,
                        columnDefs: attendanceList.colDefs};
                    //upload file
                    $scope.uploadFile = function(){
                        var file = $scope.myFile;
                        console.log('file is ' + JSON.stringify(file));
                        //var uploadUrl = "/fileUpload";
                        //fileUpload.uploadFileToUrl(file, uploadUrl);
                    };
                    //get data from service
                    attendanceService.getAttendanceInfo(1, $http)
                        .success(function (result) {
                            result = attendanceService.parseJason(result);
                            $scope.myData = result.tableData;
                            //$scope.$apply();
                        });
        }]);
    }
});
