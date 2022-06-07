package eu.nicokempe.discordbot.module;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.List;

public interface IModuleLoader {

    void loadModules() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    List<ModuleInterface> getModules();

}
