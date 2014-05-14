package com.quane.little.ide.view

import com.quane.little.data.model.FunctionRecord

trait FunctionDefinitionView
  extends View[FunctionDefinitionViewPresenter] {

  def setName(name: String): Unit

  def createFunctionParameter(): FunctionParameterView

  def createBlock(): BlockView

  def compile(): FunctionDefinition = presenter.compile()

  def save(): FunctionRecord = presenter.save()

}

trait FunctionDefinitionViewPresenter
  extends ViewPresenter {

  def onNameChange(name: String): Unit

  def requestAddParameter(): Unit

  def compile(): FunctionDefinition

  def save(): FunctionRecord

}