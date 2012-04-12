package org.abstractmeta.toolbox.compilation.compiler.util;


import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * URI utilities
 *
 * @author Adrian Witas
 */

public class URIUtil {

    public static String SOURCE_CODE_URI_TEMPLATE = "string:///%s%s";
    public static String CLASS_CODE_URI_TEMPLATE = "bytecode:///%s%s";

    /**
     * Builds url for a given location, class or internal name
     *
     * @param location java file manager location
     * @param name     name
     * @return URI
     */
    public static URI buildUri(JavaFileManager.Location location, String name) {
        String extension = "";
        String template = location.getName().toLowerCase().replace("_", "") + ":///%s%s";
        if (location == StandardLocation.CLASS_OUTPUT) {
            extension = JavaFileObject.Kind.CLASS.extension;
            template = CLASS_CODE_URI_TEMPLATE;
        } else if (location == StandardLocation.SOURCE_OUTPUT) {
            extension = JavaFileObject.Kind.SOURCE.extension;
            template = SOURCE_CODE_URI_TEMPLATE;
        }
        int dotLastPosition = name.lastIndexOf('.');
        if (dotLastPosition != -1) {
            name = name.replace('.', '/');
        }
        return buildUri(String.format(template, name, extension));
    }

    /**
     * Builds uri for a given location, package, simple class name
     *
     * @param location
     * @param packageName
     * @param simpleClassName
     * @return URI
     */
    public static URI buildUri(JavaFileManager.Location location, String packageName, String simpleClassName) {
        if(packageName.isEmpty()) {
            return buildUri(location, simpleClassName);
        }
        return buildUri(location, packageName + "." + simpleClassName);
    }

    /**
     * Builds uri for a given uri fragment.
     *
     * @param uri uri fragment.
     * @return URI
     */
    public static URI buildUri(String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new IllegalStateException(String.format("Failed to build uri: %s", uri));
        }

    }
}
