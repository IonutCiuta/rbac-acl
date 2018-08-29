var app = angular.module('app', ['ngRoute']);

app.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'main.html',
            controller: 'AppController'
        })
});

app.controller('AppController', ['$scope', '$http', function($scope, $http) {
    console.log('App controller initialized');
    $scope.error = undefined;
    $scope.methods = [
        {'text': 'Create', 'value': 0},
        {'text': 'Read', 'value': 1},
        {'text': 'Write', 'value': 2},
        {'text': 'Change permission', 'value': 3}
    ];
    $scope.rights = ['none', 'r', 'w', 'rw'];
    $scope.types = [{'value': 0, 'text': 'Folder'}, {'value': 1, 'text': 'File'}];

    $scope.run = function() {
        resetOutput();
        sanitizeContent();

        var config = {
            headers: {
                'Content-Type': 'application/json',
            }
        };

        $http.post(getOperationPath(), $scope.password, {})
        .success(function(data, status) {
            $scope.status = status;
            $scope.result = data.text ? data.text : "Success";
            console.log(status + ": " + data.text)
        })
        .error(function(data, status) {
            $scope.status = status;
            $scope.error = data.text;
            console.error(status + ": " + data.text);
        });
    }

    function getOperationPath() {
        var root = 'http://localhost:8080/sci/hw/resource/' + $scope.username;
        var path = ['/create', '/read', '/write', '/rights'];
        var params = [
            '?name=' + $scope.name + '&type=' + $scope.type + '&value=' + ($scope.content ? $scope.content : ''),
            '?name=' + $scope.name,
            '?name=' + $scope.name + '&value=' + $scope.content,
            '?name=' + $scope.name + '&rights=' + $scope.permission
        ];

        var url = root + path[$scope.operation] + params[$scope.operation];
        console.log("Url: " + url);
        return url;
    }

    function resetOutput() {
        $scope.status = undefined;
        $scope.error = undefined;
        $scope.result = undefined;
    }

    function sanitizeContent() {
        if($scope.type == 0) {
            $scope.content = '';
        }
    }
}]);