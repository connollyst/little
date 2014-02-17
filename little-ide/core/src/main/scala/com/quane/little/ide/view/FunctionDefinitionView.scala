package com.quane.little.ide.view

import com.quane.little.language.FunctionDefinition
import com.quane.little.ide.presenter.FunctionParameterPresenter


trait FunctionDefinitionView extends View[FunctionDefinitionViewListener] {

  def setName(name: String): Unit

  def createFunctionParameter(): FunctionParameterPresenter[_]

  def compile(): List[FunctionDefinition] = {
    viewListeners.map {
      listener => listener.compile()
    }.toList
  }

}

trait FunctionDefinitionViewListener extends ViewListener {

  def compile(): FunctionDefinition

}