package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.google.common.base.Objects
import com.quane.little.data.model.Id
import com.quane.little.data.service.{CodeService, FunctionService}
import com.quane.little.ide.view.{CodeViewPresenter, PrinterView, PrinterViewPresenter}
import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.data.{Value, ValueType}
import com.quane.little.language.{Code, FunctionReference, Getter, Printer}

/** Presenter for views representing a [[com.quane.little.language.Printer]].
  *
  * @author Sean Connolly
  */
class PrinterPresenter[V <: PrinterView](view: V)(implicit val bindingModule: BindingModule)
  extends PrinterViewPresenter
  with Injectable {

  private val presenterFactory = inject[PresenterFactory]
  private val codeService = inject[CodeService]

  private var _text: Option[_ <: CodeViewPresenter] = None

  view.registerViewPresenter(this)
  createCodeMenu()


  /** Initialize the print presenter.
    *
    * @param p the print expression
    * @return the initialized presenter
    */
  private[presenter] def initialize(p: Printer): PrinterPresenter[V] = {
    text = p.value
    this
  }

  /** Get the print text expression.
    *
    * @return the text expression
    */
  private[presenter] def text: CodeViewPresenter =
    _text match {
      case Some(t) => t
      case None => throw new IllegalAccessException("No print text expression specified.")
    }

  /** Set the print text expression.
    *
    * @param e the text expression
    */
  private[presenter] def text_=(e: Code): Unit = {
    // TODO skip if not changed
    val presenter =
      e match {
        case g: Getter =>
          presenterFactory.createGetPresenter(view.createGetView()).initialize(g)
        case v: Value =>
          presenterFactory.createValuePresenter(view.createValueView()).initialize(v)
        case f: FunctionReference =>
          presenterFactory.createFunctionReference(view.createFunctionReference()).initialize(f)
        case _ => throw new IllegalArgumentException("Print text expression not supported: " + e)
      }
    _text = Some(presenter)
  }

  /** Create an add a new code menu to the printer.
    */
  private def createCodeMenu(): Unit =
    presenterFactory.createCodeMenu[PrinterViewPresenter](view.createCodeMenu(), this)

  override def acceptedValueType: ValueType = ValueType.Something

  override def requestAddCode(id: Id, index: Int) =
    text = codeService.find(id) match {
      case Some(c) => c
      case None => throw new IllegalArgumentException("No code with id=" + id.id)
    }

  /** Compile to a [[com.quane.little.language.Printer]].
    *
    * @return the compiled print statement
    */
  override def compile(): Printer = new Printer(text.compile())

  override def toString =
    Objects.toStringHelper(getClass)
      .add("text", _text)
      .toString

}