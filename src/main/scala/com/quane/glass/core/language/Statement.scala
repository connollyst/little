package com.quane.glass.core.language

import org.eintr.loglady.Logging
import com.quane.glass.core.language.data.Value
import com.quane.glass.core.language.data.Variable
import com.quane.glass.core.Guy

abstract class Statement[T]
    extends Expression[T]

class AssignmentStatement(scope: Scope, name: String, value: Value[Any])
        extends Statement[Unit]
        with Logging {

    def evaluate: Unit = {
        log.info("Setting the value of '" + name + "' to '" + value + "'");
        scope.save(new Variable(name, value))
    }

}

class SetSpeedStatement(scope: Scope, value: Value[Any])
    extends AssignmentStatement(scope, Guy.VAR_SPEED, value);

class SetDirectionStatement(scope: Scope, value: Value[Any])
    extends AssignmentStatement(scope, Guy.VAR_DIRECTION, value);

class PrintStatement(text: String)
        extends Statement[Unit]
        with Logging {

    def evaluate: Unit = {
        // TODO this should display a speech bubble over the guy
        log.error(text);
    }

}

class ReturnStatement(name: String, scope: Scope)
        extends Statement[Variable] {

    def evaluate: Variable = scope.fetch(name);

}