package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.quane.little.data.model.Id
import com.quane.little.data.service.CodeService
import com.quane.little.ide.view._
import com.quane.little.language._
import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.data.{Value, ValueType}
import com.quane.little.language.math.BasicMath

/** Presenter for views representing a [[com.quane.little.language.Setter]].
  *
  * @author Sean Connolly
  */
class SetterPresenter[V <: SetterView](view: V)(implicit val bindingModule: BindingModule)
  extends SetterViewPresenter
  with Injectable {

  private val presenterFactory = inject[PresenterFactory]
  private val codeService = inject[CodeService]

  private var _name = ""
  private var _value: Option[CodeViewPresenter] = None

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

  private[presenter] def value: CodeViewPresenter =
    _value match {
      case Some(e) => e
      case _ => throw new IllegalAccessException("No value expression set.")
    }

  /** Set the value expression.
    *
    * @param e the value expression
    */
  private[presenter] def value_=(e: Code): Unit = {
    val presenter =
      e match {
        case g: Getter =>
          presenterFactory.createGetPresenter(view.createGetterView()).initialize(g)
        case m: BasicMath =>
          presenterFactory.createMathPresenter(view.createMathView()).initialize(m)
        case l: Logic =>
          presenterFactory.createLogicPresenter(view.createLogicView()).initialize(l)
        case v: Value =>
          presenterFactory.createValuePresenter(view.createValueView()).initialize(v)
        case f: FunctionReference =>
          presenterFactory.createFunctionReference(view.createFunctionReferenceView()).initialize(f)
        case _ => throw new IllegalArgumentException("Not supported: " + e)
      }
    // TODO skip if the presenter type hasn't changed (?)
    _value = Some(presenter)
  }

  override def onNameChange(name: String): Unit = _name = name

  // TODO this changes depending on the Variable
  override def acceptedValueType: ValueType = ValueType.Something

  override def requestAddCode(id: Id, index: Int) =
    value = codeService.find(id) match {
      case Some(c) => c
      case None => throw new IllegalArgumentException("No code with id=" + id.id)
    }

  override def compile(): Setter = new Setter(_name, value.compile())

}