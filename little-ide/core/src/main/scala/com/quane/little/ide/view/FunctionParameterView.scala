package com.quane.little.ide.view

import com.quane.little.language.data.ValueType.ValueType

trait FunctionParameterView extends View[FunctionParameterViewPresenter] {

  def setName(name: String): Unit

  def setValueType(valueType: ValueType): Unit

}

trait FunctionParameterViewPresenter extends ViewPresenter {

  def onNameChanged(name: String): Unit

  def onValueTypeChanged(valueType: ValueType): Unit

}
