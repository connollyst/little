package com.quane.little.ide.view

import org.mockito.stubbing.Answer
import org.mockito.invocation.InvocationOnMock

object MockView {

  /** Returns a Mockito [[org.mockito.stubbing.Answer]] which generates a new
    * instance of the expected class using the given `provider` function.
    *
    * @param provider the mock object provider
    * @tparam T the type of mock object
    * @return an answer which generates mock objects
    */
  def answer[T](provider: () => T): Answer[T] = {
    new Answer[T] {
      override def answer(p1: InvocationOnMock): T = provider()
    }
  }

}

/** A mock view trait for implementing base view functionality in tests.
  *
  * @author Sean Connolly
  */
trait MockView {

  // mock views are never added to anything so don't need to be removed
  def remove(): Unit = Unit

}
