package eu.nicokempe.discordbot.module;

import com.google.gson.Gson;
import com.sun.tools.jconsole.JConsoleContext;
import eu.nicokempe.discordbot.DiscordBot;
import eu.nicokempe.discordbot.request.RequestBuilder;
import lombok.Getter;
import okhttp3.FormBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.NoSuchFileException;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.Consumer;

@Getter
public class ModuleLoader implements IModuleLoader {

    private final List<ModuleInterface> modules = new ArrayList<>();

    private final File path;

    public ModuleLoader() {
        path = new File("./modules");
        path.mkdirs();
    }

    @Override
    public void loadModules(Consumer<Exception> onFinish) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        File[] modules = path.listFiles(pathname -> pathname.getName().endsWith(".jar"));
        if (modules == null) {
            onFinish.accept(new NoSuchFileException("No Modules found"));
            return;
        }
        List<URL> modulesInURL = new ArrayList<>(modules.length);
        for (File module : modules) {
            modulesInURL.add(module.toURI().toURL());
        }

        URLClassLoader loader = new URLClassLoader(modulesInURL.toArray(new URL[0]));
        Enumeration<URL> resources = loader.findResources("module.properties");

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            Properties properties = new Properties();
            try (InputStream is = resource.openStream()) {
                properties.load(is);
                if (!properties.containsKey("main") || !properties.containsKey("version") || !properties.containsKey("name")) {
                    onFinish.accept(new MissingFormatArgumentException("module.properties does not contain required argument! (main, name, version)"));
                    return;
                }
                String className = properties.getProperty("main");
                Class<?> pluginClass = loader.loadClass(className);

                ModuleInterface moduleInterface = (ModuleInterface) pluginClass.getDeclaredConstructor().newInstance();
                moduleInterface.setProperties(properties);
                moduleInterface.setDiscordBot(DiscordBot.INSTANCE);

                System.out.println(MessageFormat.format("Loading {0} v{1}", properties.getProperty("name"), properties.getProperty("version")));
                moduleInterface.enable();
                this.modules.add(moduleInterface);
            }
        }

        System.out.println(MessageFormat.format("{0} module(s) was successfully loaded!", this.modules.size()));
        onFinish.accept(null);
    }

    @Override
    public void disable() {
        for (ModuleInterface module : modules) {
            module.disable();
            System.out.println(MessageFormat.format("Disabled {0} v{1}", module.getProperties().getProperty("name"), module.getProperties().getProperty("version")));
        }
    }

}
