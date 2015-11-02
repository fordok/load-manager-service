var app = angular.module('taskApp', []);
var taskController = app.controller('TaskController', function($http,$scope) {

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
        $http({
            method: 'POST',
            url: 'http://localhost:8080/api/tasks',
            data: $scope.task
        }).then(function successCallback(response) {
            $scope.response = response.data;
            $scope.loadData();
            $scope.task = {};
        }, function errorCallback(response) {
            $scope.errors.push({type: "Error", msg: "Can't add task!"})
        });
    };

    $scope.httpMethods = ["GET", "POST", "PUT", "DELETE"];

});