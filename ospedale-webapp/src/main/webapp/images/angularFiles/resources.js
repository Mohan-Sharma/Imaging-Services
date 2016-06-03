angular.module('resources.bill', [])
    .factory('billing', ["$http", function ($http) {
        var Billing = {};
        Billing.getLabTets = function (successcb, errorcb) {
            $http.get('../superbilldata?dataCategory=LABTEST').success(successcb).error(errorcb);
        };
        Billing.getDoctor = function (successcb, errorcb) {
            $http.get('../superbilldata?dataCategory=DOCTOR').success(successcb).error(errorcb);
        };
        Billing.getReferral = function (successcb, errorcb) {
            $http.get('../superbilldata?dataCategory=REFERRAL').success(successcb).error(errorcb);
        };
        Billing.searchPatients = function (data,successcb, errorcb) {
            $http.post('../superbillsearchpatient?patientaction=SEARCH',data).success(successcb).error(errorcb);
        };
        Billing.addPatient = function (data,successcb, errorcb) {
            $http.post('../superbillsearchpatient?patientaction=ADD',data).success(successcb).error(errorcb);
        };
        Billing.getGender = function (successcb, errorcb) {
            $http.get('../superbilldata?dataCategory=GENDER').success(successcb).error(errorcb);
        };
        Billing.sendInvoice = function (data,successcb, errorcb) {
            $http.post('../superbill',data).success(successcb).error(errorcb);
        };
        return Billing;
    }])
    .factory('authorizationService', function($http,$location) {
        var auth = {}
        auth.authorize = function(){
            $http.get('../securityauthorization').then(function(response){
                if(!response.data.isBiller){
                    $location.path('/auth');
                }
            })
        }
        return auth;
    });