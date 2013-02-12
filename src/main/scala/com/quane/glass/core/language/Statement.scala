package com.quane.glass.core.language

import org.eintr.loglady.Logging
import com.quane.glass.core.language.data.Value
import com.quane.glass.core.language.data.Number
import com.quane.glass.core.language.data.Direction
import com.quane.glass.core.language.data.Variable
import com.quane.glass.core.Guy

abstract class Statement[T]
    extends Expression[T]

// TODO can names be dynamic?.. not at the moment (KISS)
class AssignmentStatement(scope: Scope, name: String, value: Expression[Value])
        extends Statement[Unit]
        with Logging {

    def evaluate: Unit = {
        log.info("Setting the value of '" + name + "' to '" + value + "'");
        scope.save(new Variable(name, value.evaluate))
        // TODO could return value!
    }

}

class SetSpeedStatement(scope: Scope, value: Expression[Number])
    extends AssignmentStatement(scope, Guy.VAR_SPEED, value);

class SetDirectionStatement(scope: Scope, value: Expression[Direction])
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