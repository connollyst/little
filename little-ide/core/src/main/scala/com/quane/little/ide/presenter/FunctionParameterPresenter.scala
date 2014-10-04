package com.quane.little.ide.presenter

import com.quane.little.ide.view.{FunctionParameterViewPresenter, FunctionParameterView}
import com.quane.little.language.FunctionParameter
import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.data.ValueType

/** A presenter for views representing a function definition parameter.
  *
  * @author Sean Connolly
  */
class FunctionParameterPresenter[V <: FunctionParameterView](view: V)
  extends FunctionParameterViewPresenter {

  private var _name = ""
  private var _valueType = ValueType.String

  view.registerViewPresenter(this)

  override def onNameChanged(n: String) = _name = n

  override def onValueTypeChanged(t: ValueType) = _valueType = t

  private[presenter] def initialize(param: FunctionParameter): FunctionParameterPresenter[V] = {
    name = param.name
    this
  }

  private[presenter] def name: String = _name

  private[presenter] def name_=(n: String): Unit = {
    _name = n
    view.setName(_name)
  }

  private[presenter] def valueType: ValueType = _valueType

  private[presenter] def valueType_=(t: ValueType): Unit = {
    _valueType = t
    view.setValueType(_valueType)
  }

  def compile(): FunctionParameter = new FunctionParameter(name, valueType)

}