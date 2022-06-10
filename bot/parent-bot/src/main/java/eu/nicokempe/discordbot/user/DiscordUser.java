package eu.nicokempe.discordbot.user;

import eu.nicokempe.discordbot.DiscordBot;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

@Getter
@Setter
public class DiscordUser implements IDiscordUser {

    private User user;
    private Member member;
    private long id;

    @Override
    public boolean isBot() {
        return user.isBot();
    }

    @Override
    public void load(long id) {
        this.id = id;
        this.member = DiscordBot.INSTANCE.getGuild().getMemberById(id);
        if (member == null) throw new IllegalArgumentException("Member can not be null");
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
}
