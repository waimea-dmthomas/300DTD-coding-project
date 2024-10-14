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

class Location(val type: String)

//=============================================================================================

/**
 * GUI class
 * Defines the UI and responds to events
 */
class MenuGUI : JFrame(), ActionListener {

    // Setup some properties to hold the UI elements
    private lateinit var titleLabel: JLabel
    private lateinit var prepareButton: JButton
    private lateinit var startButton: JButton

    /**
     * Create, build and run the UI
     */
    init {
        setupWindow()
        buildUI()

        // Show the app, centred on screen
        setLocationRelativeTo(null)
        isVisible = true
    }

    /**
     * Configure the main window
     */
    private fun setupWindow() {
        title = "Caves, Creatures and Carrots"
        contentPane.preferredSize = Dimension(300, 300)
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
        titleLabel.bounds = Rectangle(30, 30, 240, 40)
        titleLabel.font = baseFont
        add(titleLabel)

        prepareButton = JButton("Prepare")
        prepareButton.bounds = Rectangle(30,130,240,40)
        prepareButton.font = baseFont
        prepareButton.addActionListener(this)
        add(prepareButton)

        startButton = JButton("Enter the Cave")
        startButton.bounds = Rectangle(30,186,240,40)
        startButton.font = baseFont
        startButton.addActionListener(this)
        add(startButton)
    }

    /**
     * Handle any UI events
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            prepareButton -> prepareAction()
            startButton -> startAction()
        }
    }

    /**
     * An Example Action
     */
    private fun prepareAction() {
        startButton.isEnabled = false
        SetupGUI()
    }

    private fun startAction() {
        titleLabel.text = "You Clicked!"
    }
}

//=============================================================================================

class SetupGUI : JFrame(), ActionListener {
    // Setup some properties to hold the UI elements
    private lateinit var exitButton: JButton

    /**
     * Create, build and run the UI
     */
    init {
        setupWindow()
        buildUI()

        // Show the app, centred on screen
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun setupWindow() {
        title = "Setup"
        contentPane.preferredSize = Dimension(500, 500)
        defaultCloseOperation = WindowConstants.HIDE_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }

    private fun buildUI() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 20)

        exitButton = JButton("Exit")
        exitButton.bounds = Rectangle(30,130,240,40)
        exitButton.font = baseFont
        exitButton.addActionListener(this)
        add(exitButton)
    }

    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            exitButton -> exitAction()
        }
    }

    /**
     * An Example Action
     */
    private fun exitAction() {

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


