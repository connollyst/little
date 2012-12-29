package com.quane.glass.core.language

import org.eintr.loglady.Logging
import com.quane.glass.core.language.data.Value
import com.quane.glass.core.language.data.Variable

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

class PrintStatement(text: String)
        extends Statement[Unit] {

    def evaluate: Unit = println(text);

}

class ReturnStatement(name: String, scope: Scope)
        extends Statement[Variable] {

    def evaluate: Variable = scope.fetch(name);

}