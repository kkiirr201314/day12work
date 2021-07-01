open class room(val name: String){
    protected open val dangerLevel = 5

    fun description() = "Room: $name\n" + "Danger level: $dangerLevel"

    open fun load () = "這裡沒什麼可看的..."
}
open class TownSquare : room("Town Square"){
    override val dangerLevel = super.dangerLevel -3
    private var bellSound = "噹！噹！噹！噹！"

    final override fun  load() = "因為你的到來村民正在集會與歡呼!\n${ringBell()}"

    private fun ringBell() = "鐘樓正在宣布你的到來。　$bellSound"
}