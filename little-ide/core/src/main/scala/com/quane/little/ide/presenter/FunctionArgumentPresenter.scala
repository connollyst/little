package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.quane.little.data.model.Id
import com.quane.little.data.service.CodeService
import com.quane.little.ide.view._
import com.quane.little.language._
import com.quane.little.language.data.Value
import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.math.BasicMath

/** A presenter for views representing a function reference argument.
  *
  * @author Sean Connolly
  */
class FunctionArgumentPresenter[V <: FunctionArgumentView](view: V)(implicit val bindingModule: BindingModule)
  extends FunctionArgumentViewPresenter
  with Injectable {

  private val presenterFactory = inject[PresenterFactory]
  private val codeService = inject[CodeService]

  private var _name: String = ""
  private var _value: Option[CodeViewPresenter] = None
  private var _valueType: Option[ValueType] = None

  view.registerViewPresenter(this)

  private[presenter] def initialize(param: FunctionParameter, value: Code): FunctionArgumentPresenter[V] = {
    this.name = param.name
    this.value = value
    this.valueType = param.valueType
    this
  }

  private[presenter] def name: String = _name

  private[presenter] def name_=(n: String) = {
    _name = n
    view.setName(_name)
  }

  /** Get the argument value expression.
    *
    * @return the value expression
    */
  private[presenter] def value: CodeViewPresenter =
    _value match {
      case Some(e) => e
      case None => throw new IllegalAccessException("No argument value expression specified.")
    }

  /** Set the argument value expression.
    *
    * @param e the value expression
    */
  private[presenter] def value_=(e: Code): Unit = {
    val presenter =
      e match {
        // TODO skip if nothing has changed
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
    _value = Some(presenter)
  }

  private[presenter] def valueType: ValueType = {
    _valueType match {
      case Some(t) => t
      case None => throw new IllegalAccessException("No argument value type specified.")
    }
  }

  private[presenter] def valueType_=(t: ValueType): Unit = {
    _valueType = Some(t)
    // TODO can we bypass all the Manifest business by just passing in the valueType?
    presenterFactory.createCodeMenu[FunctionArgumentViewPresenter](view.createCodeMenuView(), this)
  }

  override def acceptedValueType: ValueType = valueType

  override def requestAddCode(id: Id, index: Int) =
    codeService.find(id) match {
      case Some(c) => value = c
      case None => throw new IllegalAccessException("No code found for id " + id.id)
    }

  override def compile(): Code = value.compile()

}