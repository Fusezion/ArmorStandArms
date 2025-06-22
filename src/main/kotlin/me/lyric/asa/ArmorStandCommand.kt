@file:Suppress("UnstableApiUsage")

package me.lyric.asa

import com.mojang.brigadier.Command
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.lyric.asa.ArmorStandPlugin.Companion.instance
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender

class ArmorStandCommand {

	val config: ArmorStandConfig
		get() = ArmorStandPlugin.armorStandConfig

	val mm: MiniMessage
		get() = AdventureUtils.MINI_MESSAGE

	val properties: ArmorStandProperties
		get() = ArmorStandPlugin.armorStandProperties

	fun register() : LiteralCommandNode<CommandSourceStack> {
		return Commands.literal("armorstand")
			.then(
				Commands.literal("toggle")
					.requires { (it.executor as? CommandSender ?: it.sender).hasPermission("armorstand.command.toggle") }
					.executes {
						val source = it.source
						val executor = source.executor as? CommandSender ?: source.sender
						config.debug = !(config.debug)
						if (config.debug) {
							executor.sendMessage(mm.deserialize(properties.getOrDefault("command.toggle.on", "Property command.toggle.on was not set")))
						} else {
							executor.sendMessage(mm.deserialize(properties.getOrDefault("command.toggle.off", "Property command.toggle.off was not set")))
						}
						return@executes Command.SINGLE_SUCCESS
					}
			)
			.then(
				Commands.literal("version")
					.requires { (it.executor as? CommandSender ?: it.sender).hasPermission("armorstand.command.version") }
					.executes {
						val source = it.source
						val executor = source.executor as? CommandSender ?: source.sender
						executor.sendMessage(mm.deserialize(properties.getOrDefault("command.version", "property command.version was not set")))
						return@executes Command.SINGLE_SUCCESS
					}
			)
			.then(
				Commands.literal("reload")
					.requires { (it.executor as? CommandSender ?: it.sender).hasPermission("armorstand.command.reload") }
					.executes {
						val source = it.source
						val executor = source.executor as? CommandSender ?: source.sender
						ArmorStandPlugin.armorStandConfig = ArmorStandConfig(instance)
						ArmorStandPlugin.armorStandProperties = ArmorStandProperties(instance, "messages.properties")
						executor.sendMessage { mm.deserialize(properties.getOrDefault("command.reloaded", "property command.reloaded was not set")) }
						return@executes Command.SINGLE_SUCCESS
					}
			)
			.executes {
				val source = it.source
				val executor = source.executor as? CommandSender ?: source.sender
				executor.sendMessage { mm.deserialize(properties.getOrDefault("command.invalid", "property command.invalid was not set")) }
				return@executes Command.SINGLE_SUCCESS
			}
			.build()
	}

}