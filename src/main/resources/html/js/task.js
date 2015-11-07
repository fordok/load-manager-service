var app = angular.module('taskApp', ['ngResource']);
var taskController = app.controller('TaskController', function($http, $scope, $resource) {

    $scope.alerts = [];

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };

    $scope.loadData = function() {
        $http.get('http://localhost:8080/api/tasks').
            success(function(data) {
                $scope.tasks = data;
            });
        $http.get('http://localhost:8080/api/types').
            success(function(data) {
                $scope.types = data;
            });
    };

    $scope.loadData();
    $scope.addTask = function(){
        var NewTask = $resource('http://localhost:8080/api/tasks');
        NewTask.save($scope.task, function(task) {
            $scope.alerts.push({type: "success", msg: "Task was added"});
            $scope.loadData();
            $scope.task = {};
        });

    };

    $scope.httpMethods = ["GET", "POST", "PUT", "DELETE"];

});