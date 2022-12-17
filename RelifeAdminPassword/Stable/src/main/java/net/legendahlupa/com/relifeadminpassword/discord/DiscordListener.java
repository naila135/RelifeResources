package net.legendahlupa.com.relifeadminpassword.discord;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class DiscordListener extends ListenerAdapter {

    ArrayList<String> id = new ArrayList<>();
    private final FileConfiguration config;
    private final User user;
    private final Player player;
    private final String group;
    private final LuckPerms api;

    public DiscordListener(String id, FileConfiguration config, User user, Player player, String group, LuckPerms api) {
        this.id.add(id);
        this.config = config;
        this.user = user;
        this.player = player;
        this.group = group;
        this.api = api;
    }


    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (Objects.requireNonNull(event.getUser()).isBot()) {
            return;
        }
        if (this.id.contains(event.getMessageId())){
            if (event.getReaction().getEmoji().equals(Emoji.fromUnicode("✅"))) {
                MessageChannel channel = event.getChannel();
                channel.sendMessage("✅Выполнено").queue();
                DiscordBot discordBot = new DiscordBot(config);
                discordBot.accepted(user,player,group, api);
                id.remove(event.getMessageId());
            }
            if (event.getReaction().getEmoji().equals(Emoji.fromUnicode("❌"))) {
                MessageChannel channel = event.getChannel();
                channel.sendMessage("✅Выполнено").queue();
                DiscordBot discordBot = new DiscordBot(config);
                discordBot.dennied(user,player,group, api);
                id.remove(event.getMessageId());
            }

        }
    }

}
