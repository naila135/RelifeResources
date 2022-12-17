package net.legendahlupa.com.relifeadminpassword.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.legendahlupa.com.relifeadminpassword.commands.Alogin;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DiscordBot {
    private JDA jda;
    private final FileConfiguration config;

    public DiscordBot(FileConfiguration config) {
        this.config = config;
    }


    public void create() throws InterruptedException {
        jda = JDABuilder.createDefault(config.getString("DiscordBotToken"))
                .setActivity(Activity.playing("ReLife"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
        jda.awaitReady();
    }

    public void sendStartMessage() {
        TextChannel textChannel = jda.getTextChannelById(config.getString("DiscordAcceptingChannel"));
        if (textChannel != null) {
            textChannel.sendMessage("\uD83D\uDFE2 Бот запущен").queue();
        } else {
            System.out.println(ChatColor.RED + "Данного канала не существует");
        }
    }

    public void sendAcceptMessage(User user, Player player, String group, LuckPerms api) {

        try{
            create();
            TextChannel textChannel = jda.getTextChannelById(config.getString("DiscordAcceptingChannel"));

            if (textChannel != null) {
                Message msg = textChannel.sendMessage("```Администратор:" + player.getName() +
                        "\nПросит подтверждения для получения прав: " + group +
                        "\nГалочка что бы подтвердить" +
                        "\nКрестик что бы отклонить```").complete();
                msg.addReaction(Emoji.fromUnicode("✅")).queue();
                msg.addReaction(Emoji.fromUnicode("❌")).queue();
                jda.addEventListener(new DiscordListener(msg.getId(), config, user, player, group, api));
            } else {
                System.out.println(ChatColor.RED + "Данного канала не существует");
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    public void accepted(User user, Player player, String group, LuckPerms api) {
        Alogin alogin = new Alogin(config, api);
        alogin.accepted(user, player, group, api);
    }

    public void dennied(User user, Player player, String group, LuckPerms api) {
        Alogin alogin = new Alogin(config, api);
        alogin.dennied(user, player, group, api);
    }


}
