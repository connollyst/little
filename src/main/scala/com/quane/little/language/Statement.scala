package com.quane.little.language

import org.eintr.loglady.Logging
import com.quane.little.language.exceptions.GlassCastException
import com.quane.little.language.data.Direction
import com.quane.little.language.data.Number
import com.quane.little.language.data.Value
import com.quane.little.language.data.Variable
import com.quane.little.language.memory.Pointer
import com.quane.little.game.entity.Mob

abstract class Statement[T]
    extends Expression[T]

class SetStatement[+V <: Value](pointer: Pointer[V], value: Expression[V]) // TODO we need a compiler check that its the right value type..?
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
    extends SetStatement(
        new Pointer[Number](scope, Mob.VAR_SPEED, classOf[Number]),
        value
    );

class SetDirectionStatement(scope: Scope, value: Expression[Direction])
    extends SetStatement[Direction](
        new Pointer[Direction](scope, Mob.VAR_DIRECTION, classOf[Direction]),
        value
    );

class GetStatement[V <: Value](pointer: Pointer[V])
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