angular.module('hmis',['ui.bootstrap','ngRoute','resources.bill','mgcrea.ngStrap','angular-loading-bar','ngAnimate'])
    .config(['$routeProvider', '$locationProvider',
        function($routeProvider, $locationProvider) {
            $routeProvider
                .when('/', {
                    templateUrl: '../superBill/templates/demo.tpl.html',
                    controller: 'DemoCtrl',
                    resolve:{
                        requireAuthorization : function(authorizationService){
                            authorizationService.authorize();
                        },
                        labTest: ["$q", "billing", function ($q, billing) {
                            var deferred = $q.defer();
                            billing.getLabTets(function (response, status, headers, config) {
                                    deferred.resolve(response);
                                },
                                function (response, status, headers, config) {
                                    deferred.reject();
                                }
                            );
                            return deferred.promise;
                        }],
                        doctor: ["$q", "billing", function ($q, billing) {
                            var deferred = $q.defer();
                            billing.getDoctor(function (response, status, headers, config) {
                                    deferred.resolve(response);
                                },
                                function (response, status, headers, config) {
                                    deferred.reject();
                                }
                            );
                            return deferred.promise;
                        }],
                        referralDetails: ["$q", "billing", function ($q, billing) {
                            var deferred = $q.defer();
                            billing.getReferral(function (response, status, headers, config) {
                                    deferred.resolve(response);
                                },
                                function (response, status, headers, config) {
                                    deferred.reject();
                                }
                            );
                            return deferred.promise;
                        }],
                        genderList: ["$q", "billing", function ($q, billing) {
                            var deferred = $q.defer();
                            billing.getGender(function (response, status, headers, config) {
                                    deferred.resolve(response);
                                },
                                function (response, status, headers, config) {
                                    deferred.reject();
                                }
                            );
                            return deferred.promise;
                        }]
                    }
                }).when('/auth',{
                    templateUrl: '../superBill/templates/auth.html'
                });

        }])
    .service('TotalBill',  function() {
        var total = [];
        this.setBill= function(items) {
            total = items;
        };
        this.getBill= function() {
            return total;
        };
    })
    .controller('DemoCtrl', ['$scope','labTest','TotalBill','$aside','$alert','$route','doctor','referralDetails','billing','genderList',
        function($scope,labTest,TotalBill,$aside,$alert,$route,doctor,referralDetails,billing,genderList){
            $scope.laboratoryDetails = labTest;
            $scope.doctorDetails = doctor;
            $scope.referralDetails = referralDetails;
            $scope.genderList = genderList;

            $scope.selctedLabDetails = {"total":0,"items":[]};
            $scope.selectedDocDetails = {"total":0,"items":[]};
            $scope.selectedCasualityDetails = {"total":0,"items":[]};
            $scope.selectedRadiologyDetails = {"total":0,"items":[]};
            $scope.casualityItem={show:true};
            $scope.radiologyItem = {show:true};
            $scope.total =0;
            $scope.selectedItem ={
                lab:undefined,
                doc:undefined
            }
            $scope.selectedPatient = {
                show:false,
                patientDetails : undefined
            }
            $scope.noOfRecordsPerPage = 4;
            $scope.startIndex =0;
            $scope.user = {};
            $scope.referral = {details:undefined};

            $scope.openAlert=function(content){
                $scope.alert =  $alert({ content: content, placement: 'top-left', container:'body',duration:"5",type: 'warning', show: true});
            }
            $scope.$watch('selectedItem.lab', function(newValue, oldValue) {
                if(angular.isObject(newValue)){
                    $scope.addToSelectedItem($scope.selctedLabDetails,newValue,'panelName','Lab Type');
                    $scope.selectedItem.lab = undefined;
                }
            });
            $scope.$watch('selectedItem.doc', function(newValue, oldValue) {
                if(angular.isObject(newValue)){
                    $scope.addToSelectedItem($scope.selectedDocDetails,newValue,'firstName','Consultant');
                    $scope.selectedItem.doc = undefined;
                }
            });
            $scope.calculateTotal =  function(selctedObjDetails){
                selctedObjDetails.total = _.reduce(selctedObjDetails.items, function(memo, selectedObj){
                    var amount = isNaN(parseInt(selectedObj.amount))?0:parseInt(selectedObj.amount);
                    return memo + amount;
                }, 0);
                $scope.total = $scope.selctedLabDetails.total + $scope.selectedDocDetails.total + $scope.selectedCasualityDetails.total +$scope.selectedRadiologyDetails.total;
            }
            $scope.addRow = function(rowObj,item,category){
                console.log(category);
                if(item.Type && item.amount){
                    if($scope.alert){
                        $scope.alert.hide();
                    }
                    $scope.openAlert(item.Type+' got added');
                    rowObj.items.push(item);
                    $scope.calculateTotal( rowObj);
                    if('casuality'===category){
                        $scope.casualityItem={show:false};
                    }else if('radiology'===category){
                        $scope.radiologyItem = {show:false};
                    }
                }

            }
            $scope.alterRow = function(rowObj,index){
                //
                if(rowObj.items[index].Type==''){
                    rowObj.items[index].amount = 0;
                }
                $scope.calculateTotal( rowObj);
            }
            $scope.addToSelectedItem = function(selectedDetailsList,selcetedItem,contentKey,categoryType){
                if($scope.alert){
                    $scope.alert.hide();
                }
                $scope.openAlert(selcetedItem[contentKey]+' '+categoryType+' got added');
                selectedDetailsList.items.push(selcetedItem);
                $scope.calculateTotal( selectedDetailsList);
            }
            $scope.removeSelecetedItem = function(selectedDetailsList,selcetedItem,key){
                selectedDetailsList.items = _.filter(selectedDetailsList.items, function(selectedLabDetail){ return !(selectedLabDetail[key] === selcetedItem[key]); });
                $scope.calculateTotal( selectedDetailsList)
            }
            $scope.showTotalBill = function(){
                var referenceId = undefined;
                var referralType = undefined;
                if($scope.referral.details && $scope.referral.details.referralId && $scope.referral.details.referralType){
                    referenceId = $scope.referral.details.referralId;
                    referralType =$scope.referral.details.referralType;
                }
                if($scope.selectedPatient.patientDetails && $scope.selectedPatient.patientDetails.patientId){
                    var item = {
                        "Lab":$scope.selctedLabDetails,
                        "Doc":$scope.selectedDocDetails,
                        "Casuality":$scope.selectedCasualityDetails,
                        "Radiology":$scope.selectedRadiologyDetails,
                        "referredBy":referenceId,
                        "referralType":referralType,
                        "patient": $scope.selectedPatient.patientDetails.patientId,
                        "Total":$scope.total
                    }
                    TotalBill.setBill(item);
                    var myAside = $aside({template: '../superBill/templates/viewSelection.tpl.html',show:true,container:'body',html:true});
                }
            }
            $scope.selectPatient = function(patient){
                $scope.selectedPatient = {
                    show:true,
                    patientDetails : patient
                }
            }
            $scope.resetSelectedPatient = function(){
                $route.reload();
            }
            $scope.searchPatient = function(patient){
                patient = _.omit(patient,'title');
                $scope.startIndex = 0;
                $scope.noOfRecordsPerPage = 4;
                patient.noOfRecordsPerPage = $scope.noOfRecordsPerPage;
                patient.startIndex = $scope.startIndex;
                $scope.isSearchCompleted = undefined;// used in searching more patients
                billing.searchPatients(patient,function (response, status, headers, config) {
                        $scope.patients = response;
                        $scope.showSearch =true;
                    },
                    function (response, status, headers, config) {
                    }
                )
            }
            $scope.searchMorePatients = function(){
                if($scope.patients && !$scope.isSearchCompleted){
                    $scope.showProgressBar = true;
                    $scope.user = _.omit($scope.user,'title');
                    $scope.startIndex = $scope.startIndex + $scope.noOfRecordsPerPage + 1;
                    var patient = angular.copy($scope.user);
                    patient.noOfRecordsPerPage = $scope.noOfRecordsPerPage;
                    patient.startIndex = $scope.startIndex;
                    billing.searchPatients(patient,function (response, status, headers, config) {
                            angular.forEach(response,function(value){
                                $scope.patients.push(value);
                            })
                            $scope.showSearch =true;
                            if(response.length==0){
                                $scope.isSearchCompleted=true;
                            }
                            $scope.showProgressBar = false;
                        },
                        function (response, status, headers, config) {
                            $scope.showProgressBar = false;
                        }
                    );
                }

            }
            $scope.getGenderDescription = function(genderId){
                var genderDetails =   _.findWhere(genderList, {id:genderId})
                if(genderDetails){
                    return genderDetails.description;
                }

            }
            $scope.addPatient = function(patient){
                patient = _.omit(patient, 'mrnNumber');
                angular.forEach(patient,function(value,key){
                    if(value===''|| angular.isUndefined(value)){
                        patient[key]=null;
                    }
                });
                billing.addPatient(patient,function (response, status, headers, config) {
                        $alert({ content: "Patient Added successfully", container:'#alert-box',duration:"5",type: 'success', show: true});
                        patient.patientId =  response.patientId;
                        patient.mrnNumber =  response.mrnNumber;
                        $scope.selectPatient(patient);
                    },
                    function (response, status, headers, config) {
                    }
                )
            }
            $scope.isTotalBillDisabled = function(){
                if( $scope.selectedPatient.patientDetails &&
                    ($scope.selctedLabDetails.items.length >0 ||
                        $scope.selectedDocDetails.items.length >0 ||
                        $scope.selectedCasualityDetails.items.length >0||
                        $scope.selectedRadiologyDetails.items.length >0)){

                    return false;
                }
                return true;
            }
        }
    ]
    ).filter('arrayFilter', function() {
        return function(input, filterContent) {
            return  _.difference(input,filterContent);
        };
    }).controller('asideTotalController', ['$scope','TotalBill','billing','$alert','$window','$route',
        function($scope,TotalBill,billing,$alert,$window,$route){
            $scope.paymentMode = "OPD_CASH";
            $scope.removeInvalidRows = function(){
                $scope.totalBillDetails = angular.copy(TotalBill.getBill());
                $scope.totalBillDetails.Casuality.items =  _.reject($scope.totalBillDetails.Casuality.items, function(item){ return item.Type  == ''; });
                $scope.totalBillDetails.Radiology.items =  _.reject($scope.totalBillDetails.Radiology.items, function(item){ return item.Type  == ''; });
            }
            $scope.calculateTotal =  function(selctedObjDetails){
                selctedObjDetails.total = _.reduce(selctedObjDetails.items, function(memo, selectedObj){
                    var amount = isNaN(parseInt(selectedObj.amount))?0:parseInt(selectedObj.amount);
                    return memo + amount;
                }, 0);
                $scope.totalBillDetails.Total = $scope.totalBillDetails.Lab.total + $scope.totalBillDetails.Doc.total + $scope.totalBillDetails.Casuality.total +$scope.totalBillDetails.Radiology.total;
            }
            $scope.removeInvalidRows();
            $scope.ifInvalidReturnZero = function(amount){
                if(amount==''){
                    return 0;
                }
                return amount;
            }
            $scope.prepareData = function(){
                $scope.hideReceivePayment = true;
                var invoice ={
                    "invoiceItems":[],
                    "payments":
                        [
                            {
                                "paymentMode":$scope.paymentMode,
                                "amount"	 :$scope.totalBillDetails.Total
                            }
                        ],
                    "referredBy": $scope.totalBillDetails.referredBy ? $scope.totalBillDetails.referredBy : null ,
                    "referralType" : $scope.totalBillDetails.referralType ? $scope.totalBillDetails.referralType : null ,
                    "patient": $scope.totalBillDetails.patient
                }
                angular.forEach($scope.totalBillDetails.Lab.items,function(labItem,key){
                    invoice.invoiceItems.push({
                        "itemType" : "OPD_LAB_CHARGES",
                        "itemCode" : labItem.panelCode,
                        "itemName" : labItem.panelName,
                        "quantity" : 1,
                        "amount"   : labItem.amount
                    });
                });
                angular.forEach($scope.totalBillDetails.Doc.items,function(DocItem,key){
                    invoice.invoiceItems.push({
                        "itemType" : "OPD_CONSULTATION",
                        "itemCode" : DocItem.doctorId,
                        "itemName" : DocItem.firstName+' '+DocItem.lastName,
                        "quantity" : 1,
                        "amount"   : DocItem.amount
                    });
                });
                angular.forEach($scope.totalBillDetails.Casuality.items,function(CasualtyItem,key){
                    invoice.invoiceItems.push({
                        "itemType" : "CASUALTY",
                        "itemCode" : CasualtyItem.Type,
                        "itemName" : CasualtyItem.Type,
                        "quantity" : 1,
                        "amount"   : CasualtyItem.amount
                    });
                });
                angular.forEach($scope.totalBillDetails.Radiology.items,function(RadiologyItem,key){
                    invoice.invoiceItems.push({
                        "itemType" : "RADIOLOGY",
                        "itemCode" : RadiologyItem.Type,
                        "itemName" : RadiologyItem.Type,
                        "quantity" : 1,
                        "amount"   : RadiologyItem.amount
                    });
                });
                $scope.sendInvoice(invoice);
            }
            $scope.sendInvoice = function(invoice){
                billing.sendInvoice(invoice,function (response, status, headers, config) {
                        var url =  '/ospedale/billing/billingTxnItemPrint.zul?invoiceId='+response.invoiceId
                        $window.open(url,'_blank');
                        $scope.$hide();// hide the aside window before reloading
                        $route.reload();
                    },
                    function (response, status, headers, config) {
                        $scope.hideReceivePayment = false;
                    }
                )
            }
        }
    ]
    ).directive('whenScrolled', function() {
        return function(scope, elm, attr) {
            var raw = elm[0];

            elm.bind('scroll', function() {
                if (raw.scrollTop + raw.offsetHeight >= raw.scrollHeight) {
                    scope.$apply(attr.whenScrolled);
                }
            });
        };
    })
    .config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
        cfpLoadingBarProvider.latencyThreshold = 10;
    }]);

