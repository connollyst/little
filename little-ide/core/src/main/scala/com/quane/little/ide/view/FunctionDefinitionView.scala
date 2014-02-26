package com.quane.little.ide.view

import com.quane.little.language.FunctionDefinition
import com.quane.little.ide.presenter.{BlockPresenter, FunctionReferencePresenter, FunctionParameterPresenter}


trait FunctionDefinitionView extends View[FunctionDefinitionViewListener] {

  def setName(name: String): Unit

  def createFunctionParameter(): FunctionParameterPresenter[_]

  def createBlock(): BlockPresenter[_]

  def createFunctionReference(): FunctionReferencePresenter[_]

  def compile(): List[FunctionDefinition] = {
    viewListeners.map {
      listener => listener.compile()
    }.toList
  }

}

trait FunctionDefinitionViewListener extends ViewListener {

  def onNameChange(name: String): Unit

  def compile(): FunctionDefinition

}