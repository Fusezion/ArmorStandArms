package me.lyric.asa

import org.bukkit.entity.ArmorStand
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

class ArmorStandPlugin : JavaPlugin() {

	companion object {
		lateinit var instance : ArmorStandPlugin
			private set
		lateinit var pluginLogger: Logger
			private set
		lateinit var armorStandConfig : ArmorStandConfig
		lateinit var armorStandProperties : ArmorStandProperties
	}

	override fun onEnable() {
		instance = this
		pluginLogger = this.logger
		armorStandConfig = ArmorStandConfig(this)
		armorStandProperties = ArmorStandProperties(this, "messages.properties")
		server.pluginManager.registerEvents(ArmorStandListener(), this)
	}

	override fun onDisable() {
		armorStandConfig.saveConfig()
	}

}
