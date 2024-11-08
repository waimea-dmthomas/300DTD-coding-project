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
    var item: Item? = null
    var unlockItem: Item? = null
    var barrier: Barrier? = null
    var treasureNeeded: Item? = null

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

    fun addItem(newItem: Item) {
        item = newItem
    }

    fun addUnlockItem(itemNeeded: Item) {
        unlockItem = itemNeeded
    }

    fun addBarrier(barrierBlock: Barrier){
        barrier = barrierBlock
    }

    fun addNeedsTreasure(treasure: Item) {
        treasureNeeded = treasure
    }

}

//=============================================================================================

/**
 * Item class
 */
class Item(val name: String, val description: String){
    override fun toString(): String{
        return "$name: $description"
    }
}

/**
 * Barrier class
 */
class Barrier(val name: String){
    override fun toString(): String{
        return name
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

    val inventory = DefaultListModel<Item>()

    var time = 150
    private lateinit var timeLabel: JLabel

    // Setup some properties to hold the UI elements
    private lateinit var titleLabel: JLabel

    private lateinit var startButton: JButton

    private lateinit var northButton: JButton
    private lateinit var eastButton: JButton
    private lateinit var southButton: JButton
    private lateinit var westButton: JButton

    private lateinit var inventoryButton: JButton
    private var InventoryGUI = InventoryGUI(inventory)
    private lateinit var inspectButton: JButton
    private lateinit var useButton: JButton

    private lateinit var statusHeaderLabel: JLabel
    private lateinit var statusLabel: JLabel
    private lateinit var blockedLabel: JLabel
    private lateinit var locationHeaderLabel: JLabel
    private lateinit var locationLabel: JLabel

    private lateinit var victoryLabel: JLabel
    private lateinit var gameOverLabel: JLabel

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


        timer()
    }

    private fun setupMap() {
        // Add locations
        val opening = Location("Opening", "You awake in a rough hilly plain. In front of you is the opening of a cave and behind you is the entrance of a forest.")
        val woods = Location("Woods", "You are surrounded by trees and vines in a thick wooded area. To your east is a crooked wooden hut with a busted door.")
        val hut = Location("Hut", "You are in a dilapidated old hut. Inside there is a destroyed bed frame, a broken bookshelf and a raggedy spool of rope.")
        val mouth = Location("Mouth", "You are in the mouth of an ominous cave opening. There are two different tunnels to your west and your east.")
        val eastFork = Location("East Fork", "You are in a cramped, dank tunnel.")
        val westFork = Location("West Fork", "You are in a grand large opening.")
        val pit = Location("Pit", "In front of you is a cavernous pit dropping directly into a bottomless darkness.")
        val hallway = Location("Hallway", "In front of you is a stone hallway with a bend to the west.")
        val crystalCavern = Location("Crystal Cavern", "You are in a large cavern opening covered in shiny blue crystals. In the center is a smooth loose diamond.")
        val stoneDoor = Location("Stone Door", "In front of you is a solid stone door with strange engravings. In the middle is an empty gap.")
        val deepTunnel = Location("Deep Tunnel", "In front of you is the entrance of a deep, dark tunnel.")
        val templeMain = Location("Temple", "You enter into an ancient stone temple with engravings on the walls. There are hallways leading in every direction.")
        val templeWest = Location("West Hallway", "You enter through a large ancient hallway. There is a dead body laying against the wall.")
        val templeNorth = Location("North Doorway", "You enter through a large ancient hallway. There is an ornate door in front of you.")
        val templeEast = Location("East Hallway", "You enter through a large ancient hallway. There is a golden doorway to your east.")
        val templeTreasury = Location("Treasury", "You are in the temple's treasury. There is gold bars surrounding you and a gilded locked chest.")
        val exit = Location("Exit", "")

        // Add items
        val rope = Item("Rope", "A raggedy spool of rope.")
        val crystal = Item("Crystal", "A smooth blue gem.")
        val key = Item("Key", "A golden key.")
        val treasure = Item("Treasure", "The treasure trove.")

        // Add barriers
        val pitBarrier = Barrier("pit")
        val doorBarrier = Barrier("stone door")
        val keyDoorBarrier = Barrier("locked door")

        // Add data to locations
        locations.add(opening)
        opening.addNorth(mouth)
        opening.addSouth(woods)

        locations.add(woods)
        woods.addEast(hut)

        locations.add(hut)
        hut.addItem(rope)

        locations.add(mouth)
        mouth.addEast(eastFork)
        mouth.addWest(westFork)

        locations.add(westFork)
        westFork.addNorth(pit)

        locations.add(pit)
        pit.addNorth(hallway)

        locations.add(hallway)
        hallway.addBarrier(pitBarrier)
        hallway.addUnlockItem(rope)
        hallway.addWest(crystalCavern)

        locations.add(crystalCavern)
        crystalCavern.addItem(crystal)

        locations.add(eastFork)
        eastFork.addNorth(stoneDoor)

        locations.add(stoneDoor)
        stoneDoor.addNorth(deepTunnel)

        locations.add(deepTunnel)
        deepTunnel.addBarrier(doorBarrier)
        deepTunnel.addUnlockItem(crystal)
        deepTunnel.addNorth(templeMain)

        locations.add(templeMain)
        templeMain.addNorth(templeNorth)
        templeMain.addWest(templeWest)
        templeMain.addEast(templeEast)

        locations.add(templeWest)
        templeWest.addItem(key)

        locations.add(templeEast)
        templeEast.addEast(templeTreasury)

        locations.add(templeNorth)
        templeNorth.addNorth(exit)

        locations.add(exit)
        exit.addNeedsTreasure(treasure)

        locations.add(templeTreasury)
        templeTreasury.addBarrier(keyDoorBarrier)
        templeTreasury.addUnlockItem(key)
        templeTreasury.addItem(treasure)

    }

    /**
     * Configure the main window
     */
    private fun setupWindow() {
        title = "Treasure Hunt"
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

        titleLabel = JLabel("Treasure Hunt", SwingConstants.CENTER)
        titleLabel.bounds = Rectangle(76, 62, 348, 36)
        titleLabel.font = baseFont
        add(titleLabel)

        startButton = JButton("Start")
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

        blockedLabel = JLabel("", SwingConstants.CENTER)
        blockedLabel.bounds = Rectangle(63, 264, 374, 46)
        blockedLabel.font = baseFont
        blockedLabel.isVisible = false
        add(blockedLabel)

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

        timeLabel = JLabel("Time: $time", SwingConstants.CENTER)
        timeLabel.bounds = Rectangle(353, 351, 131, 50)
        timeLabel.font = baseFont
        timeLabel.isVisible = false
        add(timeLabel)

        victoryLabel = JLabel("", SwingConstants.CENTER)
        victoryLabel.bounds = Rectangle(119, 74, 262, 176)
        victoryLabel.font = baseFont
        victoryLabel.isVisible = false
        add(victoryLabel)

        gameOverLabel = JLabel("", SwingConstants.CENTER)
        gameOverLabel.bounds = Rectangle(119, 74, 262, 176)
        gameOverLabel.font = baseFont
        gameOverLabel.isVisible = false
        add(gameOverLabel)

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

        useButton = JButton("Use")
        useButton.bounds = Rectangle(353,411,131,50)
        useButton.font = baseFont
        useButton.addActionListener(this)
        useButton.isVisible = false
        add(useButton)
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

            inventoryButton -> inventoryOpen()
            inspectButton -> useInspect()
            useButton -> useItem()
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
        useButton.isVisible = true
        timeLabel.isVisible = true

        statusHeaderLabel.isVisible = true
        statusLabel.isVisible = true
        blockedLabel.isVisible = true
        locationHeaderLabel.isVisible = true
        locationLabel.isVisible = true

        showLocation()
    }

    private fun winGame() {
        northButton.isVisible = false
        eastButton.isVisible = false
        southButton.isVisible = false
        westButton.isVisible = false
        inventoryButton.isVisible = false
        inspectButton.isVisible = false
        useButton.isVisible = false
        timeLabel.isVisible = false

        statusHeaderLabel.isVisible = false
        statusLabel.isVisible = false
        blockedLabel.isVisible = false
        locationHeaderLabel.isVisible = false
        locationLabel.isVisible = false

        victoryLabel.isVisible = true
        victoryLabel.text = "<html>Congratulations! You escaped the cave with the treasure.</html>"
    }

    private fun loseGame() {
        northButton.isVisible = false
        eastButton.isVisible = false
        southButton.isVisible = false
        westButton.isVisible = false
        inventoryButton.isVisible = false
        inspectButton.isVisible = false
        useButton.isVisible = false
        timeLabel.isVisible = false

        statusHeaderLabel.isVisible = false
        statusLabel.isVisible = false
        blockedLabel.isVisible = false
        locationHeaderLabel.isVisible = false
        locationLabel.isVisible = false

        gameOverLabel.isVisible = true
        gameOverLabel.text = "<html>You ran out of time! Game Over..</html>"
    }

    private fun showLocation() {
        locationLabel.text = currentLocation.name
        statusLabel.setText("<html>" + currentLocation.desc + "</html>")
        checkNorth()
        checkEast()
        checkSouth()
        checkWest()
    }

    /**
     *  Timer
     */

    private fun timer() {
        while (time >= 0) {
            time = time - 1
            Thread.sleep(1000)
            timeLabel.text = "Time: $time"

            if (time <= 0) {
                loseGame()
            }
        }
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
        if (currentLocation.north!!.treasureNeeded != null) {
            if (inventory.contains(currentLocation.north?.treasureNeeded)) {
                winGame()
            }
            else {
                blockedLabel.text = "You need the treasure to leave!"
            }
        }
        else {
            if (currentLocation.north!!.barrier != null){
                blockedLabel.text = "You are blocked by a ${currentLocation.north!!.barrier}"
            }
            else {
                blockedLabel.text = ""
                currentLocation = currentLocation.north!!
                showLocation()
            }
        }
    }

    private fun goEast() {
        if (currentLocation.east!!.barrier != null){
            blockedLabel.text = "You are blocked by a ${currentLocation.east!!.barrier}!"
        }
        else {
            blockedLabel.text = ""
            currentLocation = currentLocation.east!!
            showLocation()
        }
    }

    private fun goSouth() {
        if (currentLocation.south!!.barrier != null){
            blockedLabel.text = "You are blocked by a ${currentLocation.south!!.barrier}!"
        }
        else {
            blockedLabel.text = ""
            currentLocation = currentLocation.south!!
            showLocation()
        }
    }

    private fun goWest() {
        if (currentLocation.west!!.barrier != null){
            blockedLabel.text = "You are blocked by a ${currentLocation.west!!.barrier}!"
        }
        else {
            blockedLabel.text = ""
            currentLocation = currentLocation.west!!
            showLocation()
        }
    }

    private fun useItem() {
        if (inventory.contains(currentLocation.north?.unlockItem)) {
            blockedLabel.text = "You used ${currentLocation.north!!.unlockItem!!.name}!"
            currentLocation.north!!.unlockItem = null
            currentLocation.north!!.barrier = null
        }
        else {
            if (inventory.contains(currentLocation.east?.unlockItem)) {
                blockedLabel.text = "You used ${currentLocation.east!!.unlockItem!!.name}!"
                currentLocation.east!!.unlockItem = null
                currentLocation.east!!.barrier = null
            }
            else {
                if (inventory.contains(currentLocation.south?.unlockItem)) {
                    blockedLabel.text = "You used ${currentLocation.south!!.unlockItem!!.name}!"
                    currentLocation.south!!.unlockItem = null
                    currentLocation.south!!.barrier = null
                }
                else {
                    if (inventory.contains(currentLocation.west?.unlockItem)) {
                        blockedLabel.text = "You used ${currentLocation.west!!.unlockItem!!.name}!"
                        currentLocation.west!!.unlockItem = null
                        currentLocation.west!!.barrier = null
                    }
                    else {
                        blockedLabel.text = "Nothing to use here!"
                    }
                }
            }
        }
    }

    private fun inventoryOpen() {
        InventoryGUI.setLocationRelativeTo(null)
        InventoryGUI.isVisible = true
    }

    private fun useInspect() {
        if (currentLocation.item != null) {
            statusLabel.text = "You found a ${currentLocation.item!!.name}!"
            inventory.addElement(currentLocation.item!!)
            currentLocation.item = null
        }
        else {
            statusLabel.text = "This room is empty!"
        }
    }
}

//=============================================================================================

class InventoryGUI(val inventory: DefaultListModel<Item>) : JDialog() {

    private lateinit var inventoryLabel: JLabel
    private lateinit var backButton: JButton
    private lateinit var inventoryList: JList<Item>


    init {
        setupWindow()
        buildInvUI()
    }

    private fun setupWindow() {
        title = "Inventory"
        contentPane.preferredSize = Dimension(500, 500)
        isResizable = false
        isModal = true
        layout = null
        pack()
    }

    private fun buildInvUI() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 30)

        inventoryLabel = JLabel("Inventory", SwingConstants.CENTER)
        inventoryLabel.bounds = Rectangle(174, 50, 152, 42)
        inventoryLabel.font = baseFont
        add(inventoryLabel)

        inventoryList = JList(inventory)
        inventoryList.bounds = Rectangle(45, 106, 400, 320)
        inventoryList.font = baseFont
        add(inventoryList)

        backButton = JButton("Back")
        backButton.bounds = Rectangle(640, 877, 200, 40)
        backButton.font = baseFont
        add(backButton)
    }
}

/**
 * Launch the application
 */
fun main() {
    // Flat, Dark look-and-feel
    FlatDarkLaf.setup()

    // Create the UI
    MenuGUI()
}