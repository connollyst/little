package com.quane.little.ide.presenter

import com.quane.little.ide.view.{SetStatementViewPresenter, SetStatementView}
import com.quane.little.language._
import com.quane.little.language.data.Value
import com.quane.little.data.model.RecordId
import com.quane.little.data.service.{PrimitiveService, FunctionService}

/** Presenter for views representing a [[com.quane.little.language.SetStatement]].
  *
  * @author Sean Connolly
  */
class SetStatementPresenter[V <: SetStatementView](view: V)
  extends StatementPresenter
  with SetStatementViewPresenter {

  private var _name = ""
  private var _value: Option[ExpressionPresenter] = None

  view.registerViewPresenter(this)
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
    val presenter =
      e match {
        case v: Value =>
          view.createValueStatement().initialize(v)
        case g: GetStatement =>
          view.createGetStatement().initialize(g)
        case f: FunctionReference =>
          view.createFunctionReference().initialize(f)
        case _ => throw new IllegalArgumentException("Expression not supported: " + e)
      }
    // TODO skip if the presenter type hasn't changed (?)
    _value = Some(presenter)
  }

  override def onNameChange(name: String): Unit = _name = name

  override def requestAddPrimitive(id: RecordId, index: Int) = value = PrimitiveService().findPrimitive(id)

  // TODO skip if already this function reference
  override def requestAddFunctionReference(id: RecordId, index: Int) = value = FunctionService().findReference(id)

  override def compile: SetStatement = new SetStatement(_name, value.compile)

}