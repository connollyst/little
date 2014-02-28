package com.quane.little.ide.presenter

import com.quane.little.ide.view.{SetStatementViewListener, SetStatementView}
import com.quane.little.language._
import com.quane.little.language.memory.Pointer
import com.quane.little.language.data.Value
import scala.Some

/** Presenter for views representing a [[com.quane.little.language.SetStatement]].
  *
  * @author Sean Connolly
  */
class SetStatementPresenter[V <: SetStatementView](view: V)
  extends StatementPresenter
  with SetStatementViewListener {

  private var _name = "???"
  private var _value: Option[ExpressionPresenter] = None

  view.addViewListener(this)
  view.setName(_name)

  /** Initialize the set statement presenter.
    *
    * @param s the set statement
    * @return the initialized presenter
    */
  private[presenter] def initialize(s: SetStatement): SetStatementPresenter[V] = {
    name = s.name
    value = s.value
    this
  }

  private[presenter] def name: String = _name

  private[presenter] def name_=(name: String): Unit = {
    _name = name
    view.setName(name)
  }

  private[presenter] def value: ExpressionPresenter = {
    _value match {
      case e: Some[ExpressionPresenter] => e.get
      case _ => throw new IllegalAccessException("No value expression set.")
    }
  }

  /** Set the value expression.
    *
    * @param e the value expression
    */
  private[presenter] def value_=(e: Expression): Unit = {
    println("Setting value expression: " + e)
    val presenter =
      e match {
        case v: Value =>
          val value = view.createValueStatement()
          value.value = v.asText
          value
        case g: GetStatement =>
          val get = view.createGetStatement()
          get.name = g.name
          get
        case f: FunctionReference =>
          val fun = view.createFunctionReference()
          fun.name = f.name
          // TODO set function name & args
          fun
        case _ => throw new IllegalAccessException("Cannot add " + e)
      }
    value_=(presenter)
  }

  /** Set the value presenter.
    *
    * @param value the value presenter
    */
  private[presenter] def value_=(value: ExpressionPresenter): Unit = {
    _value = Some(value)
    // TODO notify view?
  }

  override def onNameChange(name: String): Unit = _name = name

  override def onValueChange(p: ExpressionPresenter) = _value = Some(p)


  override def compile(scope: Scope): SetStatement =
  // TODO what if _value isn't set?
    new SetStatement(new Pointer(scope, _name), _value.get.compile(scope))

}