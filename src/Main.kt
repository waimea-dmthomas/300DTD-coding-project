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
import com.formdev.flatlaf.FlatLightLaf
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
            location.addEast(this)
        }
    }

    fun addWest(location: Location) {
        if (west == null) {
            west = location
            location.addNorth(this)
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

    private lateinit var prepareButton: JButton
    private lateinit var startButton: JButton
    private lateinit var exitPrepareButton: JButton

    private lateinit var northButton: JButton
    private lateinit var eastButton: JButton
    private lateinit var southButton: JButton
    private lateinit var westButton: JButton

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
        val surface = Location("Surface", "The opening of a large cave.")

        locations.add(surface)
    }

    /**
     * Configure the main window
     */
    private fun setupWindow() {
        title = "Caves, Creatures and Carrots"
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

        titleLabel = JLabel("Caves, Creatures and Carrots", SwingConstants.CENTER)
        titleLabel.bounds = Rectangle(76, 62, 348, 36)
        titleLabel.font = baseFont
        add(titleLabel)

        prepareButton = JButton("Prepare")
        prepareButton.bounds = Rectangle(130,309,240,40)
        prepareButton.font = baseFont
        prepareButton.addActionListener(this)
        add(prepareButton)

        startButton = JButton("Enter the Cave")
        startButton.bounds = Rectangle(130,374,240,40)
        startButton.font = baseFont
        startButton.addActionListener(this)
        add(startButton)

        exitPrepareButton = JButton("Exit")
        exitPrepareButton.bounds = Rectangle(130,374,240,40)
        exitPrepareButton.font = baseFont
        exitPrepareButton.addActionListener(this)
        exitPrepareButton.isVisible = false
        add(exitPrepareButton)

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
        statusHeaderLabel.bounds = Rectangle(119, 278, 262, 20)
        statusHeaderLabel.font = baseFont
        statusHeaderLabel.isVisible = false
        add(statusHeaderLabel)

        statusLabel = JLabel("", SwingConstants.CENTER)
        statusLabel.bounds = Rectangle(119, 298, 262, 20)
        statusLabel.font = baseFont
        statusLabel.isVisible = false
        add(statusLabel)

        locationHeaderLabel = JLabel("LOCATION", SwingConstants.CENTER)
        locationHeaderLabel.bounds = Rectangle(119, 236, 262, 20)
        locationHeaderLabel.font = baseFont
        locationHeaderLabel.isVisible = false
        add(locationHeaderLabel)

        locationLabel = JLabel("", SwingConstants.CENTER)
        locationLabel.bounds = Rectangle(119, 254, 262, 20)
        locationLabel.font = baseFont
        locationLabel.isVisible = false
        add(locationLabel)
    }

    /**
     * Handle any UI events
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            prepareButton -> prepareAction()
            startButton -> startAction()
            exitPrepareButton -> exitPrepareAction()

            northButton -> goNorth()
            eastButton -> goEast()
            southButton -> goSouth()
            westButton -> goWest()
        }
    }

    /**
     * Functions
     */
    private fun prepareAction() {
        startButton.isVisible = false
        prepareButton.isVisible = false
        titleLabel.isVisible = false
        exitPrepareButton.isVisible = true
    }

    private fun startAction() {
        startButton.isVisible = false
        prepareButton.isVisible = false
        titleLabel.isVisible = false

        northButton.isVisible = true
        eastButton.isVisible = true
        southButton.isVisible = true
        westButton.isVisible = true

        statusHeaderLabel.isVisible = true
        statusLabel.isVisible = true
        locationHeaderLabel.isVisible = true
        locationLabel.isVisible = true

        showLocation()
    }

    private fun exitPrepareAction() {
        startButton.isVisible = true
        prepareButton.isVisible = true
        titleLabel.isVisible = true
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

    }

    private fun goEast() {

    }

    private fun goSouth() {

    }

    private fun goWest() {

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


