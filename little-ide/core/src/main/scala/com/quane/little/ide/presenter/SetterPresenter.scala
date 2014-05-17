package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ExpressionViewPresenter, SetterViewPresenter, SetterView}
import com.quane.little.data.model.RecordId
import com.quane.little.data.service.{ExpressionService, FunctionService}
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.quane.little.language.{FunctionReference, Setter, Expression, Getter}
import com.quane.little.language.data.Value

/** Presenter for views representing a [[com.quane.little.language.Setter]].
  *
  * @author Sean Connolly
  */
class SetterPresenter[V <: SetterView](view: V)(implicit val bindingModule: BindingModule)
  extends SetterViewPresenter
  with Injectable {

  private val presenterFactory = inject[PresenterFactory]
  private val expressionService = inject[ExpressionService]
  private val functionService = inject[FunctionService]

  private var _name = ""
  private var _value: Option[ExpressionViewPresenter] = None

  view.registerViewPresenter(this)
  view.setName(_name)

  /** Initialize the set statement presenter.
    *
    * @param s the set statement
    * @return the initialized presenter
    */
  private[presenter] def initialize(s: Setter): SetterPresenter[V] = {
    name = s.name
    value = s.value
    this
  }

  private[presenter] def name: String = _name

  private[presenter] def name_=(name: String): Unit = {
    _name = name
    view.setName(name)
  }

  private[presenter] def value: ExpressionViewPresenter = {
    _value match {
      case e: Some[ExpressionViewPresenter] => e.get
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
        case g: Getter =>
          presenterFactory.createGetPresenter(view.createGetStatement()).initialize(g)
        case v: Value =>
          presenterFactory.createValuePresenter(view.createValueStatement()).initialize(v)
        case f: FunctionReference =>
          presenterFactory.createFunctionReference(view.createFunctionReference()).initialize(f)
        case _ => throw new IllegalArgumentException("Expression not supported: " + e)
      }
    // TODO skip if the presenter type hasn't changed (?)
    _value = Some(presenter)
  }

  override def onNameChange(name: String): Unit = _name = name

  override def requestAddExpression(id: RecordId, index: Int) =
    value = expressionService.findExpression(id)

  // TODO skip if already this function reference
  override def requestAddFunctionReference(id: RecordId, index: Int) =
    value = functionService.findReference(id)

  override def compile(): Setter = new Setter(_name, value.compile())

}