define ([], function () {

    return {
        uploadFile: function(file, $http) {
            var fd = new FormData();
            fd.append('file', file);
            $http.post("http://localhost:8080/OASystem/servlet/OriginalDataImporter", fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
                .success(function(){
                    console.log('success upload file');
                })
                .error(function(){
                    console.log('fail upload file');
                });
        }
    }
});
