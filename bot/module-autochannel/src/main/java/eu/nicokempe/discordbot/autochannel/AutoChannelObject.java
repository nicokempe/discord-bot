package eu.nicokempe.discordbot.autochannel;

import eu.nicokempe.discordbot.IDiscordBot;
import eu.nicokempe.discordbot.channel.ChannelEntry;
import lombok.Getter;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Getter
public class AutoChannelObject implements IAutoChannelObject {

    private final List<ChannelEntry> autoChannel = new ArrayList<>();
    private final Map<Long, List<Long>> activeChannel = new HashMap<>();

    @Override
    public void loadAutoChannel() {
        autoChannel.addAll(AutoChannel.INSTANCE.getDiscordBot().getChannelObject().getEntries().stream().filter(ChannelEntry::isAutoChannel).toList());
    }

    @Override
    public boolean isAutoChannel(long id) {
        return getAutoChannel(id) != null;
    }

    @Override
    public void createChannel(long id, Consumer<VoiceChannel> created) {
        ChannelEntry autoChannel = getAutoChannel(id);
        if (!activeChannel.containsKey(id))
            activeChannel.put(id, new ArrayList<>());
        int channelSize = activeChannel.get(id).size() + 1;
        AutoChannel.INSTANCE.getDiscordBot().getGuild().createVoiceChannel(autoChannel.getName() + " " + intToRoman(channelSize)).queue(voiceChannel -> {
             activeChannel.get(id).add(voiceChannel.getIdLong());

            System.out.println("Join channel");

            created.accept(voiceChannel);
        });
    }

    @Override
    public void leaveChannel(long id) {
        VoiceChannel channel = AutoChannel.INSTANCE.getDiscordBot().getGuild().getVoiceChannelById(id);
        if (channel == null) return;
        channel.delete().queue();
    }

    @Override
    public ChannelEntry getAutoChannel(long id) {
        return autoChannel.stream().filter(channelEntry -> channelEntry.getIdLong() == id).findFirst().orElse(null);
    }

    private String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        int times = 0;
        String[] romans = new String[]{"I", "IV", "V", "IX", "X", "XL", "L",
                "XC", "C", "CD", "D", "CM", "M"};
        int[] ints = new int[]{1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500,
                900, 1000};
        for (int i = ints.length - 1; i >= 0; i--) {
            times = num / ints[i];
            num %= ints[i];
            while (times > 0) {
                sb.append(romans[i]);
                times--;
            }
        }
        return sb.toString();
    }

}
