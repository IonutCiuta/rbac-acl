var app = angular.module('app', ['ngRoute']);

app.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'main.html',
            controller: 'AppController'
        })
});

app.controller('AppController', ['$scope', function($scope) {
    console.log('App controller initialized');

    $scope.methods = [
        {'text': 'Create', 'value': 0},
        {'text': 'Read', 'value': 1},
        {'text': 'Write', 'value': 2},
        {'text': 'Change permission', 'value': 3}
    ];
    $scope.rights = ['none', 'r', 'w', 'rw'];
    $scope.types = [{'value': 0, 'text': 'Folder'}, {'value': 1, 'text': 'File'}];

    $scope.showContent = function(operation, type) {
        console.log("showContent: " + operation + ", " + type);
        return operation === 2 ||
            (operation === 0 && type === 1);
    };

    $scope.showRights = function(operation) {
        console.log("showRights: " + operation);
        return operation === 3;
    };

    $scope.showType = function(operation) {
        console.log("shotType: " + operation);
        return operation === 3;
    };
}]);