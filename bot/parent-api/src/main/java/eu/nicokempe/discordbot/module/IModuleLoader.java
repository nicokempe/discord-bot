package eu.nicokempe.discordbot.module;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.function.Consumer;

public interface IModuleLoader {

    void loadModules(Consumer<Exception> onFinish) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    void disable();

    List<ModuleInterface> getModules();

}
