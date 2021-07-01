import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import kotlin.system.exitProcess

fun main() {
    Game.play()
}

object Game{
    val player = Player("Madrigal")
    var currentRoom: room = TownSquare()

    private var worldMap= listOf(
        listOf(currentRoom, room("Tavern"), room("Back Room")),
        listOf(room("Long Corridor"), room("Generic Room")))


    init {
        player.castFireball()
        println("歡迎您，冒險者。")
    }
    fun play(){
        while (true){
            println(currentRoom.description())
            println(currentRoom.load())
            printPlayerStatus(player)

            print(">請輸入您的指令:")

            println(GameInput(readLine()).processCommand())
            if ( readLine() == "quit" || readLine()== "exit" ){
                break
            }
        }
    }
    private fun printPlayerStatus(player:Player) {
        println(
            "光環顏色：${player.auraColor()}" + "    走運嗎？" +
                    "${if (player.isBlessed) "是的" else "否"}"
        )
        println("${player.name} ${player.formatHealthStatus()}")
    }

    private class GameInput(arg : String?) {
        private val input = arg?:""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1, { "" })

        fun processCommand() = when (command.toLowerCase()) {
            "move" -> move(argument)
            "fight" -> fight()
            "map" -> printMap(player)
            "ring" -> ringBell("Gwong~~")
            "quit" -> quit(player)
            "exit" -> quit(player)
            else -> commandNotFound()
        }
        private fun commandNotFound() = "我不確定您要做什麼！"
    }
    //在這加入move函數
    private fun move(directionInput: String)=
        try {
            val direction = Direction.valueOf(directionInput.toUpperCase())
            val newPosition = direction.updateCoordinate(player.currentPosition)
            if (!newPosition.isInBounds) {
                throw IllegalStateException("$direction is out of bounds.")
            }
            val newRoom = worldMap[newPosition.y] [newPosition.x]
            player.currentPosition = newPosition
            currentRoom = newRoom
            "OK, you move $direction to the ${newRoom.name}.\n${newRoom.load()}"
        } catch (e: Exception)
        {
            "Invalid direction: $directionInput."
        }
    //在這加入fight函數
    private fun fight() = currentRoom.monster?.let {
        while (player.healthPoints > 0 && it.healthPoints > 0){
            slay(it)
            Thread.sleep(1000)
        }
        "打完了!!!"
    } ?: "怪物已清光"

    //在這加入slay函數
    private fun slay(monster: Monster){
        println("${monster.name} 被 ${monster.attack(player)} 攻擊損傷！")
        println("${player.name} 被 ${player.attack(monster)} 攻擊損傷！")
        if (player.healthPoints <= 0) {
            println(">>>> 您已被 ${monster.name} 擊殺，再回家練練吧！ <<<<")
            exitProcess(0)
        }
        if (monster.healthPoints <=0) {
            println(">>>> ${monster.name} 已被您擊敗！ <<<<")
            currentRoom.monster = null
        }
    }

    //在這加入quit函數
    private fun quit(player: Player) = "～　再見， ${player.name} ，歡迎再來玩　～ "

    //在這加入printMap
    private fun printMap(player: Player){
        val x :Int = player.currentPosition.x
        val y :Int = player.currentPosition.y
        println("x= $x ,y= $y")
        for (h in 0..1) {
            for (i in 0..2) {
                if (i == x && h == y)
                    print("X ")
                else
                    print("O ")
                if(i == 3 && h == 1) {
                    break
                }
            }
            println()
        }
    }
    //加入ringBell()
    public fun ringBell( bellSound: String) = "鐘樓響起鐘聲宣告您的到來。 $bellSound"
}
