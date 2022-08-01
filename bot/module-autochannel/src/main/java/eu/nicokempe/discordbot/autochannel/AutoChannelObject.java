package eu.nicokempe.discordbot.autochannel;

import eu.nicokempe.discordbot.IDiscordBot;
import eu.nicokempe.discordbot.channel.ChannelEntry;
import lombok.Getter;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.*;
import java.util.function.Consumer;

@Getter
public class AutoChannelObject implements IAutoChannelObject {

    private final List<ChannelEntry> autoChannel = new ArrayList<>();
    private final Map<Long, List<Long>> activeChannel = new HashMap<>();

    @Override
    public void loadAutoChannel() {
        autoChannel.clear();
        autoChannel.addAll(AutoChannel.INSTANCE.getDiscordBot().getChannelObject().getEntries().stream().filter(ChannelEntry::isAutoChannel).toList());
        for (VoiceChannel voiceChannel : AutoChannel.INSTANCE.getDiscordBot().getGuild().getVoiceChannels()) {
            if(voiceChannel.getName().startsWith("⎿")) {
                String name = voiceChannel.getName().substring(2);
                long id = autoChannel.stream().filter(channelEntry -> channelEntry.getName().equals(name)).findFirst().get().getIdLong();
                activeChannel.put(id, new ArrayList<>());
                activeChannel.get(id).add(voiceChannel.getIdLong());
            }
        }
    }

    @Override
    public boolean isAutoChannel(long id) {
        ChannelEntry entry = getAutoChannel(id);
        return entry != null;
    }

    @Override
    public boolean isCreatedChannel(long id) {
        for (List<Long> value : activeChannel.values()) {
            for (Long aLong : value) {
                if (aLong == id) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void createChannel(long id, Consumer<VoiceChannel> created) {
        ChannelEntry autoChannel = getAutoChannel(id);
        if (!activeChannel.containsKey(id))
            activeChannel.put(id, new ArrayList<>());
        int channelSize = activeChannel.get(id).size() + 1;
        Objects.requireNonNull(AutoChannel.INSTANCE.getDiscordBot().getGuild().getVoiceChannelById(id)).createCopy().queue(voiceChannel -> {
            activeChannel.get(id).add(voiceChannel.getIdLong());

            voiceChannel.getManager().setName("⎿ " + autoChannel.getName()).queue();

            created.accept(voiceChannel);
        });
        /*AutoChannel.INSTANCE.getDiscordBot().getGuild().createVoiceChannel(autoChannel.getName() + " " + intToRoman(channelSize)).queue(voiceChannel -> {
            activeChannel.get(id).add(voiceChannel.getIdLong());

            created.accept(voiceChannel);
        });*/
    }

    @Override
    public void leaveChannel(long id) {
        VoiceChannel channel = AutoChannel.INSTANCE.getDiscordBot().getGuild().getVoiceChannelById(id);
        if (channel == null) return;
        channel.delete().queue();
        activeChannel.get(getParentChannel(id)).remove(id);
    }

    private long getParentChannel(long id) {
        for (Long aLong : activeChannel.keySet()) {
            for (Long aLong1 : activeChannel.get(aLong)) {
                if (aLong1 == id) {
                    return aLong;
                }
            }
        }
        return -1;
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
