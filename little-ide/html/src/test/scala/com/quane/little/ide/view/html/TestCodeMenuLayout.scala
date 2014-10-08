package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.PresenterAcceptsCode
import com.quane.little.ide.view.View
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

/** Test cases for the [[com.quane.little.ide.view.html.CodeMenuLayout]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestCodeMenuLayout extends WordSpec with ShouldMatchers with MockitoSugar {

  "CodeMenuLayout index" should {
    "be evaluated only when requested" in {
      val counter = mock[Counter]
      val menu = new CodeMenuLayout(mock[View[PresenterAcceptsCode]], counter.count)
      verify(counter, never()).count()
      menu.index()
      verify(counter, times(1)).count()
    }
    "be evaluated every time it is requested" in {
      val counter = mock[Counter]
      val menu = new CodeMenuLayout(mock[View[PresenterAcceptsCode]], counter.count)
      1 to 42 foreach { _ => menu.index()}
      verify(counter, times(42)).count()
    }
  }

  private trait Counter {
    def count(): Int
  }

}
