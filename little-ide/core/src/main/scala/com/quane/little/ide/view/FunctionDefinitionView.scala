package com.quane.little.ide.view

import com.quane.little.data.model.FunctionRecord
import com.quane.little.ide.presenter.{BlockPresenter, FunctionReferencePresenter, FunctionParameterPresenter}
import com.quane.little.language.FunctionDefinition


trait FunctionDefinitionView extends View[FunctionDefinitionViewPresenter] {

  def setName(name: String): Unit

  def createFunctionParameter(): FunctionParameterPresenter[_]

  def createBlock(): BlockPresenter[_]

  def createFunctionReference(): FunctionReferencePresenter[_]

  def compile: List[FunctionDefinition] =
    _viewPresenter.map {
      _.compile
    }.toList

  def save: List[FunctionRecord] =
    _viewPresenter.map {
      _.save
    }.toList
}

trait FunctionDefinitionViewPresenter extends ViewPresenter {

  def onNameChange(name: String): Unit

  def onParamAdded(param: FunctionParameterPresenter[_]): Unit

  def compile: FunctionDefinition

  def save: FunctionRecord

}