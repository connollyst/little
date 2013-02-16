package com.quane.glass.language

import org.eintr.loglady.Logging

import com.quane.glass.core.Guy
import com.quane.glass.language.exceptions.GlassCastException
import com.quane.glass.language.data.Direction
import com.quane.glass.language.data.Number
import com.quane.glass.language.data.Value
import com.quane.glass.language.data.Variable
import com.quane.glass.language.memory.Pointer

abstract class Statement[T]
    extends Expression[T]

class SetterStatement[+V <: Value](pointer: Pointer[V], value: Expression[V]) // TODO we need a compiler check that its the right value type..?
        extends Statement[Unit]
        with Logging {

    def evaluate {
        val name = pointer.variableName
        val valueClass = pointer.valueClass
        val actualValue = value.evaluate
        log.info("Setting '" + name + "' to " + valueClass.getSimpleName + "(" + actualValue.primitive + ")");
        pointer.scope.save(new Variable(name, actualValue))
        // TODO could return value!
    }

}

class SetSpeedStatement(scope: Scope, value: Expression[Number])
    extends SetterStatement(new Pointer[Number](scope, Guy.VAR_SPEED, classOf[Number]), value);

class SetDirectionStatement(scope: Scope, value: Expression[Direction])
    extends SetterStatement[Direction](new Pointer[Direction](scope, Guy.VAR_DIRECTION, classOf[Direction]), value);

class GetterStatement[V <: Value](pointer: Pointer[V])
        extends Statement[V]
        with Logging {
    
    def evaluate: V = {
        val name = pointer.variableName
        log.info("Getting the value of '" + name + "'...");
        val scope = pointer.scope
        val clazz = pointer.valueClass
        val value = scope.fetch(name).value; // TODO null check please
        if (value.getClass == clazz) {
            return value.asInstanceOf[V]
        } else {
            throw new GlassCastException(
                "'" + name + "' is a " + value
                    + ", expected " + pointer.valueClass
            );
        }
    }
}

class PrintStatement(text: String)
        extends Statement[Unit]
        with Logging {

    def evaluate {
        // TODO this should display a speech bubble over the guy
        log.error(text);
    }

}

class ReturnStatement(name: String, scope: Scope)
        extends Statement[Variable] {

    def evaluate: Variable = scope.fetch(name);

}