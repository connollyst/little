package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.Injectable
import com.quane.little.data.model.CodeCategory
import com.quane.little.data.service.{FunctionService, UserService}
import com.quane.little.ide.MockIDEBindingModule
import com.quane.little.ide.view._
import com.quane.little.language._
import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.data.{Value, ValueType}
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, WordSpec}

/** Tests for the [[com.quane.little.ide.presenter.PrinterPresenter]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestPrinterPresenter
  extends WordSpec with BeforeAndAfter with ShouldMatchers with MockitoSugar with Injectable {

  implicit val bindingModule = MockIDEBindingModule

  before({
    val mockUserService = inject[UserService]
    val mockFunctionService = inject[FunctionService]
    mockUserService.upsert("connollyst")
    mockFunctionService.insert("connollyst", CodeCategory.Basic, new FunctionDefinition("TestFunction"))
  })

  "PrinterPresenter" should {

    "set child presenter with correct text value" in {
      val presenter = new PrinterPresenter(new MockPrinterView)
      presenter.text = Value("abc")
      val textPresenter = presenter.text.asInstanceOf[ValuePresenter]
      assert(textPresenter.value == "abc")
    }
    "register itself with view immediately" in {
      val view = MockPrinterView.mocked()
      val presenter = new PrinterPresenter(view)
      verify(view).registerViewPresenter(presenter)
    }
    "create a view for a new Value expression" in {
      val view = MockPrinterView.mocked()
      val presenter = new PrinterPresenter(view)
      when(view.createValueView()).thenReturn(mock[ValueView])
      presenter.text = Value("abc")
      verify(view).createValueView()
    }
    "create a view for a new Getter expression" in {
      val view = MockPrinterView.mocked()
      val presenter = new PrinterPresenter(view)
      when(view.createGetView()).thenReturn(mock[GetterView])
      presenter.text = new Getter("TestValue")
      verify(view).createGetView()
    }
    "create a view for a new FunctionReference expression" in {
      val view = MockPrinterView.mocked()
      val presenter = new PrinterPresenter(view)
      when(view.createFunctionReference()).thenReturn(mock[FunctionReferenceView])
      presenter.text = new FunctionReference("TestFunction")
      verify(view).createFunctionReference()
    }
    "initialize the view for a new Value expression" in {
      val view = MockPrinterView.mocked()
      val presenter = new PrinterPresenter(view)
      val valueView = mock[ValueView]
      when(view.createValueView()).thenReturn(valueView)
      presenter.text = Value("abc")
      verify(valueView).setValue("abc")
    }
    "initialize the view for a new Getter expression" in {
      val view = MockPrinterView.mocked()
      val presenter = new PrinterPresenter(view)
      val getterView = mock[GetterView]
      when(view.createGetView()).thenReturn(getterView)
      presenter.text = new Getter("TestValue")
      verify(getterView).setName("TestValue")
    }
    "initialize the view for a new FunctionReference expression" in {
      val view = MockPrinterView.mocked()
      val presenter = new PrinterPresenter(view)
      val functionView = mock[FunctionReferenceView]
      when(view.createFunctionReference()).thenReturn(functionView)
      presenter.text = new FunctionReference("TestFunction")
      verify(functionView).setName("TestFunction")
    }
    "create a view with a presenter for a new Value expression" in {
      val view = MockPrinterView.mocked()
      val presenter = new PrinterPresenter(view)
      val expectedValueView = mock[ValueView]
      when(view.createValueView()).thenReturn(expectedValueView)
      presenter.text = Value("abc")
      presenter.text.getClass should be(classOf[ValuePresenter])
      val valuePresenter = presenter.text.asInstanceOf[ValuePresenter]
      valuePresenter.view should be(expectedValueView)
    }
    "create a view with a presenter for a new Getter expression" in {
      val view = MockPrinterView.mocked()
      val presenter = new PrinterPresenter(view)
      val getterView = mock[GetterView]
      when(view.createGetView()).thenReturn(getterView)
      presenter.text = new Getter("TestValue")
      presenter.text.getClass should be(classOf[GetterPresenter])
      val getterPresenter = presenter.text.asInstanceOf[GetterPresenter]
      getterPresenter.view should be(getterView)
    }
    "create a view with a presenter for a new FunctionReference expression" in {
      val view = MockPrinterView.mocked()
      val presenter = new PrinterPresenter(view)
      val functionView = mock[FunctionReferenceView]
      when(view.createFunctionReference()).thenReturn(functionView)
      presenter.text = new FunctionReference("TestFunction")
      presenter.text.getClass should be(classOf[FunctionReferencePresenter])
      val functionPresenter = presenter.text.asInstanceOf[FunctionReferencePresenter]
      functionPresenter.view should be(functionView)
    }
    "error when adding unknown expression" in {
      val presenter = new PrinterPresenter(new MockPrinterView)
      val unknownCode = new Code {
        override def returnType: ValueType = ValueType.Something

        override def evaluate(scope: Scope): Value = Value("I shouldn't exist.")
      }
      val error = intercept[IllegalArgumentException] {
        presenter.text = unknownCode
      }
      error.getMessage should startWith("Print text expression not supported")
    }
    "compile print(value expression)" in {
      assertCompiledValue(Value("abc"))
    }
    "compile print(get expression)" in {
      assertCompiledValue(new Getter("TestValue"))
    }
    "compile print(function reference)" in {
      assertCompiledValue(new FunctionReference("TestFunction"))
    }
    "error if compiled without expression" in {
      val view = MockPrinterView.mocked()
      val presenter = new PrinterPresenter(view)
      intercept[IllegalAccessException] {
        presenter.compile()
      }
    }

  }

  private def assertCompiledValue(value: Code) = {
    val view = MockPrinterView.mocked()
    val presenter = new PrinterPresenter(view)
    when(view.createGetView()).thenReturn(new MockGetterView)
    when(view.createValueView()).thenReturn(new MockValueView)
    when(view.createFunctionReference()).thenReturn(new MockFunctionReferenceView)
    presenter.text = value
    val compiled = presenter.compile()
    assert(compiled.value == value)
  }
}
