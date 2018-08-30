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
        {'text': 'Change permission', 'value': 3},
        {'text': 'Create role', 'value': 4},
        {'text': 'Change rights', 'value': 5},
        {'text': 'Assign role', 'value': 6},
        {'text': 'Add rights', 'value': 7},

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

    $scope.cleanup = function() {
         $scope.operation = ""
         $scope.username = ""
         $scope.password = ""
         $scope.name = ""
         $scope.type = ""
         $scope.permission = ""
         $scope.content = ""
         $scope.role = ""
         $scope.assignee = ""
         console.log("Cleanup complete!")
    }

    function getOperationPath() {
        var root = 'http://localhost:8080/sci/hw/resource/' + $scope.username;
        var path = ['/create', '/read', '/write', '/rights',
                    '/create_role', '/role_rights', '/assign_role', '/add_role'];
        var params = [
            '?name=' + $scope.name + '&type=' + $scope.type + '&value=' + ($scope.content ? $scope.content : ''),
            '?name=' + $scope.name,
            '?name=' + $scope.name + '&value=' + $scope.content,
            '?name=' + $scope.name + '&rights=' + $scope.permission,
            '?role=' + $scope.role,
            '?role=' + $scope.role + '&rights=' + $scope.permission,
            '?role=' + $scope.role + '&assignee=' + $scope.assignee,
            '?role=' + $scope.role + '&resource=' + $scope.name
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