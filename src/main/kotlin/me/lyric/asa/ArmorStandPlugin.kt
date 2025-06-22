package me.lyric.asa

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class ArmourStandArms : JavaPlugin() {

	companion object {
		var armorStandArms : Boolean = true
	}

	override fun onLoad() { super.onLoad() }

	override fun onEnable() { /* Plugin startup logic */ }

	override fun onDisable() {
		/* Plugin shutdown logic */
	}

}
