package com.quane.little.ide.language

import scala.collection.mutable.ListBuffer
import com.quane.little.language.Expression
import scala.swing.BoxPanel
import scala.swing.Orientation
import org.eintr.loglady.Logging

/** A listener panel is simply a specific type of {@link FunctionPanel}.
  * That is, the UI for an event listener and a function behaves exactly the
  * same. The {@link ListenerController}, however, can {@code compile}
  * down to an {@link EventListener} by known what kind of {@link LittleEvent}
  * the {@link Function} is related to.
  *
  * @author Sean Connolly
  */
class ListenerPanel
    extends FunctionPanel
    with Logging