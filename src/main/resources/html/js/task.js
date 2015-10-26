var app = angular.module('taskApp', []);
var taskController = app.controller('TaskController', function($http,$scope) {
    $http.get('http://localhost:8080/api/tasks').
        success(function(data) {
            $scope.tasks = data;
        });
});