package eu.nicokempe.discordbot.module;

import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

@Getter
public class ModuleLoader implements IModuleLoader {

    private final List<ModuleInterface> modules = new ArrayList<>();

    private final File path;

    public ModuleLoader() {
        path = new File("./modules");
        path.mkdirs();
    }

    @Override
    public void loadModules() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        File[] modules = path.listFiles(pathname -> pathname.getName().endsWith(".jar"));
        if (modules == null) return;
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
                if (!properties.containsKey("main")) return;
                String className = properties.getProperty("main");
                Class<?> pluginClass = loader.loadClass(className);
                ModuleInterface moduleInterface = (ModuleInterface) pluginClass.getDeclaredConstructor().newInstance();
                moduleInterface.enable();
                this.modules.add(moduleInterface);
                System.out.println("Loaded " + properties.getProperty("name"));
            }
        }

        System.out.println("Loaded " + this.modules.size() + " modules");
    }

}
