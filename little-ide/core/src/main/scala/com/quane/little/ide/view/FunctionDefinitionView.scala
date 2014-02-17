package com.quane.little.ide.view

import com.quane.little.language.FunctionDefinition


trait FunctionDefinitionView extends View[FunctionDefinitionViewListener] {

  def compile(): List[FunctionDefinition] = {
    viewListeners.map {
      listener => listener.compile()
    }.toList
  }

}

trait FunctionDefinitionViewListener extends ViewListener {

  def compile(): FunctionDefinition

}