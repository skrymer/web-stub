'use strict';

var webStubModule = angular

    .module('webStub', ['webStub.filters', 'webStub.services', 'webStub.directives', 'ngRoute'])

    .config(function($routeProvider) {
        $routeProvider.when('/home', {templateUrl: 'partials/home.html'});
        $routeProvider.when('/createStub', {templateUrl: 'partials/createStub.html', controller: StubCreationController});
        $routeProvider.when('/stubManagement', {templateUrl: 'partials/stubManagement.html', controller: StubManagementController});
        $routeProvider.otherwise({redirectTo: '/home'});
    });
