package eu.nicokempe.discordbot.user;

import eu.nicokempe.discordbot.DiscordBot;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class DiscordUser implements IDiscordUser {

    private User user;
    private Member member;
    private long id;

    private final Map<String, Object> player = new HashMap<>();

    @Override
    public boolean isBot() {
        return user.isBot();
    }

    @Override
    public void load(long id) {
        this.id = id;
        this.member = DiscordBot.INSTANCE.getGuild().getMemberById(id);
        if (member == null) throw new IllegalArgumentException("Member cannot be null");
        this.user = member.getUser();
    }

    @Override
    public String getIdString() {
        return String.valueOf(id);
    }

    @Override
    public void setNickname(String name) {
        member.modifyNickname(name).queue();
    }

    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public String getNickname() {
        return member.getNickname() == null ? getName() : member.getNickname();
    }

    @Override
    public OnlineStatus getStatus() {
        return member.getOnlineStatus();
    }

    @Override
    public <T> T getPlayer(Class<T> tClass) {
        return (T) this.player.getOrDefault(tClass.getName(), null);
    }

    @Override
    public <T> void setPlayer(Class<T> tClass, T player) {
        this.player.put(tClass.getName(), player);
    }
}
