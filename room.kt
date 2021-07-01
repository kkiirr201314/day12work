open class room(val name: String){
    protected open val dangerLevel = 5

    var monster: Monster? = Goblin()

    fun description() = "你現在的位置: $name\n" + "此地危險等級: $dangerLevel\n" +
            "怪物: ${monster?.description ?: "無怪物"}"

    open fun load () = "這裡沒什麼可看的..."
}
open class TownSquare : room("大城鎮"){
    override val dangerLevel = super.dangerLevel -3
    private var bellSound = "噹！噹！噹！噹！"

    final override fun  load() = "因為你的到來村民正在集會與歡呼!\n${ringBell()}"

    private fun ringBell() = "$bellSound 鐘樓響起鐘聲正宣告你的到來。"
}