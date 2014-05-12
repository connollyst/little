package com.quane.little.ide.view

import com.quane.little.data.model.FunctionRecord
import com.quane.little.ide.presenter.{BlockPresenter, FunctionParameterPresenter}
import com.quane.little.language.FunctionDefinition

trait FunctionDefinitionView
  extends View[FunctionDefinitionViewPresenter] {

  def setName(name: String): Unit

  def createFunctionParameter(): FunctionParameterPresenter[_]

  def createBlock(): BlockPresenter[_]

  def compile: FunctionDefinition = presenter.compile

  def save(): FunctionRecord = presenter.save()

}

trait FunctionDefinitionViewPresenter
  extends ViewPresenter {

  def onNameChange(name: String): Unit

  def onParamAdded(param: FunctionParameterPresenter[_]): Unit

  def compile: FunctionDefinition

  def save(): FunctionRecord

}