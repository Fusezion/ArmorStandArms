@file:Suppress("UnstableApiUsage")

package me.lyric.asa

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
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

		this.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) {
			it.registrar().register(ArmorStandCommand().register())
		}
	}

	override fun onDisable() {
		armorStandConfig.saveConfig()
	}

}
