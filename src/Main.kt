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
    private lateinit var exitPrepareButton: JButton

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


    }

    /**
     * Handle any UI events
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            prepareButton -> prepareAction()
            startButton -> startAction()
            exitPrepareButton -> exitPrepareAction()
        }
    }

    /**
     * An Example Action
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
    }

    private fun exitPrepareAction() {
        startButton.isVisible = true
        prepareButton.isVisible = true
        titleLabel.isVisible = true
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


