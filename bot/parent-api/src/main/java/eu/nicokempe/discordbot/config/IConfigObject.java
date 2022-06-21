package eu.nicokempe.discordbot.config;

import java.util.List;
import java.util.Map;

public interface IConfigObject {

    void load(String route);

    void update();

    <T> T getValue(ConfigValue<T> setting);

    <T> void setValue(ConfigValue<T> setting, T value);

    <E, T extends List<E>> void addListValue(ConfigValue<T> setting, E value);

    <E, Z, T extends Map<E, Z>> void putMapValue(ConfigValue<T> setting, E key, Z value);

    <E, Z, T extends Map<E, Z>> Z getMapValue(ConfigValue<T> setting, E key);

    Map<ConfigValue<?>, Object> getSettingObjects();

}
