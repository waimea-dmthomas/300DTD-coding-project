/**
 * ------------------------------------------------------------------------
 * PROJECT NAME HERE
 * Level 3 programming project
 *
 * by YOUR NAME HERE
 *
 * BRIEF PROJECT DESCRIPTION HERE
 * BRIEF PROJECT DESCRIPTION HERE
 * BRIEF PROJECT DESCRIPTION HERE
 * ------------------------------------------------------------------------
 */


import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import java.awt.event.*
import javax.swing.*


//=============================================================================================

class Location(val name: String, val desc: String) {
    var north: Location? = null
    var east: Location? = null
    var south: Location? = null
    var west: Location? = null

    fun addNorth(location: Location) {
        if (north == null) {
            north = location
            location.addSouth(this)
        }
    }

    fun addEast(location: Location) {
        if (east == null) {
            east = location
            location.addWest(this)
        }
    }

    fun addSouth(location: Location) {
        if (south == null) {
            south = location
            location.addNorth(this)
        }
    }

    fun addWest(location: Location) {
        if (west == null) {
            west = location
            location.addEast(this)
        }
    }
}

//=============================================================================================

/**
 * GUI class
 * Defines the UI and responds to events
 */
class MenuGUI : JFrame(), ActionListener {

    // Setup list of locations
    val locations = mutableListOf<Location>()
    var currentLocation: Location

    // Setup some properties to hold the UI elements
    private lateinit var titleLabel: JLabel

    private lateinit var startButton: JButton

    private lateinit var northButton: JButton
    private lateinit var eastButton: JButton
    private lateinit var southButton: JButton
    private lateinit var westButton: JButton

    private lateinit var inventoryButton: JButton
    private lateinit var inspectButton: JButton

    private lateinit var statusHeaderLabel: JLabel
    private lateinit var statusLabel: JLabel

    private lateinit var locationHeaderLabel: JLabel
    private lateinit var locationLabel: JLabel

    /**
     * Create, build and run the UI
     */
    init {
        setupWindow()
        setupMap()
        buildUI()

        currentLocation = locations.first()

        // Show the app, centred on screen
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun setupMap() {
        val opening = Location("Opening", "You awake in a rough hilly plain. In front of you is the opening of a cave and behind you is the entrance of a forest.")
        val woods = Location("Woods", "You are surrounded by trees and vines in a thick wooded area. To your east is a crooked wooden hut with a busted door.")
        val hut = Location("Hut", "You are in a dilapidated old hut. Inside there is a destroyed bed frame, a broken bookshelf and a raggedy spool of rope.")
        val mouth = Location("Mouth", "You are in the mouth of an ominous cave opening. There are two different tunnels to your west and your east.")
        val eastFork = Location("East Fork", "You are in a cramped, dank tunnel.")
        val westFork = Location("West Fork", "You are in a grand large opening.")
        val pit = Location("Pit", "In front of you is a cavernous pit dropping directly into a bottomless darkness.")
        val stoneDoor = Location("Stone Door", "In front of you is a solid stone door with strange engravings. In the middle is an empty gap.")

        locations.add(opening)
        opening.addNorth(mouth)
        opening.addSouth(woods)

        locations.add(woods)
        woods.addEast(hut)

        locations.add(hut)

        locations.add(mouth)
        mouth.addEast(eastFork)
        mouth.addWest(westFork)

        locations.add(eastFork)
        eastFork.addNorth(stoneDoor)

        locations.add(stoneDoor)

        locations.add(westFork)
        westFork.addNorth(pit)

        locations.add(pit)

    }

    /**
     * Configure the main window
     */
    private fun setupWindow() {
        title = "Cave Escape"
        contentPane.preferredSize = Dimension(500, 500)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }

    /**
     * Populate the UI
     */
    private fun buildUI() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 20)

        titleLabel = JLabel("Cave Escape", SwingConstants.CENTER)
        titleLabel.bounds = Rectangle(76, 62, 348, 36)
        titleLabel.font = baseFont
        add(titleLabel)

        startButton = JButton("Enter the Cave")
        startButton.bounds = Rectangle(130,374,240,40)
        startButton.font = baseFont
        startButton.addActionListener(this)
        add(startButton)

        northButton = JButton("^")
        northButton.bounds = Rectangle(225,351,50,50)
        northButton.font = baseFont
        northButton.addActionListener(this)
        northButton.isVisible = false
        add(northButton)

        eastButton = JButton(">")
        eastButton.bounds = Rectangle(284,380,50,50)
        eastButton.font = baseFont
        eastButton.addActionListener(this)
        eastButton.isVisible = false
        add(eastButton)

        southButton = JButton("v")
        southButton.bounds = Rectangle(225,411,50,50)
        southButton.font = baseFont
        southButton.addActionListener(this)
        southButton.isVisible = false
        add(southButton)

        westButton = JButton("<")
        westButton.bounds = Rectangle(166,380,50,50)
        westButton.font = baseFont
        westButton.addActionListener(this)
        westButton.isVisible = false
        add(westButton)

        statusHeaderLabel = JLabel("STATUS", SwingConstants.CENTER)
        statusHeaderLabel.bounds = Rectangle(119, 54, 262, 20)
        statusHeaderLabel.font = baseFont
        statusHeaderLabel.isVisible = false
        add(statusHeaderLabel)

        statusLabel = JLabel("", SwingConstants.CENTER)
        statusLabel.bounds = Rectangle(119, 74, 262, 176)
        statusLabel.font = baseFont
        statusLabel.isVisible = false
        add(statusLabel)

        locationHeaderLabel = JLabel("LOCATION", SwingConstants.CENTER)
        locationHeaderLabel.bounds = Rectangle(119, 9, 262, 20)
        locationHeaderLabel.font = baseFont
        locationHeaderLabel.isVisible = false
        add(locationHeaderLabel)

        locationLabel = JLabel("", SwingConstants.CENTER)
        locationLabel.bounds = Rectangle(119, 27, 262, 20)
        locationLabel.font = baseFont
        locationLabel.isVisible = false
        add(locationLabel)

        inventoryButton = JButton("Inventory")
        inventoryButton.bounds = Rectangle(16,411,131,50)
        inventoryButton.font = baseFont
        inventoryButton.addActionListener(this)
        inventoryButton.isVisible = false
        add(inventoryButton)

        inspectButton = JButton("Inspect")
        inspectButton.bounds = Rectangle(16,350,131,50)
        inspectButton.font = baseFont
        inspectButton.addActionListener(this)
        inspectButton.isVisible = false
        add(inspectButton)
    }

    /**
     * Handle any UI events
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            startButton -> startAction()

            northButton -> goNorth()
            eastButton -> goEast()
            southButton -> goSouth()
            westButton -> goWest()
        }
    }

    /**
     * Functions
     */

    private fun startAction() {
        startButton.isVisible = false
        titleLabel.isVisible = false

        northButton.isVisible = true
        eastButton.isVisible = true
        southButton.isVisible = true
        westButton.isVisible = true
        inventoryButton.isVisible = true
        inspectButton.isVisible = true

        statusHeaderLabel.isVisible = true
        statusLabel.isVisible = true
        locationHeaderLabel.isVisible = true
        locationLabel.isVisible = true

        showLocation()
    }

    private fun showLocation() {
        locationLabel.text = currentLocation.name
        statusLabel.setText("<html>" + currentLocation.desc + "</html>")
        checkNorth()
        checkEast()
        checkSouth()
        checkWest()
    }

    private fun checkNorth() {
        if(currentLocation.north != null) {
            northButton.isEnabled = true
        }
        else {
            northButton.isEnabled = false
        }
    }

    private fun checkEast() {
        if(currentLocation.east != null) {
            eastButton.isEnabled = true
        }
        else {
            eastButton.isEnabled = false
        }
    }

    private fun checkSouth() {
        if(currentLocation.south != null) {
            southButton.isEnabled = true
        }
        else {
            southButton.isEnabled = false
        }
    }

    private fun checkWest() {
        if(currentLocation.west != null) {
            westButton.isEnabled = true
        }
        else {
            westButton.isEnabled = false
        }
    }

    private fun goNorth() {
        currentLocation = currentLocation.north!!
        showLocation()
    }

    private fun goEast() {
        currentLocation = currentLocation.east!!
        showLocation()
    }

    private fun goSouth() {
        currentLocation = currentLocation.south!!
        showLocation()
    }

    private fun goWest() {
        currentLocation = currentLocation.west!!
        showLocation()
    }
}

//=============================================================================================

/**
 * Launch the application
 */
fun main() {
    // Flat, Dark look-and-feel
    FlatDarkLaf.setup()

    // Create the UI
    MenuGUI()
}


