package com.quane.little.ide.presenter

import com.quane.little.language.{GetStatement, SetStatement, PrintStatement, Scope, Statement}
import com.quane.little.language.data.Value
import com.quane.little.ide.view._
import com.quane.little.language.memory.Pointer


sealed trait StatementPresenter extends ExpressionPresenter {
  override def compile(scope: Scope): Statement
}

class SetPresenter[V <: SetStatementView](view: V)
  extends StatementPresenter
  with SetStatementViewListener {

  private var _name = ""

  view.addViewListener(this)

  override def nameChanged(name: String): Unit = _name = name

  override def compile(scope: Scope): SetStatement = {
    new SetStatement(new Pointer(scope, _name), new Value("TODO"))
  }

}

class GetPresenter[V <: GetStatementView](view: V)
  extends StatementPresenter
  with GetStatementViewListener {

  private var _name = ""

  view.addViewListener(this)

  override def nameChanged(name: String): Unit = _name = name

  override def compile(scope: Scope): GetStatement = {
    new GetStatement(new Pointer(scope, _name))
  }

}

class PrintPresenter[V <: PrintStatementView](view: V)
  extends StatementPresenter
  with PrintStatementViewListener {

  private var _value = ""

  view.addViewListener(this)

  override def valueChanged(value: String): Unit = _value = value

  override def compile(scope: Scope): PrintStatement = {
    new PrintStatement(new Value(_value))
  }

}