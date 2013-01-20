package com.quane.glass.ide.language

import scala.collection.mutable.ListBuffer
import com.quane.glass.core.language.Expression
import scala.swing.BoxPanel
import scala.swing.Orientation
import org.eintr.loglady.Logging

class ListenerPanel
        extends BoxPanel(Orientation.Vertical) // TODO is there something more lightweight
        with ExpressionPanel
        with Logging {

    log.info("Creating a ListenerPanel")

    // An event listener panel does nothing but hold a function panel

    // TODO can the controller just hold a reference to a function panel?

}