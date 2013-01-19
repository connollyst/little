package com.quane.glass.ide

import scala.swing.GridPanel

/** The memory box is one of the main components of the IDE.<br/>
  * The memory box represents the memory available to a user in their programs
  * and how it is allocated. Visually, it is intended to be very similar to
  * inventory in many games; it is broken up into a number of equally sized
  * slots with contents occupying one or more slots. The number of slots
  * available in memory can be increased by advancement through the game.
  *
  * @author Sean Connolly
  */
class GlassMemoryBox
        extends GridPanel(2, 4) {

    // TODO GridPanel requires these parameters up-front..? 

    private val MAX_SLOTS_WIDE = 4;

    val slots = 8

}