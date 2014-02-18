package com.quane.little.ide.view


trait GetStatementView extends View[GetStatementViewListener]

trait SetStatementView extends View[SetStatementViewListener]

trait PrintStatementView extends View[PrintStatementViewListener]

trait GetStatementViewListener extends ViewListener {

  def nameChanged(name: String): Unit

}

trait SetStatementViewListener extends ViewListener {

  def nameChanged(name: String): Unit

}

trait PrintStatementViewListener extends ViewListener {

  def valueChanged(name: String): Unit

}