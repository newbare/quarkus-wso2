package br.com.jefera.utils;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import java.util.Optional;

public final class PropertyUtil {

    private PropertyUtil() {

    }

    public static Optional<Config> config() {
        try {
            return Optional.ofNullable(ConfigProvider.getConfig());
        } catch (ExceptionInInitializerError | NoClassDefFoundError | IllegalStateException ex) {
            return Optional.empty();
        }
    }

    public static Optional<String> getProperty(String property) {
        Optional<Config> c = config();
        if (c.isPresent())
            return Optional.ofNullable(c.get().getOptionalValue(property, String.class).orElse(null));
        return Optional.empty();
    }
}
