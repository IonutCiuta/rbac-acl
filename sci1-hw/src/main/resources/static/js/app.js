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

    $scope.methods = ['Create', 'Read', 'Write', 'Change permission'];
    $scope.rights = ['none', 'r', 'w', 'rw'];
    $scope.types = [{'value': 0, 'text': 'Folder'}, {'value': 1, 'text': 'File'}];
}]);