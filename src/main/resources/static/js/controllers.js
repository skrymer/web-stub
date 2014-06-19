function StubManagementController($scope, $http) {
    $http.get('/api/')
        .success(function(data) {
            $scope.stubs = data

            if($scope.stubs[0] != null){
                $scope.activeStub =  $scope.stubs[0]

                if($scope.activeStub.scripts != null){
                    $scope.activeScript = $scope.activeStub.scripts[0]

                    setEditorContent($scope.activeScript.content)
                }
            }
        })
        .error(function(data, status, headers, config) {

        });

    $scope.editScript = function(script){
        $scope.activeScript = script

        setEditorContent(script.content)
    }

    $scope.setActiveStub = function(index){
        $scope.activeStub = $scope.stubs[index]

        if($scope.activeStub.scripts){
            $scope.activeScript = $scope.activeStub.scripts[0]

            setEditorContent($scope.activeScript.content)
        }
    }

    $scope.setActiveScript = function(index){

    }

    $scope.saveScript = function(){
        $scope.activeScript.content = getEditorContent()

        $http.put('/api/', $scope.activeStub)
             .success(function(data) {
                $.pnotify( {
                    pnotify_title: "Some title",
                    pnotify_type: "error"
                })
             })
             .error(function() {

             })
    }

    $scope.deleteActiveStub = function() {
        $http.delete('/api/', $scope.activeStub)
            .success(function() {
                $scope.stubs = $.map($scope.stubs, function(stub){
                        return (stub.name != $scope.activeStub) ? stub : null;
                    })

                $scope.activeStub = $scope.stubs[0]
            })
            .error(function() {

            })
    }
}

function setEditorContent(content){
    ace.edit("editor").setValue(content)
}

function getEditorContent(){
    return ace.edit("editor").getValue()
}

function StubCreationController($scope, $http) {
    $scope.scripts = [{id:1}, {id:2}]

    $scope.createNewStub = function(stub) {
        stub.scripts = getNewScripts($scope.scripts)

        $http.post('/api/', stub)
             .success( function(data) {

             })
             .error( function() {

            });
    }

    $scope.addNewScript = function(script) {
        var newScriptNo = $scope.scripts.length+1;
        $scope.scripts.push({'id':newScriptNo, 'name': script.name, 'path' : script.path});
    };

    $scope.showAddScript = function(script) {
        return script.id === $scope.scripts[$scope.scripts.length-1].id;
    };

    $scope.showScriptLabel = function (script) {
        return script.id === $scope.scripts[0].id;
    }
}

/**
 *
 * @param scope
 * @returns the scripts that has been created with a name and a path
 */
function getNewScripts(scripts){
    return $.map(scripts, function(script) {
            return (script.name != null && script.path != null) ? script : null;
        });
}


