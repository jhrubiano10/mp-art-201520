(function(ng){
    var mod = ng.module('resumeModule');
    
    mod.service('resumeService', ['CrudCreator','resumeContext', function(CrudCreator, context){
            CrudCreator.extendService(this, context);
            this.save = function(resume){
                 var self = this;
                self.saveRecord(resume);
            };
    }]);
})(window.angular);
